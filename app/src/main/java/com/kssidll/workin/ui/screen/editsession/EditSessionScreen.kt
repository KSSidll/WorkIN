package com.kssidll.workin.ui.screen.editsession

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.*
import com.kssidll.workin.ui.screen.session.component.*
import com.kssidll.workin.ui.theme.*
import dev.olshevski.navigation.reimagined.hilt.*

@Composable
fun EditSessionRoute(
    sessionId: Long,
    onBack: () -> Unit,
) {
    var isLoading: Boolean by remember {
        mutableStateOf(true)
    }

    val editSessionViewModel: EditSessionViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        editSessionViewModel.fetchSession(sessionId)
        isLoading = false
    }

    if (!isLoading) {
        EditSessionScreen(
            session = editSessionViewModel.session,
            userWorkouts = editSessionViewModel.getWorkouts()
                .collectAsState(initial = emptyList()).value,
            onBack = onBack,
            onEdit = {
                editSessionViewModel.updateSession(it)
                onBack()
            },
            onDelete = {
                editSessionViewModel.deleteSession()
                onBack()
            },
        )
    }
}

@Composable
fun EditSessionScreen(
    session: SessionWithFullSessionWorkouts,
    userWorkouts: List<Workout>,
    onBack: () -> Unit,
    onEdit: suspend (SessionWithFullSessionWorkouts) -> Unit,
    onDelete: () -> Unit,
) {
    EditSessionDataSubpage(
        onBack = onBack,
        workouts = userWorkouts,
        submitButtonContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = stringResource(
                        R.string
                            .confirm_edit_session_description
                    ),
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.confirm_edit_session_text),
                    fontSize = 20.sp,
                )
            }
        },
        onSubmit = {
            onEdit(
                session.apply {
                    this.session.name = it.name
                    this.session.description = it.description
                    this.session.days = it.days
                    this.workouts = it.workouts.mapIndexed { index, it ->
                        FullSessionWorkout(
                            sessionWorkout = SessionWorkout(
                                sessionId = this.session.id,
                                workoutId = it.workoutId,
                                repetitionCount = it.repetitionCount.value,
                                repetitionType = it.repetitionType.value.id,
                                weight = it.weight.value,
                                weightType = it.weightType.value.id,
                                order = index,
                                restTime = it.postRestTime.value,
                            ),
                            workout = Workout(
                                id = it.workoutId,
                                name = it.workoutName.value,
                                description = String(),
                            )
                        )
                    }
                }
            )
        },
        startState = EditSessionDataSubpageState(
            name = session.session.name,
            description = session.session.description,
            days = session.session.days,
            workouts = session.workouts.map {
                SessionBuilderWorkout(
                    workoutName = mutableStateOf(it.workout.name),
                    workoutId = it.workout.id,
                    repetitionCount = mutableIntStateOf(it.sessionWorkout.repetitionCount),
                    repetitionType = mutableStateOf(RepetitionTypes.getById(it.sessionWorkout.repetitionType)!!),
                    weight = mutableFloatStateOf(it.sessionWorkout.weight),
                    weightType = mutableStateOf(WeightTypes.getById(it.sessionWorkout.weightType)!!),
                    postRestTime = mutableIntStateOf(it.sessionWorkout.restTime),
                )
            },
        ),
        actions = {
            IconButton(
                onClick = onDelete,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.error,
                ),
                modifier = Modifier
                    .minimumInteractiveComponentSize()
            ) {
                Icon(
                    imageVector = Icons.Rounded.DeleteForever,
                    contentDescription = stringResource(id = R.string.delete_session),
                    modifier = Modifier.size(27.dp)
                )
            }
        }
    )
}

@Preview(
    group = "Edit Session Screen",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Edit Session Screen",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun EditWorkoutScreenPreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            EditSessionScreen(
                session = SessionWithFullSessionWorkouts(
                    session = Session(
                        name = "test session name 1",
                        description = "test session description 1",
                        days = EncodedDaysBuilder().addSaturday()
                            .addTuesday()
                            .build()
                    ),
                    workouts = listOf(
                        FullSessionWorkout(
                            sessionWorkout = SessionWorkout(
                                sessionId = 0,
                                workoutId = 0,
                                repetitionCount = 0,
                                repetitionType = RepetitionTypes.Repetitions.id,
                                weight = 0F,
                                weightType = WeightTypes.KGBodyMass.id,
                                order = 0,
                                restTime = 0,
                            ),
                            workout = Workout(
                                name = "test workout name 1",
                                description = "test workout description 1"
                            )
                        ),
                        FullSessionWorkout(
                            sessionWorkout = SessionWorkout(
                                sessionId = 0,
                                workoutId = 0,
                                repetitionCount = 0,
                                repetitionType = RepetitionTypes.RiR.id,
                                weight = 0F,
                                weightType = WeightTypes.BodyMass.id,
                                order = 0,
                                restTime = 0,
                            ),
                            workout = Workout(
                                name = "test workout name 2",
                                description = "test workout description 2"
                            )
                        )
                    )
                ),

                onBack = {},
                userWorkouts = listOf(),
                onEdit = {},
                onDelete = {},
            )
        }
    }
}
