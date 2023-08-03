package com.kssidll.workin.ui.theme

import android.os.*
import androidx.compose.foundation.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.*
import com.google.accompanist.systemuicontroller.*

private val LightColorScheme = lightColorScheme(
    primary = light_primary,
    onPrimary = light_onPrimary,
    secondary = light_secondary,
    onSecondary = light_onSecondary,
    error = light_error,
    onError = light_onError,
    errorContainer = light_errorContainer,
    onErrorContainer = light_onErrorContainer,
    outline = light_outline,
    background = light_background,
    onBackground = light_onBackground,
    surface = light_surface,
    onSurface = light_onSurface,
    scrim = light_scrim,
)

private val DarkColorScheme = darkColorScheme(
    primary = dark_primary,
    onPrimary = dark_onPrimary,
    secondary = dark_secondary,
    onSecondary = dark_onSecondary,
    error = dark_error,
    onError = dark_onError,
    errorContainer = dark_errorContainer,
    onErrorContainer = dark_onErrorContainer,
    outline = dark_outline,
    background = dark_background,
    onBackground = dark_onBackground,
    surface = dark_surface,
    onSurface = dark_onSurface,
    scrim = dark_scrim,
)

@Composable
fun WorkINTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme || Build.VERSION.SDK_INT < Build.VERSION_CODES.Q -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    val systemUiController = rememberSystemUiController()
    if (!view.isInEditMode) {
        SideEffect {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                systemUiController.setNavigationBarColor(
                    color = colorScheme.background,
                    darkIcons = !darkTheme
                )
                systemUiController.setStatusBarColor(
                    color = colorScheme.primary,
                    darkIcons = !darkTheme
                )
            } else {
                // Icon colors on versions lower than Q appear to be inverse of what we set them
                // te be
                systemUiController.setNavigationBarColor(
                    color = colorScheme.background,
                    darkIcons = darkTheme
                )
                systemUiController.setStatusBarColor(
                    color = colorScheme.primary,
                    darkIcons = darkTheme
                )
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}