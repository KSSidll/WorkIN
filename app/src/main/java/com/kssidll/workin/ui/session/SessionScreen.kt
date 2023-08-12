package com.kssidll.workin.ui.session

import android.content.res.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.hilt.navigation.compose.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.ui.shared.*
import com.kssidll.workin.ui.theme.*
import kotlinx.coroutines.*

/// Route ///
@Composable
fun SessionRoute(
    sessionId: Long,
    onBack: () -> Unit,
) {
    val sessionViewModel: SessionViewModel = hiltViewModel()

    var isLoading: Boolean by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        sessionViewModel.fetchSession(sessionId)
        isLoading = false
    }

    if (!isLoading) {
        SessionScreen(
            session = sessionViewModel.session,
            updateSessionWorkout = {
                sessionViewModel.updateWorkoutSettings(it)
            },
            addLog = {
                sessionViewModel.addLog(it)
            },
            onBack = onBack,
        )
    }
}


/// Screen ///
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SessionScreen(
    session: SessionWithFullSessionWorkouts,
    updateSessionWorkout: (SessionWorkout) -> Unit,
    addLog: (SessionWorkoutLog) -> Unit,
    onBack: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState {
        // each workout has a page with the workout and the rest timer
        // last workout doesn't have a rest timer but we add a finish screen instead
        session.workouts.size.times(2)
    }

    Column {
        SecondaryTopHeader(
            onIconClick = {
                onBack()
            },
            icon = Icons.Default.Close
        ) {

        }

        var blockPagerScroll: Boolean by remember { mutableStateOf(false) }

        HorizontalPager(
            state = pagerState,
            beyondBoundsPageCount = 1,
            userScrollEnabled = !blockPagerScroll
        ) { page ->
            if (page.mod(2) == 0) {
                SessionWorkoutPage(
                    workout = session.workouts[page.div(2)],
                    onLogSubmit = {
                        addLog(
                            SessionWorkoutLog(
                                workoutId = session.workouts[page.div(2)].workout.id,
                                repetitionCount = it.repetitionCount,
                                repetitionType = it.repetitionType.id,
                                weight = it.weight,
                                weightType = it.weightType.id,
                            )
                        )

                        if (it.changeSessionWorkoutRepsSettings.value) {
                            session.workouts[page.div(2)].sessionWorkout.apply {
                                this.repetitionCount = it.repetitionCount
                                this.repetitionType = it.repetitionType.id
                            }
                        }

                        if (it.changeSessionWorkoutWeightSettings.value) {
                            session.workouts[page.div(2)].sessionWorkout.apply {
                                this.weight = it.weight
                                this.weightType = it.weightType.id
                            }
                        }

                        if (it.changeSessionWorkoutRepsSettings.value || it.changeSessionWorkoutWeightSettings.value) {
                            updateSessionWorkout(session.workouts[page.div(2)].sessionWorkout)
                        }

                        scope.launch {
                            pagerState.animateScrollToPage(page + 1)
                        }
                    },
                )
            } else {
                // last workout page isn't a timer, but a session finish screen
                if (page != pagerState.pageCount - 1) {
                    val timerTime = session.workouts[page.div(2)].sessionWorkout.restTime
                    if (pagerState.currentPage == page && timerTime != 0) blockPagerScroll = true

                    SessionTimerPage(
                        time = timerTime,
                        activateTimer = pagerState.currentPage == page,
                        onTimerEnd = {
                            scope.launch {
                                if (timerTime != 0) {
                                    delay(400)
                                    pagerState.animateScrollToPage(page + 1)
                                    session.workouts[page.div(2)].sessionWorkout.restTime = 0
                                    blockPagerScroll = false
                                }
                            }
                        },
                        onEndEarlyClick = {
                            scope.launch {
                                if (timerTime != 0) {
                                    delay(150)
                                    pagerState.animateScrollToPage(page + 1)
                                    session.workouts[page.div(2)].sessionWorkout.restTime = 0
                                    blockPagerScroll = false
                                }
                            }
                        }
                    )
                } else {
                    SessionFinishPage(
                        onButtonClick = onBack,
                    )
                }
            }
        }

    }
}


/// Screen Preview ///
@Preview(
    group = "SessionScreen",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "SessionScreen",
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
                session = SessionWithFullSessionWorkouts(
                    session = Session(
                        name = "test session name 1",
                        description = "test session description 1",
                        days = EncodedDaysBuilder().addSaturday()
                            .addTuesday()
                            .build()
                    ),
                    workouts = listOf(
                        FullSessionWorkout(
                            sessionWorkout = SessionWorkout(
                                sessionId = 0,
                                workoutId = 0,
                                repetitionCount = 0,
                                repetitionType = RepetitionTypes.Repetitions.id,
                                weight = 0F,
                                weightType = WeightTypes.KGBodyMass.id,
                                order = 0,
                                restTime = 0,
                            ),
                            workout = Workout(
                                name = "test workout name 1",
                                description = "test workout description 1"
                            )
                        ),
                        FullSessionWorkout(
                            sessionWorkout = SessionWorkout(
                                sessionId = 0,
                                workoutId = 0,
                                repetitionCount = 0,
                                repetitionType = RepetitionTypes.RiR.id,
                                weight = 0F,
                                weightType = WeightTypes.BodyMass.id,
                                order = 0,
                                restTime = 0,
                            ),
                            workout = Workout(
                                name = "test workout name 2",
                                description = "test workout description 2"
                            )
                        )
                    )
                ),
                updateSessionWorkout = {},
                addLog = {},
                onBack = {},
            )
        }
    }
}
