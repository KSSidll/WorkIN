package com.kssidll.workin

import androidx.compose.runtime.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.kssidll.workin.NavigationDestinations.ADD_SESSION_ROUTE
import com.kssidll.workin.NavigationDestinations.ADD_WORKOUT_ROUTE
import com.kssidll.workin.NavigationDestinations.DASHBOARD_ROUTE
import com.kssidll.workin.NavigationDestinations.SESSION_ROUTE
import com.kssidll.workin.NavigationDestinations.WORKOUTS_ROUTE
import com.kssidll.workin.ui.addsession.*
import com.kssidll.workin.ui.addworkout.*
import com.kssidll.workin.ui.dashboard.*
import com.kssidll.workin.ui.session.*
import com.kssidll.workin.ui.workouts.*

object NavigationDestinations {
    const val DASHBOARD_ROUTE = "dashboard"
    const val WORKOUTS_ROUTE = "workouts"
    const val ADD_WORKOUT_ROUTE = "addworkout"
    const val ADD_SESSION_ROUTE = "addsession"
    const val SESSION_ROUTE = "session"
}

val LocalNavigation = compositionLocalOf<NavigationController> {
    error("No NavigationController provided")
}

/**
 * I hate this but couldn't think of a better way to handle ScreenWithBottomNavBar that would be
 * preview compatible than making a mock of this, unless you prefer passing several navigation
 * functions to everything that uses a navbar or not seeing the navbar on preview, of all the
 * options i got this monstrosity seems the least painful to deal with
 */
interface INavigationController {
    val currentlyAt: String

    /**
     * Should only be used in the navigation bar to avoid multiple "home"/dashboard screens on
     * backstack
     * Dashboard screen should ALWAYS be the first on backstack, if it's not, the navigation
     * logic is non functional, and something went terribly wrong in navigation design
     */
    fun popToDashboard()

    fun navigateDashboard()
    fun navigateWorkouts()
    fun navigateAddWorkout()
    fun navigateAddSession()
    fun navigateSession(sessionId: Long)
}

class NavigationController(
    private val navController: NavHostController
): INavigationController {
    override val currentlyAt: String
        get() = navController.currentDestination?.route!!

    override fun popToDashboard() {
        navController.popBackStack(DASHBOARD_ROUTE, false)
    }

    override fun navigateDashboard() {
        navController.navigate(DASHBOARD_ROUTE)
    }

    override fun navigateWorkouts() {
        navController.navigate(WORKOUTS_ROUTE)
    }

    override fun navigateAddWorkout() {
        navController.navigate(ADD_WORKOUT_ROUTE)
    }

    override fun navigateAddSession() {
        navController.navigate(ADD_SESSION_ROUTE)
    }

    override fun navigateSession(sessionId: Long) {
        navController.navigate("$SESSION_ROUTE/$sessionId")
    }
}

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
) {

    val navigationController = remember {
        NavigationController(navController)
    }

    CompositionLocalProvider(LocalNavigation provides navigationController) {
        NavHost(
            navController = navController,
            startDestination = DASHBOARD_ROUTE
        ) {
            composable(DASHBOARD_ROUTE) {
                DashboardRoute(
                    onSessionStart = {
                        navigationController.navigateSession(it)
                    },
                )
            }

            composable(WORKOUTS_ROUTE) {
                WorkoutsRoute(
                    onSessionStart = {
                        navigationController.navigateSession(it)
                    },
                    onAddWorkout = {
                        navigationController.navigateAddWorkout()
                    },
                    onAddSession = {
                        navigationController.navigateAddSession()
                    }
                )
            }

            composable(ADD_WORKOUT_ROUTE) {
                AddWorkoutRoute(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(ADD_SESSION_ROUTE) {
                AddSessionRoute(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                "$SESSION_ROUTE/{sessionId}",
                arguments = listOf(
                    navArgument("sessionId") { type = NavType.LongType }
                )
            ) {
                SessionRoute(
                    sessionId = it.arguments?.getLong("sessionId")!!,
                    onBack = {
                        navigationController.popToDashboard()
                    }
                )
            }
        }
    }
}