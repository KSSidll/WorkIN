package com.kssidll.workin

import android.os.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.*
import com.kssidll.workin.ui.screen.home.*
import com.kssidll.workin.ui.screen.modify.add.session.*
import com.kssidll.workin.ui.screen.modify.add.workout.*
import com.kssidll.workin.ui.screen.modify.edit.session.*
import com.kssidll.workin.ui.screen.modify.edit.workout.*
import com.kssidll.workin.ui.screen.session.*
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

fun defaultNavigateContentTransformation(
    screenWidth: Int,
): ContentTransform {
    val easing = CubicBezierEasing(
        0.48f,
        0.19f,
        0.05f,
        1.03f
    )

    return slideInHorizontally(
        animationSpec = tween(
            500,
            easing = easing
        ),
        initialOffsetX = { screenWidth }) + fadeIn(
        tween(
            250,
            50
        )
    ) togetherWith slideOutHorizontally(
        animationSpec = tween(
            500,
            easing = easing
        ),
        targetOffsetX = { -screenWidth }) + fadeOut(
        tween(
            250,
            50
        )
    )
}

fun defaultPopContentTransformation(
    screenWidth: Int,
): ContentTransform {
    val easing = CubicBezierEasing(
        0.48f,
        0.19f,
        0.05f,
        1.03f
    )

    return slideInHorizontally(
        animationSpec = tween(
            500,
            easing = easing
        ),
        initialOffsetX = { -screenWidth }) + fadeIn(
        tween(
            250,
            50
        )
    ) togetherWith slideOutHorizontally(
        animationSpec = tween(
            500,
            easing = easing
        ),
        targetOffsetX = { screenWidth }) + fadeOut(
        tween(
            250,
            50
        )
    )
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

    AnimatedNavHost(
        controller = navController,
        transitionSpec = { action, _, _ ->
            if (action != NavAction.Pop) {
                defaultNavigateContentTransformation(screenWidth)
            } else {
                defaultPopContentTransformation(screenWidth)
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