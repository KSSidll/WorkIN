package com.kssidll.workin.ui.editsession

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.hilt.navigation.compose.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.ui.addsession.*
import com.kssidll.workin.ui.shared.*
import com.kssidll.workin.ui.theme.*

/// Route ///
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
            onBack = onBack,
            onEdit = {
                editSessionViewModel.updateSession(it)
                onBack()
            },
            onDelete = {
                editSessionViewModel.deleteSession()
                onBack()
            }
        )
    }
}


/// Screen ///
@Composable
fun EditSessionScreen(
    session: SessionWithFullSessionWorkouts,
    onBack: () -> Unit,
    onEdit: (SessionWithFullSessionWorkouts) -> Unit,
    onDelete: () -> Unit,
) {

}


/// Screen Preview ///
@Preview(
    group = "EditSessionScreen",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "EditSessionScreen",
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
                onEdit = {},
                onDelete = {},
            )
        }
    }
}
