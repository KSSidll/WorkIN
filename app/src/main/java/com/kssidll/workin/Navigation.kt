package com.kssidll.workin

import android.os.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.*
import com.kssidll.workin.ui.addsession.*
import com.kssidll.workin.ui.addworkout.*
import com.kssidll.workin.ui.dashboard.*
import com.kssidll.workin.ui.editsession.*
import com.kssidll.workin.ui.editworkout.*
import com.kssidll.workin.ui.session.*
import com.kssidll.workin.ui.workouts.*
import dev.olshevski.navigation.reimagined.*
import kotlinx.parcelize.*

val LocalNavigation = compositionLocalOf<NavController<Screen>> {
    error("No NavigationController provided")
}

@Parcelize
sealed class Screen: Parcelable {
    data object Dashboard: Screen()
    data object Workouts: Screen()
    data object AddWorkout: Screen()
    data class EditWorkout(val id: Long): Screen()
    data object AddSession: Screen()
    data class EditSession(val id: Long): Screen()
    data class Session(val id: Long): Screen()
}

@Composable
fun Navigation(
    navController: NavController<Screen> = rememberNavController(startDestination = Screen.Dashboard)
) {
    NavBackHandler(controller = navController)

    val onBack: () -> Unit = {
        navController.apply {
            if (backstack.entries.size > 1) pop()
        }
    }

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val easing = CubicBezierEasing(
        0.48f,
        0.19f,
        0.05f,
        1.03f
    )

    CompositionLocalProvider(LocalNavigation provides navController) {
        AnimatedNavHost(
            controller = navController,
            transitionSpec = { action, _, _ ->
                if (action != NavAction.Pop) {
                    slideInHorizontally(
                        animationSpec = tween(
                            600,
                            easing = easing
                        ),
                        initialOffsetX = { screenWidth }) + fadeIn(
                        tween(
                            300,
                            100
                        )
                    ) togetherWith slideOutHorizontally(
                        animationSpec = tween(
                            600,
                            easing = easing
                        ),
                        targetOffsetX = { -screenWidth }) + fadeOut(
                        tween(
                            300,
                            100
                        )
                    )
                } else {
                    slideInHorizontally(
                        animationSpec = tween(
                            600,
                            easing = easing
                        ),
                        initialOffsetX = { -screenWidth }) + fadeIn(
                        tween(
                            300,
                            100
                        )
                    ) togetherWith slideOutHorizontally(
                        animationSpec = tween(
                            600,
                            easing = easing
                        ),
                        targetOffsetX = { screenWidth }) + fadeOut(
                        tween(
                            300,
                            100
                        )
                    )
                }
            },
        ) { screen ->
            when (screen) {
                is Screen.Dashboard -> {
                    DashboardRoute(
                        onSessionStart = {
                            navController.navigate(Screen.Session(it))
                        },
                        onSessionClick = {
                            navController.navigate(Screen.EditSession(it))
                        }
                    )
                }

                is Screen.Workouts -> {
                    WorkoutsRoute(
                        onSessionStart = {
                            navController.navigate(Screen.Session(it))
                        },
                        onAddWorkout = {
                            navController.navigate(Screen.AddWorkout)
                        },
                        onWorkoutClick = {
                            navController.navigate(Screen.EditWorkout(it))
                        },
                        onAddSession = {
                            navController.navigate(Screen.AddSession)
                        },
                        onSessionClick = {
                            navController.navigate(Screen.EditSession(it))
                        },
                    )
                }

                is Screen.AddWorkout -> {
                    AddWorkoutRoute(
                        onBack = onBack,
                    )
                }

                is Screen.EditWorkout -> {
                    EditWorkoutRoute(
                        workoutId = screen.id,
                        onBack = onBack,
                    )
                }

                is Screen.AddSession -> {
                    AddSessionRoute(
                        onBack = onBack,
                    )
                }

                is Screen.EditSession -> {
                    EditSessionRoute(
                        sessionId = screen.id,
                        onBack = onBack,
                    )
                }

                is Screen.Session -> {
                    SessionRoute(
                        sessionId = screen.id,
                        onBack = onBack,
                    )
                }

            }

        }
    }
}