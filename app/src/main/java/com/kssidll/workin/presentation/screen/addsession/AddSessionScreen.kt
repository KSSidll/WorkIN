package com.kssidll.workin.presentation.screen.addsession

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.presentation.screen.session.component.*
import com.kssidll.workin.presentation.theme.*
import dev.olshevski.navigation.reimagined.hilt.*

/// Route ///
@Composable
fun AddSessionRoute(
    onBack: () -> Unit
) {
    val addSessionViewModel: AddSessionViewModel = hiltViewModel()

    AddSessionScreen(
        onBack = onBack,
        onSessionAdd = {
            addSessionViewModel.addSession(it)
            onBack()
        },
        workouts = addSessionViewModel.getWorkouts()
            .collectAsState(initial = emptyList()).value
    )
}


/// Screen ///
@Composable
fun AddSessionScreen(
    onBack: () -> Unit,
    onSessionAdd: suspend (EditSessionDataSubpageState) -> Unit,
    workouts: List<Workout>,
) {
    EditSessionDataSubpage(
        onBack = onBack,
        workouts = workouts,
        submitButtonContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(
                        R.string
                            .confirm_add_session_description
                    ),
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.confirm_add_session_text),
                    fontSize = 20.sp
                )
            }
        },
        onSubmit = onSessionAdd,
    )
}


/// Screen Preview ///
@Preview(
    group = "AddSessionScreen",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "AddSessionScreen",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun AddSessionScreenPreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AddSessionScreen(
                onBack = {},
                onSessionAdd = {},
                workouts = listOf(),
            )
        }
    }
}

