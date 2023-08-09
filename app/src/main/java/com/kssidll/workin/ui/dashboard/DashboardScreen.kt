package com.kssidll.workin.ui.dashboard

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import com.kssidll.workin.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.mock.*
import com.kssidll.workin.ui.shared.*
import com.kssidll.workin.ui.theme.*
import java.util.*

/// Route ///
@Composable
fun DashboardRoute(
    onSessionStart: (Long) -> Unit,
    onSessionClick: (Long) -> Unit,
) {
    val dashboardViewModel: DashboardViewModel = hiltViewModel()

    ScreenWithBottomNavBar {
        DashboardScreen(
            onSessionStart = onSessionStart,
            onSessionClick = onSessionClick,
            sessions = dashboardViewModel.getAllSessionsDescFlow()
                .collectAsState(initial = emptyList()).value,
        )
    }
}


/// Screen ///
@Composable
fun DashboardScreen(
    onSessionStart: (Long) -> Unit,
    onSessionClick: (Long) -> Unit,
    sessions: List<SessionWithFullSessionWorkouts>,
) {
    val currentDayEncoding = (1).shl(
        Calendar.getInstance()
            .get(Calendar.DAY_OF_WEEK) - 1
    )

    val currentDay = DaysEncoding.getByEncoding(currentDayEncoding.toByte())
    val nextDay = DaysEncoding.getByEncoding(
        currentDayEncoding.shl(1)
            .toByte()
    )

    val sessionsToday = mutableListOf<SessionWithFullSessionWorkouts>()
    val sessionsTomorrow = mutableListOf<SessionWithFullSessionWorkouts>()

    sessions.forEach {
        // requires incremental order of days to avoid duplicates
        val decodedDays = DaysEncoding.decode(it.session.days)
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

    // TODO change to something scrollable that works better than this here
    LazyColumn {
        item {
            if (sessionsToday.isNotEmpty()) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.sessions_today),
                            fontSize = 20.sp
                        )
                    }

                    sessionsToday.forEach {
                        SessionCardItem(
                            session = it,
                            onSelect = { clickedSession ->
                                onSessionClick(clickedSession.session.id)
                            },
                            showStartIcon = true,
                            onStartIconClick = { startedSession ->
                                onSessionStart(startedSession.session.id)
                            }
                        )
                    }
                }
            }
        }

        item {
            if (sessionsTomorrow.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.sessions_tomorrow),
                        fontSize = 20.sp
                    )
                }

                Column {
                    sessionsTomorrow.forEach {
                        SessionCardItem(
                            session = it,
                            onSelect = { clickedSession ->
                                onSessionClick(clickedSession.session.id)
                            },
                            showStartIcon = true,
                            onStartIconClick = { startedSession ->
                                onSessionStart(startedSession.session.id)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

        }

    }
}


/// Screen Preview ///
@Preview(
    group = "DashboardScreen",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "DashboardScreen",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun DashboardScreenPreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ScreenWithBottomNavBar(
                navigationController = NavigationControllerMock(NavigationDestinations.DASHBOARD_ROUTE)
            ) {
                DashboardScreen(
                    onSessionStart = {},
                    onSessionClick = {},
                    sessions = listOf()
                )
            }
        }
    }
}