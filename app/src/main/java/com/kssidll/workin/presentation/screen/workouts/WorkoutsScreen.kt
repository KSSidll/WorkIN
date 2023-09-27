package com.kssidll.workin.presentation.screen.workouts

import android.content.res.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.presentation.component.*
import com.kssidll.workin.presentation.theme.*
import dev.olshevski.navigation.reimagined.*
import dev.olshevski.navigation.reimagined.hilt.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/// Route ///
@Composable
fun WorkoutsRoute(
    onSessionStart: (Long) -> Unit,
    onAddWorkout: () -> Unit,
    onWorkoutClick: (Long) -> Unit,
    onAddSession: () -> Unit,
    onSessionClick: (Long) -> Unit
) {
    val workoutsViewModel: WorkoutsViewModel = hiltViewModel()

    ScreenWithBottomNavBar {
        WorkoutsScreen(
            onSessionStart = onSessionStart,
            workouts = workoutsViewModel.getAllWorkoutsDescFlow(),
            sessions = workoutsViewModel.getAllSessionsDescFlow(),
            onAddWorkout = onAddWorkout,
            onWorkoutClick = onWorkoutClick,
            onAddSession = onAddSession,
            onSessionClick = onSessionClick,
        )
    }
}


/// Screen ///
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WorkoutsScreen(
    onSessionStart: (Long) -> Unit,
    workouts: Flow<List<Workout>>,
    sessions: Flow<List<SessionWithFullSessionWorkouts>>,
    onAddWorkout: () -> Unit,
    onWorkoutClick: (Long) -> Unit,
    onAddSession: () -> Unit,
    onSessionClick: (Long) -> Unit,
) {
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val pagerState = rememberPagerState(
                initialPage = 1,
                initialPageOffsetFraction = 0F,
                pageCount = { 2 }
            )

            Box(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth()
            ) {
                HorizontalPager(
                    state = pagerState,
                    pageSize = PageSize.Fill,
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        when (it) {
                            0 -> {
                                Box {
                                    val collectedSessions =
                                        sessions.collectAsState(initial = emptyList()).value
                                    SessionsPage(
                                        collectedSessions = collectedSessions,
                                        onSessionStart = onSessionStart,
                                        onSessionClick = onSessionClick
                                    )
                                }
                            }

                            1 -> {
                                Box {
                                    val collectedWorkouts =
                                        workouts.collectAsState(initial = emptyList()).value

                                    WorkoutsPage(
                                        collectedWorkouts = collectedWorkouts,
                                        onWorkoutClick = {
                                            onWorkoutClick(it.id)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    FilledIconButton(
                        modifier = Modifier
                            .size(64.dp)
                            .shadow(6.dp, CircleShape),
                        onClick = {
                            when (pagerState.currentPage) {
                                0 -> onAddSession()
                                1 -> onAddWorkout()
                            }
                        },
                        colors = IconButtonColors(
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = Color.Transparent,
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add new item",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(bottom = 12.dp)
            ) {
                val iconBottomPadding = 5.dp

                IconButton(
                    enabled = pagerState.currentPage != 0,
                    modifier = Modifier
                        .weight(1F, true)
                        .fillMaxHeight(),
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    },
                    colors = IconButtonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        disabledContainerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4F),
                        disabledContentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.fitness_center),
                        contentDescription = stringResource(
                            id = R.string
                                .navigate_to_dashboard_description
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = iconBottomPadding)
                    )
                }

                VerticalDivider()

                IconButton(
                    enabled = pagerState.currentPage != 1,
                    modifier = Modifier
                        .weight(1F, true)
                        .fillMaxHeight(),
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    },
                    colors = IconButtonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        disabledContainerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4F),
                        disabledContentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.event_list),
                        contentDescription = stringResource(
                            id = R.string
                                .navigate_to_dashboard_description
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = iconBottomPadding)
                    )
                }
            }
        }
    }
}


/// Screen Preview ///
@Preview(
    group = "WorkoutsScreen",
    name = "Workouts Screen Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "WorkoutsScreen",
    name = "Workouts Screen Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun WorkoutsScreenPreview() {
    WorkINTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            ScreenWithBottomNavBar(
                navigationController = rememberNavController(startDestination = Screen.Workouts)
            ) {
                WorkoutsScreen(
                    onSessionStart = {},
                    workouts = flowOf(),
                    sessions = flowOf(),
                    onAddWorkout = {},
                    onWorkoutClick = {},
                    onAddSession = {},
                    onSessionClick = {},
                )
            }
        }
    }
}

