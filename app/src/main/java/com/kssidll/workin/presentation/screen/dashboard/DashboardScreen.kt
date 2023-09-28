package com.kssidll.workin.presentation.screen.dashboard

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
import com.kssidll.workin.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.*
import com.kssidll.workin.presentation.component.*
import com.kssidll.workin.presentation.screen.dashboard.component.*
import com.kssidll.workin.presentation.theme.*
import dev.olshevski.navigation.reimagined.*
import dev.olshevski.navigation.reimagined.hilt.*
import java.util.*

/// Route ///
@Composable
fun DashboardRoute(
    onSessionStart: (Long) -> Unit,
    onSessionClick: (Long) -> Unit,
) {
    val dashboardViewModel: DashboardViewModel = hiltViewModel()

    DashboardScreen(
        onSessionStart = onSessionStart,
        onSessionClick = onSessionClick,
        sessions = dashboardViewModel.getAllSessionsDescFlow()
            .collectAsState(emptyList()).value,
    )
}

/// Screen ///
@Composable
fun DashboardScreen(
    onSessionStart: (Long) -> Unit,
    onSessionClick: (Long) -> Unit,
    sessions: List<SessionWithFullSessionWorkouts>,
) {
    Scaffold(
        bottomBar = {
            BottomDashboardNavigationBar()
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            DashboardScreenContent(
                onSessionStart = onSessionStart,
                onSessionClick = onSessionClick,
                sessions = sessions,
            )
        }
    }
}

@Composable
private fun DashboardScreenContent(
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(id = R.string.sessions_today),
                    fontSize = 20.sp
                )
            }

            if (sessionsToday.isEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = stringResource(id = R.string.no_sessions_today),
                        fontSize = 16.sp,
                        modifier = Modifier.alpha(0.8F),
                    )
                }
            } else {
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

            Spacer(modifier = Modifier.height(12.dp))

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
            CompositionLocalProvider(LocalNavigation provides rememberNavController(Screen.Dashboard)) {
                DashboardScreen(
                    onSessionStart = {},
                    onSessionClick = {},
                    sessions = listOf()
                )
            }
        }
    }
}