package com.kssidll.workin.ui.screen.modify.shared.session.component

import android.content.res.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.domain.*
import com.kssidll.workin.ui.theme.*

/**
 * @param enabled controls the enabled state of the page, interactions are disabled when false
 * @param encodedDays list of days encoded into [Byte], if not available in this form, should be acquired via [EncodedDaysBuilder]
 * @param onDaysChange the callback that is triggered when the input service updates the [encodedDays]. An updated value comes as a parameter of the callback
 */
@Composable
fun ModifySessionDaysPage(
    enabled: Boolean,
    encodedDays: Byte,
    onDaysChange: (newValue: Byte) -> Unit,
) {
    val checkedDays: SnapshotStateList<WeekDays> = remember {
        WeekDays.decode(encodedDays)
            .toMutableStateList()
    }

    LaunchedEffect(encodedDays) {
        checkedDays.clear()
        checkedDays.addAll(WeekDays.decode(encodedDays))
    }

    val onCheckedChange: (checked: Boolean, weekDays: WeekDays) -> Unit = { checked, day ->
        if (enabled) {
            if (checked) {
                onDaysChange((encodedDays + day.encoding).toByte())
            } else {
                onDaysChange((encodedDays - day.encoding).toByte())
            }
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .width(IntrinsicSize.Min)
        ) {
            WeekDays.entries.forEach {
                val interactionSource = remember { MutableInteractionSource() }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                        ) {
                            onCheckedChange((it in checkedDays).not(), it)
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Checkbox(
                            enabled = enabled,
                            checked = it in checkedDays,
                            onCheckedChange = { checked ->
                                onCheckedChange(checked, it)
                            },
                            modifier = Modifier.scale(1.2f)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = it.getTranslation(),
                            style = Typography.titleLarge,
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    group = "Modify Session Days Page",
    name = "Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Modify Session Days Page",
    name = "Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun ModifySessionDaysPagePreview() {
    WorkINTheme {
        Surface {
            ModifySessionDaysPage(
                enabled = true,
                encodedDays = EncodedDaysBuilder().addMonday()
                    .addSunday()
                    .build(),
                onDaysChange = {},
            )
        }
    }
}
