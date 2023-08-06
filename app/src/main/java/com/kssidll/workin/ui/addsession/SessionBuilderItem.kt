package com.kssidll.workin.ui.addsession

import android.annotation.*
import android.content.res.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.sharp.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.core.text.*
import com.kssidll.workin.R
import com.kssidll.workin.ui.shared.*
import com.kssidll.workin.ui.theme.*
import org.burnoutcrew.reorderable.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.SessionBuilderItem(
    reorderableState: ReorderableLazyListState,
    workouts: MutableList<AddSessionWorkoutData>,
    thisWorkout: AddSessionWorkoutData,
    onWorkoutSearch: (Long) -> Unit
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .animateItemPlacement()
        ) {

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .border(
                                width = OutlinedTextFieldDefaults.UnfocusedBorderThickness,
                                color = if (thisWorkout.isError.value) MaterialTheme.colorScheme.error
                                    else MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .height(OutlinedTextFieldDefaults.MinHeight)
                            .width(223.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onWorkoutSearch(thisWorkout.id)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = thisWorkout.workoutName.value.ifBlank { stringResource(id = R.string.no_workout_session_builder_item) },
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp),
                            maxLines = 2,
                            color = if (thisWorkout.isError.value) MaterialTheme.colorScheme.error
                                else MaterialTheme.colorScheme.onSurface
                        )

                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    OutlinedTextField(
                        singleLine = true,
                        modifier = Modifier.width(70.dp),
                        value = thisWorkout.repetitionCount.value.toString(),
                        onValueChange = { newValue ->
                            if (newValue.isBlank()) {
                                thisWorkout.repetitionCount.value = 0
                            } else if (newValue.isDigitsOnly()) {
                                thisWorkout.repetitionCount.value =
                                    newValue.toInt()
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Column(
                        modifier = Modifier.height(
                            OutlinedTextFieldDefaults.MinHeight
                        ),
                    ) {
                        Spacer(modifier = Modifier.height(5.dp))
                        FilledIconButton(
                            modifier = Modifier
                                .weight(1F)
                                .width(18.dp),
                            onClick = {
                                thisWorkout.repetitionCount.value =
                                    thisWorkout.repetitionCount.value.inc()
                            },
                            colors = IconButtonColors(
                                contentColor = MaterialTheme.colorScheme.onTertiary,
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                disabledContentColor = MaterialTheme.colorScheme.onTertiary,
                                disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Sharp.KeyboardArrowUp,
                                contentDescription = stringResource(id = R.string.increase_repetition_count_1_description),
                            )
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        FilledIconButton(
                            modifier = Modifier
                                .weight(1F)
                                .width(18.dp),
                            onClick = {
                                thisWorkout.repetitionCount.apply {
                                    if (this.value == 0) return@apply

                                    this.value = this.value.dec()
                                }
                            },
                            colors = IconButtonColors(
                                contentColor = MaterialTheme.colorScheme.onTertiary,
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                disabledContentColor = MaterialTheme.colorScheme.onTertiary,
                                disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Sharp
                                    .KeyboardArrowDown,
                                contentDescription = stringResource(id = R.string.lower_repetition_count_1_description),
                            )
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))

                    var menuExpanded: Boolean by remember {
                        mutableStateOf(false)
                    }

                    Box(
                        modifier = Modifier
                            .border(
                                width = OutlinedTextFieldDefaults.UnfocusedBorderThickness,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .height(OutlinedTextFieldDefaults.MinHeight)
                            .width(120.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                menuExpanded = true
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = thisWorkout.repetitionType.value.getTranslation(),
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp),
                            maxLines = 2
                        )

                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = {
                                menuExpanded = false
                            },
                            modifier = Modifier.background
                                (MaterialTheme.colorScheme.surfaceContainer)

                        ) {
                            for (item in RepetitionTypes.entries) {
                                if (item == thisWorkout.repetitionType.value) continue

                                DropdownMenuItem(
                                    text = {
                                        Text(text = item.getTranslation())
                                    },
                                    onClick = {
                                        menuExpanded = false
                                        thisWorkout.repetitionType.value = item
                                    },
                                )
                            }
                        }
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    if (!thisWorkout.weightType.value.hideWeight) {
                        Row {
                            OutlinedTextField(
                                singleLine = true,
                                modifier = Modifier.width(70.dp),
                                value = thisWorkout.weight.value.toString(),
                                onValueChange = { newValue ->
                                    if (newValue.isBlank()) {
                                        thisWorkout.weight.value = 0F
                                    } else if (newValue.isDigitsOnly()) {
                                        thisWorkout.weight.value =
                                            newValue.toFloat()
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Decimal
                                ),
                            )

                            Spacer(modifier = Modifier.width(2.dp))

                            Column(
                                modifier = Modifier.height(
                                    OutlinedTextFieldDefaults.MinHeight
                                ),
                            ) {
                                Spacer(modifier = Modifier.height(5.dp))
                                FilledIconButton(
                                    modifier = Modifier
                                        .weight(1F)
                                        .width(18.dp),
                                    onClick = {
                                        thisWorkout.weight.value =
                                            thisWorkout.weight.value.plus(0.5F)
                                    },
                                    colors = IconButtonColors(
                                        contentColor = MaterialTheme.colorScheme.onTertiary,
                                        containerColor = MaterialTheme.colorScheme.tertiary,
                                        disabledContentColor = MaterialTheme.colorScheme.onTertiary,
                                        disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Sharp.KeyboardArrowUp,
                                        contentDescription = stringResource(id = R.string.increase_weight_05_description),
                                    )
                                }

                                Spacer(modifier = Modifier.height(2.dp))

                                FilledIconButton(
                                    modifier = Modifier
                                        .weight(1F)
                                        .width(18.dp),
                                    onClick = {
                                        thisWorkout.weight.apply {
                                            if (this.value == 0F) return@apply

                                            this.value = this.value.minus(0.5F)
                                        }
                                    },
                                    colors = IconButtonColors(
                                        contentColor = MaterialTheme.colorScheme.onTertiary,
                                        containerColor = MaterialTheme.colorScheme.tertiary,
                                        disabledContentColor = MaterialTheme.colorScheme.onTertiary,
                                        disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Sharp
                                            .KeyboardArrowDown,
                                        contentDescription = stringResource(id = R.string.lower_weight_05_description),
                                    )
                                }
                                Spacer(modifier = Modifier.height(5.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                    }


                    var menuExpanded: Boolean by remember {
                        mutableStateOf(false)
                    }

                    Box(
                        modifier = Modifier
                            .border(
                                width = OutlinedTextFieldDefaults.UnfocusedBorderThickness,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .height(OutlinedTextFieldDefaults.MinHeight)
                            .width(
                                if (thisWorkout.weightType.value.hideWeight) {
                                    223.dp
                                } else 120.dp
                            )
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                menuExpanded = true
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = thisWorkout.weightType.value.getTranslation(),
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp),
                            maxLines = 2
                        )

                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = {
                                menuExpanded = false
                            },
                            modifier = Modifier.background
                                (MaterialTheme.colorScheme.surfaceContainer)

                        ) {
                            for (item in WeightTypes.entries) {
                                if (item == thisWorkout.weightType.value) continue

                                DropdownMenuItem(
                                    text = {
                                        Text(text = item.getTranslation())
                                    },
                                    onClick = {
                                        menuExpanded = false
                                        thisWorkout.weightType.value = item
                                    },
                                )
                            }
                        }
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                if (thisWorkout != workouts.last()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        FilledIconButton(
                            onClick = {
                                thisWorkout.postRestTime.value =
                                    thisWorkout.postRestTime.value.minus(10)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.keyboard_double_arrow_left),
                                contentDescription = stringResource(id = R.string.lower_rest_time_10_description),
                                modifier = Modifier.size(28.dp),
                            )
                        }

                        FilledIconButton(
                            onClick = {
                                thisWorkout.postRestTime.value =
                                    thisWorkout.postRestTime.value.dec()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.KeyboardArrowLeft,
                                contentDescription = stringResource(id = R.string.lower_rest_time_1_description),
                                modifier = Modifier.size(28.dp),
                            )
                        }

                        Spacer(modifier = Modifier.width(5.dp))

                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                progress = 1F,
                                color = MaterialTheme.colorScheme.outline,
                                modifier = Modifier.size(85.dp),
                            )
                            Text(
                                text = formatTime(thisWorkout.postRestTime.value),
                                fontSize = 18.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(5.dp))

                        FilledIconButton(
                            onClick = {
                                thisWorkout.postRestTime.value =
                                    thisWorkout.postRestTime.value.inc()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.KeyboardArrowRight,
                                contentDescription = stringResource(id = R.string.increase_rest_time_1_description),
                                modifier = Modifier.size(28.dp),
                            )
                        }

                        FilledIconButton(
                            onClick = {
                                thisWorkout.postRestTime.value =
                                    thisWorkout.postRestTime.value.plus(10)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.keyboard_double_arrow_right),
                                contentDescription = stringResource(id = R.string.increase_rest_time_10_description),
                                modifier = Modifier.size(28.dp),
                            )
                        }
                    }
                }

            }

            Column(
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                FilledIconButton(
                    onClick = {
                        workouts.remove(thisWorkout)
                    },
                    colors = IconButtonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.errorContainer,
                        disabledContentColor = MaterialTheme.colorScheme.onErrorContainer,
                    ),
                    modifier = Modifier
                        .size(40.dp)
                        .padding(6.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete_forever),
                        contentDescription = stringResource(id = R.string.remove_item_session_builder),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .detectReorder(reorderableState)
                    .align(Alignment.CenterEnd)
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 16.dp)
                ) {
                    Icon(
                        modifier = Modifier.detectReorder(
                            reorderableState
                        ),
                        imageVector = Icons.Rounded.KeyboardArrowUp,
                        contentDescription = stringResource(id = R.string.change_item_order)
                    )
                    Icon(
                        modifier = Modifier.detectReorder(
                            reorderableState
                        ),
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = stringResource(id = R.string.change_item_order)
                    )
                    Icon(
                        modifier = Modifier.detectReorder(
                            reorderableState
                        ),
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = stringResource(id = R.string.change_item_order)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }

}


/// Item Preview ///
@Preview(
    group = "SessionBuilderItem",
    name = "Session Builder Hide Weight Item Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "SessionBuilderItem",
    name = "Session Builder Item Hide Weight Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@SuppressLint("UnrememberedMutableState")
@Composable
fun SessionBuilderItemHideWeightPreview() {
    WorkINTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface,
        ) {
            val workouts = mutableListOf(
                AddSessionWorkoutData(
                    id = 0,
                    repetitionCount = mutableIntStateOf(3),
                    repetitionType = mutableStateOf(RepetitionTypes.Repetitions),
                    weight = mutableFloatStateOf(1.5F),
                    weightType = mutableStateOf(WeightTypes.BodyMass),
                    postRestTime = mutableIntStateOf(137),
                ),
                // needs more than 1 to show the timer
                AddSessionWorkoutData(id = 0)
            )
            LazyColumn {
                item {
                    SessionBuilderItem(
                        reorderableState = rememberReorderableLazyListState(onMove = { _, _ -> }),
                        workouts = workouts,
                        thisWorkout = workouts[0],
                        onWorkoutSearch = {},
                    )
                }
            }
        }
    }
}

@Preview(
    group = "SessionBuilderItem",
    name = "Session Builder Item Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "SessionBuilderItem",
    name = "Session Builder Item Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@SuppressLint("UnrememberedMutableState")
@Composable
fun SessionBuilderItemPreview() {
    WorkINTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface,
        ) {
            val workouts = mutableListOf(
                AddSessionWorkoutData(
                    id = 0,
                    repetitionCount = mutableIntStateOf(3),
                    repetitionType = mutableStateOf(RepetitionTypes.Repetitions),
                    weight = mutableFloatStateOf(1.5F),
                    weightType = mutableStateOf(WeightTypes.KGBodyMass),
                    postRestTime = mutableIntStateOf(137),
                ),
                // needs more than 1 to show the timer
                AddSessionWorkoutData(id = 0)
            )
            LazyColumn {
                item {
                    SessionBuilderItem(
                        reorderableState = rememberReorderableLazyListState(onMove = { _, _ -> }),
                        workouts = workouts,
                        thisWorkout = workouts[0],
                        onWorkoutSearch = {},
                    )
                }
            }
        }
    }
}
