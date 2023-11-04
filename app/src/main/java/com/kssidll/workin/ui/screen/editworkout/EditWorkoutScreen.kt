package com.kssidll.workin.ui.screen.editworkout

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
import com.kssidll.workin.ui.component.*
import com.kssidll.workin.ui.theme.*
import dev.olshevski.navigation.reimagined.hilt.*

@Composable
fun EditWorkoutRoute(
    workoutId: Long,
    onBack: () -> Unit,
) {
    var isLoading: Boolean by remember {
        mutableStateOf(true)
    }

    val editWorkoutViewModel: EditWorkoutViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        editWorkoutViewModel.fetchWorkout(workoutId)
        isLoading = false
    }

    if (!isLoading) {
        EditWorkoutScreen(
            workout = editWorkoutViewModel.workout,
            onBack = onBack,
            onEdit = {
                editWorkoutViewModel.updateWorkout(it)
                onBack()
            },
            onDelete = {
                editWorkoutViewModel.deleteWorkout()
                onBack()
            }
        )
    }
}

@Composable
fun EditWorkoutScreen(
    workout: Workout,
    onBack: () -> Unit,
    onEdit: suspend (Workout) -> Unit,
    onDelete: () -> Unit,
) {
    EditWorkoutDataSubpage(
        onBack = onBack,
        submitButtonContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = stringResource(
                        R.string
                            .confirm_edit_workout_description
                    ),
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.confirm_edit_workout_text),
                    fontSize = 20.sp,
                )
            }
        },
        onSubmit = {
            onEdit(
                workout.apply {
                    name = it.name
                    description = it.description
                }
            )
        },
        startState = EditWorkoutDataSubpageState(
            name = workout.name,
            description = workout.description,
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
                    contentDescription = stringResource(id = R.string.delete_workout),
                    modifier = Modifier.size(27.dp)
                )
            }
        }
    )
}

@Preview(
    group = "Edit Workout Screen",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Edit Workout Screen",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun EditWorkoutScreenPreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            EditWorkoutScreen(
                workout = Workout(
                    name = "test workout name",
                    description = "test workout description",
                ),
                onBack = {},
                onEdit = {},
                onDelete = {},
            )
        }
    }
}
