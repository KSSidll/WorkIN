package com.kssidll.workin.ui.screen.modify.add.session

import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.*
import com.kssidll.workin.R
import com.kssidll.workin.ui.screen.modify.shared.session.*
import dev.olshevski.navigation.reimagined.hilt.*
import kotlinx.coroutines.*

@Composable
fun AddSessionRoute(
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val viewModel: AddSessionViewModel = hiltViewModel()

    ModifySessionScreen(
        state = viewModel.screenState,
        onBack = onBack,
        onSubmit = {
            scope.launch {
                if (viewModel.addSession()) {
                    onBack()
                }
            }
        },
        submitButtonText = stringResource(id = R.string.confirm_add_session_text),
        submitButtonIcon = Icons.Default.Check,
        allWorkouts = viewModel.allWorkouts()
            .collectAsState(initial = emptyList()).value,
    )
}