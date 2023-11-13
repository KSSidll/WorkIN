package com.kssidll.workin.ui.screen.session

import android.content.res.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.*
import com.kssidll.workin.ui.component.*
import com.kssidll.workin.ui.screen.session.component.*
import com.kssidll.workin.ui.theme.*
import dev.olshevski.navigation.reimagined.hilt.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@Composable
fun SessionRoute(
    sessionId: Long,
    onBack: () -> Unit,
) {
    val viewModel: SessionViewModel = hiltViewModel()

    var isLoading: Boolean by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        viewModel.fetchSession(sessionId)
        isLoading = false
    }

    if (!isLoading) {
        SessionScreen(
            onBack = onBack,
            session = viewModel.session,
            requestSessionWorkoutLogById = {
                viewModel.newestLogsByWorkoutIdFlow(it)
            },
            updateSessionWorkout = {
                viewModel.updateWorkoutSettings(it)
            },
            addLog = {
                viewModel.addLog(it)
            },
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SessionScreen(
    onBack: () -> Unit,
    session: SessionWithWorkouts,
    requestSessionWorkoutLogById: (workoutId: Long) -> Flow<List<SessionWorkoutLog>>,
    updateSessionWorkout: (FullSessionWorkout) -> Unit,
    addLog: (SessionWorkoutLog) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val pages: SnapshotStateList<@Composable (onScreen: Boolean) -> Unit> =
        remember { mutableStateListOf() }

    val pagerState = rememberPagerState {
        // each workout has a page with the workout and the rest timer
        // last workout doesn't have a rest timer but we add a finish screen instead
        session.workouts.size.times(2)
    }

    var blockPagerScroll: Boolean by remember { mutableStateOf(false) }

    LaunchedEffect(session) {
        pages.clear()
        pagerState.scrollToPage(
            pagerState.initialPage,
            pagerState.initialPageOffsetFraction
        ) // reset pager in case it wasn't on default values

        session.workouts.forEachIndexed { index, sessionWorkout ->
            pages.add { _ ->
                SessionWorkoutPage(
                    workout = sessionWorkout,
                    lastWorkoutLogs = requestSessionWorkoutLogById(sessionWorkout.workout.id).collectAsState(
                        initial = emptyList()
                    ).value,
                    onLogSubmit = {
                        addLog(
                            SessionWorkoutLog(
                                workoutId = sessionWorkout.workout.id,
                                repetitionCount = it.repetitionCount,
                                repetitionType = it.repetitionType.value,
                                weight = it.weight,
                                weightType = it.weightType.value,
                            )
                        )

                        if (it.changeSessionWorkoutRepsSettings.value) {
                            sessionWorkout.apply {
                                this.repetitionCount = it.repetitionCount
                                this.repetitionType = it.repetitionType.value.id
                            }
                        }

                        if (it.changeSessionWorkoutWeightSettings.value) {
                            sessionWorkout.apply {
                                this.weight = it.weight
                                this.weightType = it.weightType.value.id
                            }
                        }

                        if (it.changeSessionWorkoutRepsSettings.value || it.changeSessionWorkoutWeightSettings.value) {
                            updateSessionWorkout(sessionWorkout)
                        }

                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                )
            }

            if (index == session.workouts.lastIndex) {
                pages.add { _ ->
                    SessionFinishPage(
                        onButtonClick = onBack,
                    )
                }

                return@forEachIndexed // all items were computed, early return so we don't add more by accident
            }

            pages.add { onScreen ->
                if (onScreen && sessionWorkout.restTime != 0) blockPagerScroll = true

                SessionTimerPage(
                    time = sessionWorkout.restTime,
                    activateTimer = onScreen,
                    onTimerEnd = {
                        scope.launch {
                            if (sessionWorkout.restTime != 0) {
                                delay(400)
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                sessionWorkout.restTime = 0
                                blockPagerScroll = false
                            }
                        }
                    },
                    onEndEarlyClick = {
                        scope.launch {
                            if (sessionWorkout.restTime != 0) {
                                delay(150)
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                sessionWorkout.restTime = 0
                                blockPagerScroll = false
                            }
                        }
                    }
                )
            }
        }
    }

    Column {
        WorkINTopAppBar(
            navigationIcon = navigationIcon(
                type = NavigationIcon.Types.Close,
                onClick = onBack,
                contentDescription = stringResource(id = R.string.stop_session),
            ),
        )

        HorizontalPager(
            state = pagerState,
            beyondBoundsPageCount = 1,
            userScrollEnabled = !blockPagerScroll
        ) { page ->
            if (pages.isNotEmpty()) {
                pages[page](pagerState.currentPage == page)
            }
        }

    }
}

@Preview(
    group = "Session Screen",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Session Screen",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SessionScreenPreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SessionScreen(
                onBack = {},
                session = SessionWithWorkouts(
                    session = Session(
                        name = "test session name 1",
                        description = "test session description 1",
                        days = EncodedDaysBuilder().addSaturday()
                            .addTuesday()
                            .build()
                    ),
                    workouts = listOf(
                        FullSessionWorkout(
                            sessionId = 0,
                            workout = Workout(
                                name = "test workout name 1",
                                description = "test workout description 1"
                            ),
                            repetitionCount = 0,
                            repetitionType = RepetitionTypes.Repetitions,
                            weight = 0F,
                            weightType = WeightTypes.KGBodyMass,
                            order = 0,
                            restTime = 0,
                        ),
                        FullSessionWorkout(
                            sessionId = 0,
                            workout = Workout(
                                name = "test workout name 2",
                                description = "test workout description 2"
                            ),
                            repetitionCount = 0,
                            repetitionType = RepetitionTypes.RiR,
                            weight = 0F,
                            weightType = WeightTypes.BodyMass,
                            order = 0,
                            restTime = 0,
                        )
                    )
                ),
                requestSessionWorkoutLogById = { flowOf() },
                updateSessionWorkout = {},
                addLog = {},
            )
        }
    }
}
