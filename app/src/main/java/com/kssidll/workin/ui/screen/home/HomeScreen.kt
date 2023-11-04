package com.kssidll.workin.ui.screen.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.painter.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.res.*
import com.kssidll.workin.R
import com.kssidll.workin.ui.screen.home.HomeScreenLocations.Companion.getByOrdinal
import com.kssidll.workin.ui.screen.home.component.*
import com.kssidll.workin.ui.screen.home.dashboard.*
import com.kssidll.workin.ui.screen.home.workouts.*
import kotlinx.coroutines.*

// Important, the order of items in the enum determines the order that the locations appear in
// on the bottom navigation bar
enum class HomeScreenLocations(
    val initial: Boolean = false,
) {
    Dashboard(initial = true),
    Workouts,
    ;

    val description: String
        @Composable
        @ReadOnlyComposable
        get() = when (this) {
            Dashboard -> stringResource(R.string.navigate_to_dashboard_description)
            Workouts -> stringResource(R.string.navigate_to_workouts_description)
        }

    val imageVector: ImageVector?
        @Composable
        get() = when (this) {
            Dashboard -> Icons.Rounded.Home
            Workouts -> null
        }

    val painter: Painter?
        @Composable
        get() = when (this) {
            Dashboard -> null
            Workouts -> painterResource(R.drawable.exercise)
        }

    companion object {
        private val idMap = entries.associateBy { it.ordinal }
        fun getByOrdinal(ordinal: Int) = idMap[ordinal]
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeRoute(
    onSessionStart: (Long) -> Unit,
    onSessionClick: (Long) -> Unit,
    onAddSession: () -> Unit,
    onWorkoutClick: (Long) -> Unit,
    onAddWorkout: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = HomeScreenLocations.entries.first { it.initial }.ordinal,
        initialPageOffsetFraction = 0F,
        pageCount = { HomeScreenLocations.entries.size },
    )

    Scaffold(
        bottomBar = {
            BottomHomeNavigationBar(
                currentLocation = getByOrdinal(pagerState.currentPage)!!,
                onLocationChange = {
                    scope.launch {
                        pagerState.animateScrollToPage(it.ordinal)
                    }
                },
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            HomeScreenContent(
                pagerState = pagerState,
                onSessionStart = onSessionStart,
                onSessionClick = onSessionClick,
                onAddSession = onAddSession,
                onWorkoutClick = onWorkoutClick,
                onAddWorkout = onAddWorkout,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreenContent(
    pagerState: PagerState,
    onSessionStart: (Long) -> Unit,
    onSessionClick: (Long) -> Unit,
    onAddSession: () -> Unit,
    onWorkoutClick: (Long) -> Unit,
    onAddWorkout: () -> Unit,
) {
    HorizontalPager(
        state = pagerState,
        userScrollEnabled = false,
    ) {
        when (getByOrdinal(it)!!) {
            HomeScreenLocations.Dashboard -> {
                DashboardRoute(
                    onSessionStart = onSessionStart,
                    onSessionClick = onSessionClick,
                )
            }

            HomeScreenLocations.Workouts -> {
                WorkoutsRoute(
                    onSessionStart = onSessionStart,
                    onSessionClick = onSessionClick,
                    onAddSession = onAddSession,
                    onWorkoutClick = onWorkoutClick,
                    onAddWorkout = onAddWorkout,
                )
            }
        }
    }
}