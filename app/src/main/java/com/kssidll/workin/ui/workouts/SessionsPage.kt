package com.kssidll.workin.ui.workouts

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.ui.shared.*
import com.kssidll.workin.ui.theme.*
import kotlinx.coroutines.flow.*

@Composable
fun SessionsPage(
    sessions: Flow<List<SessionWithWorkouts>>
) {
    val collectedSessions = sessions.collectAsState(initial = emptyList()).value

    SessionsPageContent(collectedSessions = collectedSessions)
}

@Composable
internal fun SessionsPageContent(
    collectedSessions: List<SessionWithWorkouts>
) {
    Column {
        PrimaryTopHeader {
            Text(text = stringResource(id = R.string.sessions))
        }

        LazyColumn {
            items(collectedSessions) {
                Row {
                    Text(text = it.session.name)
                    Spacer(modifier = Modifier.width(15.dp))
                    for (day in decodeDaysToString(
                        it.session.days,
                        LocalContext.current
                    )) {
                        Text(text = day)
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                }
            }
        }
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
            SessionsPageContent(
                collectedSessions = listOf(

                )
            )
        }
    }
}
