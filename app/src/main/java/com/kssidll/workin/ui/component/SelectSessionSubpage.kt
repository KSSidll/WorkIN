package com.kssidll.workin.ui.component

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.*
import com.kssidll.workin.ui.theme.*

@Composable
fun SelectSessionSubpage(
    collectedSessions: List<SessionWithWorkouts>,
    onSelect: (SessionWithWorkouts) -> Unit,
    onStartIconClick: ((SessionWithWorkouts) -> Unit)? = null,
) {
    Column {
        Spacer(modifier = Modifier.height(12.dp))

        // TODO search

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            items(collectedSessions) {
                SessionCardItem(
                    session = it,
                    onClick = onSelect,
                    onStartIconClick = onStartIconClick,
                )
            }
        }

    }
}

@Preview(
    group = "Select Session Subpage",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Select Session Subpage",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SelectSessionSubpagePreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SelectSessionSubpage(
                collectedSessions = listOf(
                    SessionWithWorkouts(
                        session = Session(
                            name = "test session name 1",
                            description = "test session description 1",
                            days = EncodedDaysBuilder().addSaturday()
                                .addTuesday()
                                .build()
                        ),
                        workouts = listOf(
                            FullSessionWorkout(
                                sessionId = 0,
                                workout = Workout(
                                    name = "test workout name 1",
                                    description = "test workout description 1"
                                ),
                                repetitionCount = 0,
                                repetitionType = RepetitionTypes.Repetitions,
                                weight = 0F,
                                weightType = WeightTypes.KGBodyMass,
                                order = 0,
                                restTime = 0,
                            ),
                            FullSessionWorkout(
                                sessionId = 0,
                                workout = Workout(
                                    name = "test workout name 2",
                                    description = "test workout description 2"
                                ),
                                repetitionCount = 0,
                                repetitionType = RepetitionTypes.RiR,
                                weight = 0F,
                                weightType = WeightTypes.BodyMass,
                                order = 0,
                                restTime = 0,
                            ),
                        )
                    ),

                    ),
                onSelect = {},
                onStartIconClick = {},
            )
        }
    }
}
