package com.kssidll.workin.ui.addsession

import android.content.res.*
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
import androidx.hilt.navigation.compose.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.ui.shared.*
import com.kssidll.workin.ui.theme.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/// Route ///
@Composable
fun AddSessionRoute(
    onBack: () -> Unit
) {
    val addSessionViewModel: AddSessionViewModel = hiltViewModel()

    AddSessionScreen(
        onBack = onBack,
        onSessionAdd = {
            addSessionViewModel.addSession(it)
            onBack()
        },
        workouts = addSessionViewModel.getWorkouts()
    )
}


/// Screen ///
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddSessionScreen(
    onBack: () -> Unit,
    onSessionAdd: (AddSessionData) -> Unit,
    workouts: Flow<List<Workout>>,
) {
    val scope = rememberCoroutineScope()

    val nameText = remember {
        mutableStateOf(String())
    }
    val nameError = remember {
        mutableStateOf(false)
    }

    val descriptionText = remember {
        mutableStateOf(String())
    }

    val mondayChecked = remember {
        mutableStateOf(false)
    }

    val tuesdayChecked = remember {
        mutableStateOf(false)
    }

    val wednesdayChecked = remember {
        mutableStateOf(false)
    }

    val thursdayChecked = remember {
        mutableStateOf(false)
    }

    val fridayChecked = remember {
        mutableStateOf(false)
    }

    val saturdayChecked = remember {
        mutableStateOf(false)
    }

    val sundayChecked = remember {
        mutableStateOf(false)
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
            icon = if (pagerState.currentPage == 0) Icons.Rounded.Close else Icons.Rounded.ArrowBack,
            iconDescription = stringResource(id = R.string.cancel_add_session),
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
            pageSize = PageSize.Fill,
            beyondBoundsPageCount = 2,
        ) { page ->
            when (page) {
                0 -> {
                    NamePage(
                        onNext = {
                            scope.launch {
                                pagerState.animateScrollToPage(1)
                            }
                        },
                        nameText = nameText,
                        nameError = nameError,
                        descriptionText = descriptionText,
                    )
                }

                1 -> {
                    DaysPage(
                        onNext = {
                            scope.launch {
                                pagerState.animateScrollToPage(2)
                            }
                        },
                        mondayChecked = mondayChecked,
                        tuesdayChecked = tuesdayChecked,
                        wednesdayChecked = wednesdayChecked,
                        thursdayChecked = thursdayChecked,
                        fridayChecked = fridayChecked,
                        saturdayChecked = saturdayChecked,
                        sundayChecked = sundayChecked,
                    )
                }

                2 -> {
                    SessionBuilderPage(
                        onCreate = {
                            nameError.value = nameText.value.isBlank()
                            it.forEach { workout ->
                                if (workout.workoutName.value.isBlank()) {
                                    workout.isError.value = true
                                }
                            }

                            if (nameError.value) {
                                scope.launch {
                                    pagerState.animateScrollToPage(0)
                                }
                            } else if (!it.any { workout -> workout.isError.value }) {
                                // already in the page with the error so no need to scroll

                                val days = EncodedDaysBuilder()

                                // if spam go brrr, honestly no idea how to not do that here
                                if (mondayChecked.value) days.addMonday()
                                if (tuesdayChecked.value) days.addTuesday()
                                if (wednesdayChecked.value) days.addWednesday()
                                if (thursdayChecked.value) days.addThursday()
                                if (fridayChecked.value) days.addFriday()
                                if (saturdayChecked.value) days.addSaturday()
                                if (sundayChecked.value) days.addSunday()

                                onSessionAdd(
                                    AddSessionData(
                                        name = nameText.value,
                                        description = descriptionText.value,
                                        days = days.build(),
                                        workouts = it,
                                    )
                                )

                            }
                        },
                        userWorkouts = workouts,
                        active = pagerState.currentPage == 2
                    )
                }
            }
        }
    }
}


/// Screen Preview ///
@Preview(
    group = "AddSessionScreen",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "AddSessionScreen",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun AddSessionScreenPreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AddSessionScreen(
                onBack = {},
                onSessionAdd = {},
                workouts = flowOf(),
            )
        }
    }
}

