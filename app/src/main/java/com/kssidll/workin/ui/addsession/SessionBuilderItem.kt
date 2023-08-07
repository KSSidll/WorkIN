package com.kssidll.workin.ui.addsession

import android.annotation.*
import android.content.res.*
import android.util.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.foundation.text.selection.*
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
import kotlinx.coroutines.*
import org.burnoutcrew.reorderable.*
import kotlin.concurrent.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.SessionBuilderItem(
    reorderableState: ReorderableLazyListState,
    workouts: MutableList<AddSessionWorkoutData>,
    thisWorkout: AddSessionWorkoutData,
    onWorkoutSearch: (Long) -> Unit
) {
    val scope = rememberCoroutineScope()
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
                            .height(65.dp)
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
                    var repCountText: String by remember {
                        mutableStateOf(thisWorkout.repetitionCount.value.toString())
                    }

                    OutlinedTextField(
                        singleLine = true,
                        modifier = Modifier
                            .width(70.dp)
                            .height(65.dp),
                        value = repCountText,
                        onValueChange = { newValue ->
                            if (newValue.isBlank()) {
                                thisWorkout.repetitionCount.value = 0
                                repCountText = newValue
                            } else if (newValue.isDigitsOnly()) {
                                thisWorkout.repetitionCount.value =
                                    newValue.toInt()
                                repCountText = thisWorkout.repetitionCount.value.toString()
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            cursorColor = MaterialTheme.colorScheme.onSurface,
                            selectionColors = TextSelectionColors(
                                handleColor = MaterialTheme.colorScheme.tertiary,
                                backgroundColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4F)
                            ),
                            focusedBorderColor = MaterialTheme.colorScheme.outline,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        ),
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Column(modifier = Modifier.height(65.dp)) {
                        Spacer(modifier = Modifier.height(8.dp))

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
                        Spacer(modifier = Modifier.height(8.dp))
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
                            .height(65.dp)
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
                    Box(
                        modifier = Modifier.width(223.dp)
                    ) {
                        var currentlyResizing: Boolean by remember {
                            mutableStateOf(false)
                        }

                        if (!thisWorkout.weightType.value.hideWeight && !currentlyResizing) {
                            Row(
                                modifier = Modifier.align(Alignment.CenterStart)
                            ) {
                                var weightText: String by remember {
                                    mutableStateOf(thisWorkout.weight.value.toString())
                                }

                                OutlinedTextField(
                                    singleLine = true,
                                    modifier = Modifier
                                        .width(70.dp)
                                        .height(65.dp),
                                    value = weightText,
                                    onValueChange = { newValue ->
                                        if (newValue.isBlank()) {
                                            thisWorkout.weight.value = 0F
                                            weightText = String()
                                        } else if (newValue.matches(Regex("""\d+?\.?\d{0,2}"""))) {
                                            thisWorkout.weight.value =
                                                newValue.toFloat()
                                            weightText = thisWorkout.weight.value.toString().dropLast(
                                                if (newValue.last() == '.') 1
                                                else if (
                                                    thisWorkout.weight.value.toString().endsWith(".0") &&
                                                    !newValue.endsWith(".0")
                                                    ) 2
                                                else 0
                                            )
                                        }
                                    },
                                    shape = RoundedCornerShape(12.dp),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Decimal
                                    ),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        cursorColor = MaterialTheme.colorScheme.onSurface,
                                        selectionColors = TextSelectionColors(
                                            handleColor = MaterialTheme.colorScheme.tertiary,
                                            backgroundColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4F)
                                        ),
                                        focusedBorderColor = MaterialTheme.colorScheme.outline,
                                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                    ),
                                )

                                Spacer(modifier = Modifier.width(2.dp))

                                Column(modifier = Modifier.height(65.dp)) {
                                    Spacer(modifier = Modifier.height(8.dp))
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
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                            }
                        }

                        var menuExpanded: Boolean by remember {
                            mutableStateOf(false)
                        }

                        Box(
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            val targetWidth: Dp = if (thisWorkout.weightType.value.hideWeight) {
                                223.dp
                            } else {
                                120.dp
                            }

                            val currentWidth: Dp by animateDpAsState(
                                targetValue = targetWidth,
                                finishedListener = {
                                    scope.launch {
                                        // i like how it's "finished" like 700ms or so before it's finished
                                        // makes sense
                                        delay(700)
                                        currentlyResizing = false
                                    }
                                },
                                label = "Weight type resize animation on hide parameter"
                            )

                            Box(
                                modifier = Modifier
                                    .border(
                                        width = OutlinedTextFieldDefaults.UnfocusedBorderThickness,
                                        color = MaterialTheme.colorScheme.outline,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .height(65.dp)
                                    .width(currentWidth)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        menuExpanded = true
                                    }
                                    .animateContentSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = thisWorkout.weightType.value.getTranslation(),
                                    modifier = Modifier.padding(
                                        vertical = 6.dp,
                                        horizontal = 12.dp
                                    ),
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
                                                if (item.hideWeight) currentlyResizing = true
                                                menuExpanded = false
                                                thisWorkout.weightType.value = item
                                            },
                                        )
                                    }
                                }
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
                        .size(50.dp)
                        .padding(10.dp)
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
