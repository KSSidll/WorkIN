package com.kssidll.workin

import androidx.compose.runtime.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.kssidll.workin.NavigationDestinations.ADD_WORKOUT_ROUTE
import com.kssidll.workin.NavigationDestinations.DASHBOARD_ROUTE
import com.kssidll.workin.NavigationDestinations.WORKOUTS_ROUTE
import com.kssidll.workin.ui.addworkout.*
import com.kssidll.workin.ui.dashboard.*
import com.kssidll.workin.ui.workouts.*

object NavigationDestinations {
    const val DASHBOARD_ROUTE = "dashboard"
    const val WORKOUTS_ROUTE = "workouts"
    const val ADD_WORKOUT_ROUTE = "addworkout"
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

                )
            }

            composable(WORKOUTS_ROUTE) {
                WorkoutsRoute(
                    onAddWorkout = {
                        navigationController.navigateAddWorkout()
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
        }
    }
}