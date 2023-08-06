package com.kssidll.workin.ui.workouts

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.ui.shared.*
import com.kssidll.workin.ui.theme.*


@Composable
fun SessionsPage(
    collectedSessions: List<SessionWithFullSessionWorkouts>
) {
    Column {
        PrimaryTopHeader {
            Text(text = stringResource(id = R.string.sessions))
        }

        SelectSessionSubpage(
            collectedSessions = collectedSessions,
            onSelect = {
                // TODO add session edition
            },
            showStartIcon = true,
            onStartIconClick = {

            }
        )
    }
}

/// Page Preview ///
@Preview(
    group = "SessionsPage",
    name = "Sessions Page Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "SessionsPage",
    name = "Sessions Page Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SessionsPagePreview() {
    WorkINTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,
        ) {
            SessionsPage(
                collectedSessions = listOf(

                )
            )
        }
    }
}
