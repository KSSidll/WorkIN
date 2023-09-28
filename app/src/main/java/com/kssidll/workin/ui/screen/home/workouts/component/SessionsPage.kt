package com.kssidll.workin.ui.screen.home.workouts.component

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.ui.component.*
import com.kssidll.workin.ui.component.topbar.*
import com.kssidll.workin.ui.theme.*

@Composable
fun SessionsPage(
    collectedSessions: List<SessionWithFullSessionWorkouts>,
    onSessionStart: (Long) -> Unit,
    onSessionClick: (Long) -> Unit,
) {
    Column {
        PrimaryTopHeader {
            Text(
                text = stringResource(id = R.string.sessions),
                fontSize = 21.sp,
            )
        }

        SelectSessionSubpage(
            collectedSessions = collectedSessions,
            onSelect = {
                onSessionClick(it.session.id)
            },
            showStartIcon = true,
            onStartIconClick = {
                onSessionStart(it.session.id)
            }
        )
    }
}

@Preview(
    group = "Sessions Page",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Sessions Page",
    name = "Light",
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

                ),
                onSessionStart = {},
                onSessionClick = {},
            )
        }
    }
}