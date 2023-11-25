package com.kssidll.workin.ui.screen.display.session.component

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.R
import com.kssidll.workin.ui.component.*
import com.kssidll.workin.ui.theme.*

/**
 * @param timerEnabled Whether the timer is enabled, False pauses the timer
 * @param onTimerEnd Callback called when the timer ends
 * @param onTimerEndEarly Callback called when timer is forced to end, called instead of [onTimerEnd]
 */
@Composable
fun SessionTimerPage(
    time: Int,
    timerEnabled: Boolean,
    onTimerEnd: () -> Unit = {},
    onTimerEndEarly: () -> Unit = {},
) {
    var forceTimerEnd by remember { mutableStateOf(false) }

    Column {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
        ) {
            CircularProgressTimer(
                enabled = timerEnabled,
                time = time,
                onTimerEnd = onTimerEnd,
                indicatorProgressingColor = MaterialTheme.colorScheme.tertiary,
                indicatorEndColor = MaterialTheme.colorScheme.errorContainer,
                forceEnd = forceTimerEnd,
                onTimerForceEnd = onTimerEndEarly,
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Button(
                onClick = {
                    forceTimerEnd = true
                },
                shape = RoundedCornerShape(23.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 32.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
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

@Preview(
    group = "Session Timer Page",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Session Timer Page",
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
                timerEnabled = false,
            )
        }
    }
}
