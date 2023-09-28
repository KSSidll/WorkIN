package com.kssidll.workin.ui.component

import android.content.res.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.domain.*
import com.kssidll.workin.ui.theme.*
import java.util.*

private val paddingBetweenValues = 3.dp

data class HorizontalWeekdayListColors(
    val border: Color,
    val foreground: Color,
    val markedDayBorder: Color,
    val highlightedDayBackground: Color,
    val highlightedDayForeground: Color,
)

@Composable
@ReadOnlyComposable
fun horizontalWeekdayListDefaultColors(
    border: Color = MaterialTheme.colorScheme.outline,
    foreground: Color = MaterialTheme.colorScheme.onSurface,
    markedDayBorder: Color = MaterialTheme.colorScheme.tertiary,
    highlightedDayBackground: Color = MaterialTheme.colorScheme.tertiaryContainer,
    highlightedDayForeground: Color = MaterialTheme.colorScheme.onTertiaryContainer,
) = HorizontalWeekdayListColors(
    border = border,
    foreground = foreground,
    markedDayBorder = markedDayBorder,
    highlightedDayBackground = highlightedDayBackground,
    highlightedDayForeground = highlightedDayForeground,
)

@Composable
fun HorizontalWeekdayList(
    highlightedDays: List<WeekDays> = listOf(),
    markCurrentDay: Boolean = false,
    colors: HorizontalWeekdayListColors = horizontalWeekdayListDefaultColors(),
) {
    var currentWeekDay: WeekDays by remember { mutableStateOf(WeekDays.getCurrent()) }

    if (markCurrentDay) {
        val currentDay = Calendar.getInstance()
            .get(Calendar.DAY_OF_WEEK)
        LaunchedEffect(currentDay) {
            currentWeekDay = WeekDays.getCurrent()
        }
    }

    Box(
        modifier = Modifier
            .border(
                width = Dp.Hairline,
                color = colors.border,
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        Row {
            Spacer(modifier = Modifier.width(paddingBetweenValues))
            WeekDays.entries.forEach {
                val marked = if (markCurrentDay) it == currentWeekDay else false
                WeekdayItem(
                    day = it,
                    highlighted = it in highlightedDays,
                    marked = marked,
                    colors = colors,
                )
            }
            Spacer(modifier = Modifier.width(paddingBetweenValues))
        }
    }
}

@Composable
private fun WeekdayItem(
    day: WeekDays,
    highlighted: Boolean,
    marked: Boolean,
    colors: HorizontalWeekdayListColors,
) {
    val markedModifier = if (marked)
        Modifier.border(
            width = OutlinedTextFieldDefaults.UnfocusedBorderThickness,
            color = colors.markedDayBorder,
            shape = CircleShape,
        )
    else Modifier

    val highlightedModifier = if (highlighted)
        Modifier.background(
            color = colors.highlightedDayBackground,
            shape = CircleShape,
        )
    else Modifier

    Box(
        modifier = Modifier.padding(paddingBetweenValues)
    ) {
        Box(
            modifier = markedModifier
                .then(highlightedModifier)
                .then(
                    Modifier
                        .width(30.dp)
                        .aspectRatio(0.9F)
                )
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = day.getTranslation()
                    .first()
                    .toString(),
                color = if (highlighted) colors.highlightedDayForeground else colors.foreground,
            )
        }
    }
}

@Preview(
    group = "Horizontal Weekday List",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Horizontal Weekday List",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun HorizontalWeekdayListPreview() {
    WorkINTheme {
        Surface {
            HorizontalWeekdayList(
                highlightedDays = listOf(
                    WeekDays.Saturday,
                    WeekDays.Wednesday,
                    WeekDays.Monday,
                    WeekDays.getCurrent()
                ),
                markCurrentDay = true,
            )
        }
    }
}
