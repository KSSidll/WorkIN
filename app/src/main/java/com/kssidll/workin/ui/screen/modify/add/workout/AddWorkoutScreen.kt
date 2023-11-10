package com.kssidll.workin.ui.screen.modify.add.workout

import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.*
import com.kssidll.workin.R
import com.kssidll.workin.ui.screen.modify.shared.workout.*
import dev.olshevski.navigation.reimagined.hilt.*
import kotlinx.coroutines.*

@Composable
fun AddWorkoutRoute(
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val viewModel: AddWorkoutViewModel = hiltViewModel()

    ModifyWorkoutScreen(
        state = viewModel.screenState,
        onBack = onBack,
        onSubmit = {
            scope.launch {
                if (viewModel.addWorkout() != null) {
                    onBack()
                }
            }
        },
        submitButtonText = stringResource(id = R.string.confirm_add_workout_text),
        submitButtonIcon = Icons.Default.Check,
    )
}
