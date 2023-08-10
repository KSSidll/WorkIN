package com.kssidll.workin.ui.shared.session

import android.content.res.*
import android.database.sqlite.SQLiteConstraintException
import androidx.activity.compose.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.ui.shared.*
import com.kssidll.workin.ui.theme.*
import kotlinx.coroutines.*

/// Data ///
data class EditSessionDataSubpageState(
    var name: String = String(),
    var description: String = String(),
    var days: Byte = 0,
    val workouts: List<SessionBuilderWorkout> = listOf(),
)


data class SessionBuilderWorkout(
    val workoutName: MutableState<String> = mutableStateOf(String()),
    val isError: MutableState<Boolean> = mutableStateOf(false),
    var workoutId: Long = 0,
    val repetitionCount: MutableState<Int> = mutableIntStateOf(0),
    val repetitionType: MutableState<RepetitionTypes> = mutableStateOf(RepetitionTypes.Repetitions),
    val weight: MutableState<Float> = mutableFloatStateOf(0F),
    val weightType: MutableState<WeightTypes> = mutableStateOf(WeightTypes.KG),
    val postRestTime: MutableState<Int> = mutableIntStateOf(0)
)


/// Subpage ///
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EditSessionDataSubpage(
    onBack: () -> Unit,
    workouts: List<Workout>,
    submitButtonContent: @Composable () -> Unit,
    onSubmit: suspend (EditSessionDataSubpageState) -> Unit,
    startState: EditSessionDataSubpageState = EditSessionDataSubpageState(),
    headerAdditionalContent: @Composable BoxScope.() -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    val namePageState = remember {
        EditSessionDataSubpageNameState(
            name = mutableStateOf(startState.name),
            description = mutableStateOf(startState.description),
        )
    }

    val daysPageState = remember { EditSessionDataSubpageDaysState() }

    val sessionBuilderState = remember {
        EditSessionDataSubpageBuilderState(
            workouts = startState.workouts
        )
    }

    // avoid recalculation on recomposition
    LaunchedEffect(Unit) {
        val saveStateDaysBuilder = EncodedDaysBuilder().add(startState.days)

        daysPageState.apply {
            mondayChecked.value = saveStateDaysBuilder.addedMonday
            tuesdayChecked.value = saveStateDaysBuilder.addedTuesday
            wednesdayChecked.value = saveStateDaysBuilder.addedWednesday
            thursdayChecked.value = saveStateDaysBuilder.addedThursday
            fridayChecked.value = saveStateDaysBuilder.addedFriday
            saturdayChecked.value = saveStateDaysBuilder.addedSaturday
            sundayChecked.value = saveStateDaysBuilder.addedSunday
        }
    }

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0F,
        pageCount = { 3 },
    )

    BackHandler(
        enabled = pagerState.currentPage != 0
    ) {
        scope.launch {
            pagerState.animateScrollToPage(pagerState.currentPage - 1)
        }
    }

    Column {
        SecondaryTopHeader(
            onIconClick = {
                if (pagerState.currentPage != 0) {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                } else {
                    onBack()
                }
            },
            icon = if (pagerState.currentPage == 0)
                Icons.Rounded.Close
            else
                Icons.Rounded.ArrowBack,
            iconDescription = if (pagerState.currentPage == 0)
                stringResource(id = R.string.cancel_add_session)
            else
                stringResource(id = R.string.previous_page_session_builder),
            additionalContent = headerAdditionalContent,
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) MaterialTheme
                        .colorScheme.tertiary else MaterialTheme.colorScheme.onSurface
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(16.dp)
                    )
                }
            }
        }

        HorizontalPager(
            state = pagerState,
            beyondBoundsPageCount = pagerState.pageCount,
        ) { page ->

            when (page) {
                0 -> {
                    EditSessionDataSubpageName(
                        onNext = {
                            scope.launch {
                                pagerState.animateScrollToPage(1)
                            }
                        },
                        state = namePageState,
                    )
                }

                1 -> {
                    EditSessionDataSubpageDays(
                        onNext = {
                            scope.launch {
                                pagerState.animateScrollToPage(2)
                            }
                        },
                        state = daysPageState,
                    )
                }

                2 -> {
                    EditSessionDataSubpageBuilder(
                        onSubmit = {
                            namePageState.nameBlankError.value = namePageState.name.value.isBlank()

                            sessionBuilderState.workouts.forEach { workout ->
                                if (workout.workout.workoutName.value.isBlank()) {
                                    workout.workout.isError.value = true
                                }
                            }

                            if (namePageState.nameBlankError.value) {
                                scope.launch {
                                    pagerState.animateScrollToPage(0)
                                }
                            } else if (!sessionBuilderState.workouts.any { workout -> workout.workout.isError.value }) {
                                // already in the page with the error so no need to scroll

                                val days = EncodedDaysBuilder()

                                if (daysPageState.mondayChecked.value) days.addMonday()
                                if (daysPageState.tuesdayChecked.value) days.addTuesday()
                                if (daysPageState.wednesdayChecked.value) days.addWednesday()
                                if (daysPageState.thursdayChecked.value) days.addThursday()
                                if (daysPageState.fridayChecked.value) days.addFriday()
                                if (daysPageState.saturdayChecked.value) days.addSaturday()
                                if (daysPageState.sundayChecked.value) days.addSunday()

                                scope.launch {
                                    try {
                                        onSubmit(
                                            EditSessionDataSubpageState(
                                                name = namePageState.name.value,
                                                description = namePageState.description.value,
                                                days = days.build(),
                                                workouts = sessionBuilderState.workouts.map {
                                                    it.workout
                                                }
                                            )
                                        )
                                    } catch (_: SQLiteConstraintException) {
                                        namePageState.nameDuplicateError.value = true
                                        pagerState.animateScrollToPage(0)
                                    }
                                }

                            }
                        },
                        submitButtonContent = {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                submitButtonContent()
                            }
                        },
                        userWorkouts = workouts,
                        onScreen = pagerState.currentPage == 2,
                        state = sessionBuilderState,
                    )
                }
            }
        }
    }
}

/// Subpage Preview ///
@Preview(
    group = "EditWorkoutDataSubpage",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "EditWorkoutDataSubpage",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun EditSessionDataSubpagePreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            EditSessionDataSubpage(
                workouts = listOf(),
                submitButtonContent = {
                    Text(text = "Submit Button", fontSize = 20.sp)
                },
                onBack = {},
                onSubmit = {},
            )
        }
    }
}
