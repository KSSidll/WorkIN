package com.kssidll.workin.ui.session

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.ui.theme.*

/// Item ///
@Composable
fun WorkoutStatsItem(
    repetitionCount: String,
    repetitionType: RepetitionTypes,
    weight: String,
    weightType: WeightTypes,
) {
    if (weightType.hideWeight) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = repetitionCount,
                    fontSize = 20.sp,
                    modifier = Modifier.alpha(0.8F)
                )

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                        .alpha(0.8F)
                )

                Text(
                    text = repetitionType.getTranslation(),
                    fontSize = 20.sp,
                    modifier = Modifier.alpha(0.8F),
                )
            }

            Row {
                Text(
                    text = weightType.getTranslation(),
                    fontSize = 20.sp,
                    modifier = Modifier.alpha(0.8F),
                )
            }
        }
    } else {
        Row {
            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = repetitionCount,
                        fontSize = 20.sp,
                        modifier = Modifier.alpha(0.8F)
                    )

                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                            .alpha(0.8F)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = weight,
                        fontSize = 20.sp,
                        modifier = Modifier.alpha(0.8F)
                    )

                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                            .alpha(0.8F)
                    )
                }
            }

            Column {
                Text(
                    text = repetitionType.getTranslation(),
                    fontSize = 20.sp,
                    modifier = Modifier.alpha(0.8F),
                )

                Text(
                    text = weightType.getTranslation(),
                    fontSize = 20.sp,
                    modifier = Modifier.alpha(0.8F),
                )
            }
        }
    }
}


/// Item Preview ///
@Preview(
    group = "WorkoutStatsItem",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "WorkoutStatsItem",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun WorkoutStatsItemPreview() {
    WorkINTheme {
        Surface {
            WorkoutStatsItem(
                repetitionCount = "0",
                repetitionType = RepetitionTypes.Repetitions,
                weight = 0F.toString(),
                weightType = WeightTypes.KGBodyMass,
            )
        }
    }
}

@Preview(
    group = "WorkoutStatsItem",
    name = "Dark Hidden Weight",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "WorkoutStatsItem",
    name = "Light Hidden Weight",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun WorkoutStatsItemHiddenWeightPreview() {
    WorkINTheme {
        Surface {
            WorkoutStatsItem(
                repetitionCount = "0",
                repetitionType = RepetitionTypes.Repetitions,
                weight = 0F.toString(),
                weightType = WeightTypes.BodyMass,
            )
        }
    }
}
