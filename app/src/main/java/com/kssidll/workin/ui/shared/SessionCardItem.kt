package com.kssidll.workin.ui.shared

import android.content.res.*
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.ui.addsession.*
import com.kssidll.workin.ui.theme.*

@Composable
fun SessionCardItem(
    session: SessionWithFullSessionWorkouts,
    onSelect: (SessionWithFullSessionWorkouts) -> Unit,
    showStartIcon: Boolean = false,
    onStartIconClick: (SessionWithFullSessionWorkouts) -> Unit = {},
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .border(
                    width = OutlinedTextFieldDefaults.UnfocusedBorderThickness,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(24.dp)
                )
                .clickable {
                    onSelect(session)
                }
                .animateContentSize()
        ) {
            Column {
                var isExpanded: Boolean by remember {
                    mutableStateOf(false)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = session.session.name,
                        fontSize = 20.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                ) {
                    Text(
                        text = session.session.description,
                        fontSize = 16.sp,
                        modifier = Modifier.alpha(0.8F),
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column {
                    decodeDaysToString(session.session.days, LocalContext.current).forEach {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp),
                        ) {
                            Text(
                                text = it,
                                fontSize = 16.sp,
                                modifier = Modifier.alpha(0.8F),
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    FilledTonalIconToggleButton(
                        checked = isExpanded,
                        onCheckedChange = {
                            isExpanded = it
                        }
                    ) {
                        if (isExpanded) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowUp,
                                contentDescription = stringResource(id = com.kssidll.workin.R.string.contract_session_workout_details)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = stringResource(id = com.kssidll.workin.R.string.expand_session_workout_details)
                            )
                        }
                    }
                }


                if (isExpanded) {
                    session.workouts.forEach { workout ->
                        Column {
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = workout.workout.name,
                                    fontSize = 18.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Box(modifier = Modifier.fillMaxWidth()) {
                                Box(
                                    modifier = Modifier
                                        .width(224.dp)
                                        .align(Alignment.Center)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .border(
                                                width = OutlinedTextFieldDefaults.UnfocusedBorderThickness,
                                                color = MaterialTheme.colorScheme.outline,
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                            .height(65.dp)
                                            .width(70.dp)
                                            .align(Alignment.CenterStart)
                                    ) {
                                        Text(
                                            text = workout.sessionWorkout.repetitionCount.toString(),
                                            modifier = Modifier
                                                .padding(
                                                    vertical = 6.dp,
                                                    horizontal = 12.dp
                                                )
                                                .align(Alignment.Center),
                                            maxLines = 2,
                                            color = MaterialTheme.colorScheme.onSurface,
                                        )
                                    }

                                    // couldn't find any arrangement that does this so behold! Boxes
                                    Box(
                                        modifier = Modifier
                                            .width(104.dp)
                                            .align(Alignment.CenterStart)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .width(34.dp)
                                                .height(65.dp)
                                                .align(Alignment.CenterEnd)
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxSize(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Rounded.Close,
                                                    contentDescription = ""
                                                )
                                            }
                                        }
                                    }


                                    Box(
                                        modifier = Modifier
                                            .border(
                                                width = OutlinedTextFieldDefaults.UnfocusedBorderThickness,
                                                color = MaterialTheme.colorScheme.outline,
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                            .height(65.dp)
                                            .width(120.dp)
                                            .align(Alignment.CenterEnd)
                                    ) {
                                        Text(
                                            text = RepetitionTypes.getById(workout.sessionWorkout.repetitionType)!!
                                                .getTranslation(),
                                            modifier = Modifier
                                                .padding(
                                                    vertical = 6.dp,
                                                    horizontal = 12.dp
                                                )
                                                .align(Alignment.Center),
                                            maxLines = 2,
                                            color = MaterialTheme.colorScheme.onSurface,
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Box(modifier = Modifier.fillMaxWidth()) {
                                val weightType =
                                    WeightTypes.getById(workout.sessionWorkout.weightType)!!
                                if (weightType.hideWeight) {
                                    Box(
                                        modifier = Modifier
                                            .border(
                                                width = OutlinedTextFieldDefaults.UnfocusedBorderThickness,
                                                color = MaterialTheme.colorScheme.outline,
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                            .height(65.dp)
                                            .width(223.dp)
                                            .align(Alignment.Center)
                                    ) {
                                        Text(
                                            text = weightType.getTranslation(),
                                            modifier = Modifier
                                                .padding(
                                                    vertical = 6.dp,
                                                    horizontal = 12.dp
                                                )
                                                .align(Alignment.Center),
                                            maxLines = 2,
                                            color = MaterialTheme.colorScheme.onSurface,
                                        )
                                    }
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .width(224.dp)
                                            .align(Alignment.Center)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .border(
                                                    width = OutlinedTextFieldDefaults.UnfocusedBorderThickness,
                                                    color = MaterialTheme.colorScheme.outline,
                                                    shape = RoundedCornerShape(12.dp)
                                                )
                                                .height(65.dp)
                                                .width(70.dp)
                                                .align(Alignment.CenterStart)
                                        ) {
                                            Text(
                                                text = workout.sessionWorkout.weight.toString(),
                                                modifier = Modifier
                                                    .padding(
                                                        vertical = 6.dp,
                                                        horizontal = 12.dp
                                                    )
                                                    .align(Alignment.Center),
                                                maxLines = 2,
                                                color = MaterialTheme.colorScheme.onSurface,
                                            )
                                        }

                                        // couldn't find any arrangement that does this so behold! Boxes
                                        Box(
                                            modifier = Modifier
                                                .width(104.dp)
                                                .align(Alignment.CenterStart)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .width(34.dp)
                                                    .height(OutlinedTextFieldDefaults.MinHeight)
                                                    .align(Alignment.CenterEnd)
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxSize(),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Rounded.Close,
                                                        contentDescription = ""
                                                    )
                                                }
                                            }
                                        }


                                        Box(
                                            modifier = Modifier
                                                .border(
                                                    width = OutlinedTextFieldDefaults.UnfocusedBorderThickness,
                                                    color = MaterialTheme.colorScheme.outline,
                                                    shape = RoundedCornerShape(12.dp)
                                                )
                                                .height(65.dp)
                                                .width(120.dp)
                                                .align(Alignment.CenterEnd)
                                        ) {
                                            Text(
                                                text = weightType.getTranslation(),
                                                modifier = Modifier
                                                    .padding(
                                                        vertical = 6.dp,
                                                        horizontal = 12.dp
                                                    )
                                                    .align(Alignment.Center),
                                                maxLines = 2,
                                                color = MaterialTheme.colorScheme.onSurface,
                                            )
                                        }
                                    }
                                }

                            }

                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        if (workout != session.workouts.last()) {
                            HorizontalDivider()
                        }
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

            }

            if (showStartIcon) {
                Column(
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    FilledIconButton(
                        onClick = {
                            onStartIconClick(session)
                        },
                        colors = IconButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary,
                            disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                            disabledContentColor = MaterialTheme.colorScheme.onTertiary,
                        ),
                        modifier = Modifier
                            .size(62.dp)
                            .padding(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.PlayArrow,
                            contentDescription = stringResource(id = com.kssidll.workin.R.string.start_session),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }

        }
    }
}

/// Card Preview ///
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
fun SessionCardItemPrieview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SessionCardItem(
                session = SessionWithFullSessionWorkouts(
                    session = Session(
                        name = "test session name 1",
                        description = "test session description 1",
                        days = EncodedDaysBuilder().addSaturday()
                            .addTuesday()
                            .build()
                    ),
                    workouts = listOf(
                        FullSessionWorkout(
                            sessionWorkout = SessionWorkout(
                                sessionId = 0,
                                workoutId = 0,
                                repetitionCount = 0,
                                repetitionType = RepetitionTypes.Repetitions.id,
                                weight = 0F,
                                weightType = WeightTypes.KGBodyMass.id,
                                order = 0,
                                restTime = 0,
                            ),
                            workout = Workout(
                                name = "test workout name 1",
                                description = "test workout description 1"
                            )
                        ),
                        FullSessionWorkout(
                            sessionWorkout = SessionWorkout(
                                sessionId = 0,
                                workoutId = 0,
                                repetitionCount = 0,
                                repetitionType = RepetitionTypes.RiR.id,
                                weight = 0F,
                                weightType = WeightTypes.BodyMass.id,
                                order = 0,
                                restTime = 0,
                            ),
                            workout = Workout(
                                name = "test workout name 2",
                                description = "test workout description 2"
                            )
                        ),
                    )
                ),
                onSelect = {},
                showStartIcon = true,
            )
        }
    }
}
