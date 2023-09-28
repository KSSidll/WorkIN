package com.kssidll.workin

import android.os.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.*
import com.kssidll.workin.presentation.screen.addsession.*
import com.kssidll.workin.presentation.screen.addworkout.*
import com.kssidll.workin.presentation.screen.editsession.*
import com.kssidll.workin.presentation.screen.editworkout.*
import com.kssidll.workin.presentation.screen.home.*
import com.kssidll.workin.presentation.screen.session.*
import dev.olshevski.navigation.reimagined.*
import kotlinx.parcelize.*

@Parcelize
sealed class Screen: Parcelable {
    data object Home: Screen()
    data object AddWorkout: Screen()
    data class EditWorkout(val id: Long): Screen()
    data object AddSession: Screen()
    data class EditSession(val id: Long): Screen()
    data class Session(val id: Long): Screen()
}

@Composable
fun Navigation(
    navController: NavController<Screen> = rememberNavController(startDestination = Screen.Home)
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
            is Screen.Home -> {
                HomeRoute(
                    onSessionStart = {
                        navController.navigate(Screen.Session(it))
                    },
                    onSessionClick = {
                        navController.navigate(Screen.EditSession(it))
                    },
                    onAddSession = {
                        navController.navigate(Screen.AddSession)
                    },
                    onWorkoutClick = {
                        navController.navigate(Screen.EditWorkout(it))
                    },
                    onAddWorkout = {
                        navController.navigate(Screen.AddWorkout)
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