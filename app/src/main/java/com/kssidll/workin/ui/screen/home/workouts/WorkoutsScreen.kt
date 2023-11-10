package com.kssidll.workin.ui.screen.home.workouts

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
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.ui.screen.home.workouts.component.*
import com.kssidll.workin.ui.theme.*
import dev.olshevski.navigation.reimagined.hilt.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@Composable
fun WorkoutsRoute(
    onSessionStart: (Long) -> Unit,
    onSessionClick: (Long) -> Unit,
    onAddSession: () -> Unit,
    onWorkoutClick: (Long) -> Unit,
    onAddWorkout: () -> Unit,
) {
    val viewModel: WorkoutsViewModel = hiltViewModel()

    WorkoutsScreen(
        onSessionStart = onSessionStart,
        onSessionClick = onSessionClick,
        onAddSession = onAddSession,
        onWorkoutClick = onWorkoutClick,
        onAddWorkout = onAddWorkout,
        workouts = viewModel.getAllWorkoutsDescFlow(),
        sessions = viewModel.getAllSessionsDescFlow(),
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun WorkoutsScreen(
    onSessionStart: (Long) -> Unit,
    onSessionClick: (Long) -> Unit,
    onAddSession: () -> Unit,
    onWorkoutClick: (Long) -> Unit,
    onAddWorkout: () -> Unit,
    workouts: Flow<List<Workout>>,
    sessions: Flow<List<SessionWithFullSessionWorkouts>>,
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
                    Column(modifier = Modifier.fillMaxSize()) {
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
                        ),
                        modifier = Modifier
                            .size(64.dp)
                            .shadow(6.dp, CircleShape)
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
                    ),
                    modifier = Modifier
                        .weight(1F, true)
                        .fillMaxHeight()
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
                    ),
                    modifier = Modifier
                        .weight(1F, true)
                        .fillMaxHeight()
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

@Preview(
    group = "Workouts Screen",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Workouts Screen",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun WorkoutsScreenPreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            WorkoutsScreen(
                onSessionStart = {},
                onSessionClick = {},
                onAddSession = {},
                onWorkoutClick = {},
                onAddWorkout = {},
                workouts = flowOf(),
                sessions = flowOf(),
            )
        }
    }
}

