package com.kssidll.workin.ui.component


import android.content.res.Configuration.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.domain.*
import com.kssidll.workin.ui.theme.*
import kotlinx.coroutines.*

/**
 * An mm:ss timer with a circular progress indicator around it
 * @param enabled Whether the timer is enabled, False will pause the timer
 * @param time Total timer time in seconds, changing it will reset the timer, value of 0 is treated like timer end
 * @param onTimerEnd Callback called when timer ends
 * @param indicatorProgressingColor Color to use for the indicator when the timer is progressing
 * @param indicatorEndColor Color to use for the indicator when the timer ended
 * @param resetIndicatorOnEnd Whether to reset the indicator when timer ends, defaults to True
 * @param forceEnd Setting this to True forces the timer to end immediately
 * @param onTimerForceEnd Callback called when timer is forced to end, called instead of [onTimerEnd]
 */
@Composable
fun CircularProgressTimer(
    enabled: Boolean,
    time: Int,
    onTimerEnd: () -> Unit,
    indicatorProgressingColor: Color,
    indicatorEndColor: Color,
    resetIndicatorOnEnd: Boolean = true,
    forceEnd: Boolean = false,
    onTimerForceEnd: () -> Unit = {},
) {
    var timerFinished by remember { mutableStateOf(false) }
    var remainingTime by remember { mutableIntStateOf(time.coerceAtLeast(0)) }
    var indicatorColor by remember { mutableStateOf(indicatorProgressingColor) }

    LaunchedEffect(forceEnd) {
        if (forceEnd) {
            indicatorColor = indicatorEndColor
            remainingTime = 0
            timerFinished = true
            onTimerForceEnd()
        }
    }

    LaunchedEffect(time) {
        remainingTime = time.coerceAtLeast(0)
    }

    LaunchedEffect(enabled, remainingTime) {
        if (enabled) {
            // the first 1s shouldn't be delayed
            if (remainingTime != time) delay(1000)

            if (remainingTime > 0) {
                remainingTime -= 1
            } else {
                indicatorColor = indicatorEndColor
                timerFinished = true
                onTimerEnd()
            }
        }
    }

    Box {
        val targetProgress: Float =
            if (time == 0 || timerFinished) when (resetIndicatorOnEnd) {
                true -> 1F
                false -> 0F
            }
            else remainingTime / time.toFloat()

        val progress = animateFloatAsState(
            targetValue = targetProgress,
            animationSpec = tween(1000, easing = { it }), // 1s animation with constant speed
            label = "timer change value animation"
        )

        CircularProgressIndicator(
            progress = {
                progress.value
            },
            modifier = Modifier.size(256.dp),
            color = indicatorColor,
            strokeWidth = 14.dp,
        )

        Text(
            text = formatTime(remainingTime),
            fontSize = 40.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(
    group = "CircularProgressTimer",
    name = "Dark",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    group = "CircularProgressTimer",
    name = "Light",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
private fun CircularProgressTimerPreview() {
    WorkINTheme {
        Surface {
            CircularProgressTimer(
                enabled = true,
                time = 31,
                onTimerEnd = {},
                indicatorProgressingColor = Color.Blue,
                indicatorEndColor = Color.Blue,
                resetIndicatorOnEnd = true,
                onTimerForceEnd = {},
            )
        }
    }
}
