package com.kssidll.workin.ui.screen.home.dashboard

import android.content.res.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.*
import com.kssidll.workin.ui.component.*
import com.kssidll.workin.ui.theme.*
import dev.olshevski.navigation.reimagined.hilt.*

@Composable
fun DashboardRoute(
    onSessionStart: (Long) -> Unit,
    onSessionClick: (Long) -> Unit,
) {
    val viewModel: DashboardViewModel = hiltViewModel()

    DashboardScreen(
        onSessionStart = onSessionStart,
        onSessionClick = onSessionClick,
        sessions = viewModel.allSessionsWithWorkoutsFlow()
            .collectAsState(emptyList()).value,
    )
}

@Composable
fun DashboardScreen(
    onSessionStart: (Long) -> Unit,
    onSessionClick: (Long) -> Unit,
    sessions: List<SessionWithWorkouts>,
) {
    val currentDayEncoding = WeekDays.currentDayEncoding()
    val currentDay = WeekDays.getByEncoding(currentDayEncoding.toByte())
    val nextDay = WeekDays.getByEncoding(
        currentDayEncoding.shl(1)
            .toByte()
    )

    val sessionsToday = mutableListOf<SessionWithWorkouts>()
    val sessionsTomorrow = mutableListOf<SessionWithWorkouts>()

    sessions.forEach {
        // requires incremental order of days to avoid duplicates
        val decodedDays = WeekDays.decode(it.session.days)
            .reversed()

        for (dayByte in decodedDays) {
            if (dayByte == currentDay) {
                sessionsToday.add(it)
                break
            }
            if (dayByte == nextDay) {
                sessionsTomorrow.add(it)
                break
            }
        }
    }

    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .verticalScroll(
                    state = rememberScrollState()
                )
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.sessions_today),
                    fontSize = 20.sp,
                )
            }

            if (sessionsToday.isEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.no_sessions_today),
                        fontSize = 16.sp,
                        modifier = Modifier.alpha(0.8F)
                    )
                }
            } else {
                sessionsToday.forEach {
                    SessionCardItem(
                        session = it,
                        onClick = { clickedSession ->
                            onSessionClick(clickedSession.session.id)
                        },
                        onStartIconClick = { startedSession ->
                            onSessionStart(startedSession.session.id)
                        }
                    )
                }
            }

            if (sessionsTomorrow.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.sessions_tomorrow),
                        fontSize = 20.sp,
                    )
                }

                Column {
                    sessionsTomorrow.forEach {
                        SessionCardItem(
                            session = it,
                            onClick = { clickedSession ->
                                onSessionClick(clickedSession.session.id)
                            },
                            onStartIconClick = { startedSession ->
                                onSessionStart(startedSession.session.id)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview(
    group = "Dashboard Screen",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Dashboard Screen",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun DashboardScreenPreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            DashboardScreen(
                onSessionStart = {},
                onSessionClick = {},
                sessions = listOf()
            )
        }
    }
}
