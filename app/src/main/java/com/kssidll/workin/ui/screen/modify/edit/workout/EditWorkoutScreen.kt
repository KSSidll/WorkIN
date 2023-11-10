package com.kssidll.workin.ui.screen.modify.edit.workout

import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.*
import com.kssidll.workin.R
import com.kssidll.workin.ui.screen.modify.shared.workout.*
import dev.olshevski.navigation.reimagined.hilt.*
import kotlinx.coroutines.*

@Composable
fun EditWorkoutRoute(
    workoutId: Long,
    onBack: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val viewModel: EditWorkoutViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.updateState(workoutId)
    }

    ModifyWorkoutScreen(
        state = viewModel.screenState,
        onBack = onBack,
        onDelete = {
            scope.launch {
                if (viewModel.deleteWorkout(workoutId)) {
                    onBack()
                }
            }
        },
        onSubmit = {
            scope.launch {
                if (viewModel.updateWorkout(workoutId)) {
                    onBack()
                }
            }
        },
        submitButtonText = stringResource(id = R.string.confirm_edit_workout_text),
        submitButtonIcon = Icons.Default.Edit,
    )
}
