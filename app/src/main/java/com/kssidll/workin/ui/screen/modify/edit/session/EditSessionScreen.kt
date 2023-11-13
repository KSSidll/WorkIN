package com.kssidll.workin.ui.screen.modify.edit.session

import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.*
import com.kssidll.workin.R
import com.kssidll.workin.ui.screen.modify.shared.session.*
import dev.olshevski.navigation.reimagined.hilt.*
import kotlinx.coroutines.*

@Composable
fun EditSessionRoute(
    sessionId: Long,
    onBack: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val viewModel: EditSessionViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.updateState(sessionId)
    }

    ModifySessionScreen(
        state = viewModel.screenState,
        onBack = onBack,
        onSubmit = {
            scope.launch {
                if (viewModel.updateSession(sessionId)) {
                    onBack()
                }
            }
        },
        onDelete = {
            scope.launch {
                if (viewModel.deleteSession(sessionId)) {
                    onBack()
                }
            }
        },
        submitButtonText = stringResource(id = R.string.confirm_edit_session_text),
        submitButtonIcon = Icons.Default.Edit,
        allWorkouts = viewModel.allWorkoutsFlow()
            .collectAsState(initial = emptyList()).value,
    )
}