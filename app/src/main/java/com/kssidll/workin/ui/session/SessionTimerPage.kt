package com.kssidll.workin.ui.session

import android.content.res.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.R
import com.kssidll.workin.ui.shared.*
import com.kssidll.workin.ui.theme.*
import kotlinx.coroutines.*
import java.util.*

/// Page ///
/**
 * @param onTimerEnd: Optional event that triggers on normal timer end
 * @param onEndEarlyClick: Optional event that triggers on early timer end
 * @param activateTimer: flag that starts the timer if set as true
 */
@Composable
fun SessionTimerPage(
    time: Int,
    activateTimer: Boolean,
    onTimerEnd: () -> Unit = {},
    onEndEarlyClick: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    var timerStarted: Boolean by remember {
        mutableStateOf(false)
    }

    var timerFinished: Boolean by remember {
        mutableStateOf(false)
    }

    var remainingMiliseconds: Long by remember {
        mutableLongStateOf(0)
    }

    if (activateTimer && !timerStarted) {
        LaunchedEffect(Unit) {
            scope.launch {
                timerStarted = true

                val timeStarted = Calendar.getInstance().timeInMillis

                while (remainingMiliseconds >= 0 && !timerFinished) {
                    remainingMiliseconds =
                        time.times(1000) - (Calendar.getInstance().timeInMillis - timeStarted)
                    delay(100)

                    if (remainingMiliseconds - 100 <= 0) {
                        timerFinished = true
                        onTimerEnd()
                    }
                }

                remainingMiliseconds = 0
            }
        }
    }


    Column {
        Column(
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box {

                val progress: Float = if (remainingMiliseconds > 0) {
                    remainingMiliseconds / time.times(1000)
                        .toFloat()
                } else 1F

                CircularProgressIndicator(
                    progress = animateFloatAsState(
                        targetValue = progress,
                        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
                        label = "timer change value animation"
                    ).value,
                    modifier = Modifier.size(256.dp),
                    strokeWidth = 14.dp,
                    color = if (remainingMiliseconds > 0) {
                        MaterialTheme.colorScheme.tertiary
                    } else MaterialTheme.colorScheme.errorContainer,
                )

                Text(
                    text = formatTime(
                        remainingMiliseconds.div(1000)
                            .toInt()
                    ),
                    fontSize = 40.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    timerFinished = true
                    onEndEarlyClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 32.dp),
                shape = RoundedCornerShape(23.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.timer_end_early_text),
                        fontSize = 24.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

    }
}

/// Page Preview ///
@Preview(
    group = "SessionTimerPage",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "SessionTimerPage",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SessionTimerPagePreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SessionTimerPage(
                time = 137,
                activateTimer = false,
            )
        }
    }
}
