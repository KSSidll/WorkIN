package com.kssidll.workin.ui.component

import android.content.res.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.ui.theme.*

@Composable
fun WorkoutCardItem(
    workout: Workout,
    onSelect: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(24.dp),
                )
                .clickable {
                    onSelect()
                }
        ) {
            Column {
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = workout.name,
                        fontSize = 20.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    if (workout.description.isBlank()) {
                        Text(
                            text = stringResource(id = R.string.workout_no_description_text),
                            fontSize = 16.sp,
                            modifier = Modifier.alpha(0.45F)
                        )
                    } else {
                        Text(
                            text = workout.description,
                            fontSize = 16.sp,
                            modifier = Modifier.alpha(0.8F)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Preview(
    group = "SessionCardItem",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "SessionCardItem",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun WorkoutCardItemPrieview() {
    WorkINTheme {
        Surface {
            WorkoutCardItem(
                workout = Workout(
                    name = "test workout name 1",
                    description = "test workout description 1"
                ),
                onSelect = {},
            )
        }
    }
}
