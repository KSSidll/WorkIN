package com.kssidll.workin.ui.screen.modify.edit.session

import android.content.res.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.foundation.text.selection.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.rounded.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.sharp.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.core.text.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.*
import com.kssidll.workin.ui.theme.*
import kotlinx.coroutines.*
import org.burnoutcrew.reorderable.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.EditSessionDataSubpageBuilderItem(
    reorderableState: ReorderableLazyListState,
    thisItem: EditSessionDataSubpageBuilderItemData,
    showTimer: Boolean,
    onWorkoutSearch: (Int) -> Unit,
    onMoveUp: () -> Unit,
    onMoveDown: () -> Unit,
    onDelete: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .animateItemPlacement()
        ) {

            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .border(
                                width = OutlinedTextFieldDefaults.UnfocusedBorderThickness,
                                color = if (thisItem.workout.isError.value) MaterialTheme.colorScheme.error
                                else MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .height(65.dp)
                            .width(223.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onWorkoutSearch(thisItem.id)
                            }
                    ) {
                        Text(
                            text = thisItem.workout.workoutName.value.ifBlank { stringResource(id = R.string.no_workout_session_builder_item) },
                            maxLines = 2,
                            color = if (thisItem.workout.isError.value) MaterialTheme.colorScheme.error
                            else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp),
                        )

                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    var repCountText: String by remember {
                        mutableStateOf(thisItem.workout.repetitionCount.value.toString())
                    }

                    OutlinedTextField(
                        singleLine = true,
                        value = repCountText,
                        onValueChange = { newValue ->
                            if (newValue.isBlank()) {
                                thisItem.workout.repetitionCount.value = 0
                                repCountText = newValue
                            } else if (newValue.isDigitsOnly()) {
                                thisItem.workout.repetitionCount.value =
                                    newValue.toInt()
                                repCountText = thisItem.workout.repetitionCount.value.toString()
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
                        modifier = Modifier
                            .width(70.dp)
                            .height(65.dp)
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Column(modifier = Modifier.height(65.dp)) {
                        Spacer(modifier = Modifier.height(8.dp))

                        FilledIconButton(
                            onClick = {
                                thisItem.workout.repetitionCount.value =
                                    thisItem.workout.repetitionCount.value.inc()
                                repCountText = thisItem.workout.repetitionCount.value.toString()
                            },
                            colors = IconButtonColors(
                                contentColor = MaterialTheme.colorScheme.onTertiary,
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                disabledContentColor = MaterialTheme.colorScheme.onTertiary,
                                disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                            ),
                            modifier = Modifier
                                .weight(1F)
                                .width(18.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Sharp.KeyboardArrowUp,
                                contentDescription = stringResource(id = R.string.increase_repetition_count_1_description),
                            )
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        FilledIconButton(
                            onClick = {
                                thisItem.workout.repetitionCount.apply {
                                    if (this.value == 0) return@apply

                                    this.value = this.value.dec()
                                    repCountText = thisItem.workout.repetitionCount.value.toString()
                                }
                            },
                            colors = IconButtonColors(
                                contentColor = MaterialTheme.colorScheme.onTertiary,
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                disabledContentColor = MaterialTheme.colorScheme.onTertiary,
                                disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                            ),
                            modifier = Modifier
                                .weight(1F)
                                .width(18.dp)
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
                        contentAlignment = Alignment.Center,
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
                            }
                    ) {
                        Text(
                            text = thisItem.workout.repetitionType.value.getTranslation(),
                            maxLines = 2,
                            modifier = Modifier
                                .padding(vertical = 6.dp, horizontal = 12.dp)
                        )

                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = {
                                menuExpanded = false
                            },
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surfaceContainer)

                        ) {
                            for (item in RepetitionTypes.entries) {
                                if (item == thisItem.workout.repetitionType.value) continue

                                DropdownMenuItem(
                                    text = {
                                        Text(text = item.getTranslation())
                                    },
                                    onClick = {
                                        menuExpanded = false
                                        thisItem.workout.repetitionType.value = item
                                    },
                                )
                            }
                        }
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(modifier = Modifier.width(223.dp)) {
                        var weightTargetAlpha: Float by remember {
                            if (thisItem.workout.weightType.value.hideWeight) mutableFloatStateOf(0F)
                            else mutableFloatStateOf(1F)
                        }

                        val currentAlpha: Float by animateFloatAsState(
                            targetValue = weightTargetAlpha,
                            label = "Weight type resize animation on hide parameter"
                        )

                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .alpha(currentAlpha)
                        ) {
                            var weightText: String by remember {
                                mutableStateOf(thisItem.workout.weight.value.toString())
                            }

                            OutlinedTextField(
                                singleLine = true,
                                value = weightText,
                                onValueChange = { newValue ->
                                    if (newValue.isBlank()) {
                                        thisItem.workout.weight.value = 0F
                                        weightText = String()
                                    } else if (newValue.matches(Regex("""\d+?\.?\d{0,2}"""))) {
                                        thisItem.workout.weight.value =
                                            newValue.toFloat()
                                        weightText = thisItem.workout.weight.value.toString()
                                            .dropLast(
                                                if (newValue.last() == '.') 1
                                                else if (
                                                    thisItem.workout.weight.value.toString()
                                                        .endsWith(".0") &&
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
                                        backgroundColor = MaterialTheme.colorScheme.tertiary.copy(
                                            alpha = 0.4F
                                        )
                                    ),
                                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                ),
                                modifier = Modifier
                                    .width(70.dp)
                                    .height(65.dp)
                            )

                            Spacer(modifier = Modifier.width(2.dp))

                            Column(modifier = Modifier.height(65.dp)) {
                                Spacer(modifier = Modifier.height(8.dp))
                                FilledIconButton(
                                    onClick = {
                                        thisItem.workout.weight.value =
                                            thisItem.workout.weight.value.plus(0.5F)
                                        weightText = thisItem.workout.weight.value.toString()
                                    },
                                    colors = IconButtonColors(
                                        contentColor = MaterialTheme.colorScheme.onTertiary,
                                        containerColor = MaterialTheme.colorScheme.tertiary,
                                        disabledContentColor = MaterialTheme.colorScheme.onTertiary,
                                        disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                                    ),
                                    modifier = Modifier
                                        .weight(1F)
                                        .width(18.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Sharp.KeyboardArrowUp,
                                        contentDescription = stringResource(id = R.string.increase_weight_05_description),
                                    )
                                }

                                Spacer(modifier = Modifier.height(2.dp))

                                FilledIconButton(
                                    onClick = {
                                        thisItem.workout.weight.apply {
                                            if (this.value == 0F) return@apply

                                            this.value = this.value.minus(0.5F)
                                            weightText =
                                                thisItem.workout.weight.value.toString()
                                        }
                                    },
                                    colors = IconButtonColors(
                                        contentColor = MaterialTheme.colorScheme.onTertiary,
                                        containerColor = MaterialTheme.colorScheme.tertiary,
                                        disabledContentColor = MaterialTheme.colorScheme.onTertiary,
                                        disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                                    ),
                                    modifier = Modifier
                                        .weight(1F)
                                        .width(18.dp)
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

                        var menuExpanded: Boolean by remember {
                            mutableStateOf(false)
                        }

                        Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                            val targetWidth: Dp =
                                if (thisItem.workout.weightType.value.hideWeight) {
                                    223.dp
                                } else {
                                    120.dp
                                }

                            val currentWidth: Dp by animateDpAsState(
                                targetValue = targetWidth,
                                label = "Weight type resize animation on hide parameter"
                            )

                            Box(
                                contentAlignment = Alignment.Center,
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
                                    .animateContentSize()
                            ) {
                                Text(
                                    text = thisItem.workout.weightType.value.getTranslation(),
                                    maxLines = 2,
                                    modifier = Modifier
                                        .padding(
                                            vertical = 6.dp,
                                            horizontal = 12.dp
                                        )
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
                                        if (item == thisItem.workout.weightType.value) continue

                                        DropdownMenuItem(
                                            text = {
                                                Text(text = item.getTranslation())
                                            },
                                            onClick = {
                                                if (item.hideWeight) {
                                                    weightTargetAlpha = 0F
                                                } else if (thisItem.workout.weightType.value.hideWeight) {
                                                    scope.launch {
                                                        delay(50)
                                                        weightTargetAlpha = 1F
                                                    }
                                                }
                                                menuExpanded = false
                                                thisItem.workout.weightType.value = item
                                            },
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (showTimer) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        FilledIconButton(
                            onClick = {
                                thisItem.workout.postRestTime.value =
                                    thisItem.workout.postRestTime.value.minus(10)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.keyboard_double_arrow_left),
                                contentDescription = stringResource(id = R.string.lower_rest_time_10_description),
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        FilledIconButton(
                            onClick = {
                                thisItem.workout.postRestTime.value =
                                    thisItem.workout.postRestTime.value.dec()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                                contentDescription = stringResource(id = R.string.lower_rest_time_1_description),
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(5.dp))

                        Box(contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(
                                progress = 1F,
                                color = MaterialTheme.colorScheme.outline,
                                modifier = Modifier.size(85.dp),
                            )
                            Text(
                                text = formatTime(thisItem.workout.postRestTime.value),
                                fontSize = 18.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(5.dp))

                        FilledIconButton(
                            onClick = {
                                thisItem.workout.postRestTime.value =
                                    thisItem.workout.postRestTime.value.inc()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                contentDescription = stringResource(id = R.string.increase_rest_time_1_description),
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        FilledIconButton(
                            onClick = {
                                thisItem.workout.postRestTime.value =
                                    thisItem.workout.postRestTime.value.plus(10)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.keyboard_double_arrow_right),
                                contentDescription = stringResource(id = R.string.increase_rest_time_10_description),
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }

            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 10.dp)
            ) {
                FilledIconButton(
                    onClick = onDelete,
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
                        .padding(start = 10.dp, end = 7.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClickLabel = stringResource(id = R.string.click_to_move_up),
                            ) {
                                onMoveUp()
                            }
                    ) {
                        Box(modifier = Modifier.align(Alignment.Center)) {
                            Icon(
                                imageVector = Icons.Rounded.KeyboardArrowUp,
                                contentDescription = null,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .width(48.dp)
                            .detectReorder(reorderableState)
                    ) {
                        Box(modifier = Modifier.align(Alignment.Center)) {
                            Column {
                                Icon(
                                    modifier = Modifier.rotate(180F),
                                    imageVector = Icons.Rounded.ArrowDropDown,
                                    contentDescription = null
                                )
                                Icon(
                                    imageVector = Icons.Rounded.MoreVert,
                                    contentDescription = stringResource(id = R.string.change_item_order)
                                )
                                Icon(
                                    imageVector = Icons.Rounded.ArrowDropDown,
                                    contentDescription = null
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClickLabel = stringResource(id = R.string.click_to_move_down),
                            ) {
                                onMoveDown()
                            }
                    ) {
                        Box(modifier = Modifier.align(Alignment.Center)) {
                            Icon(
                                modifier = Modifier.align(Alignment.Center),
                                imageVector = Icons.Rounded.KeyboardArrowDown,
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Preview(
    group = "Session Builder Item",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Session Builder Item",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SessionBuilderItemHideWeightPreview() {
    WorkINTheme {
        Surface {
            LazyColumn {
                item {
                    EditSessionDataSubpageBuilderItem(
                        reorderableState = rememberReorderableLazyListState(onMove = { _, _ -> }),
                        thisItem = EditSessionDataSubpageBuilderItemData(
                            id = 0,
                            workout = SessionBuilderWorkout(
                                repetitionCount = remember {
                                    mutableIntStateOf(3)
                                },
                                repetitionType = remember {
                                    mutableStateOf(RepetitionTypes.Repetitions)
                                },
                                weight = remember {
                                    mutableFloatStateOf(1.5F)
                                },
                                weightType = remember {
                                    mutableStateOf(WeightTypes.BodyMass)
                                },
                                postRestTime = remember {
                                    mutableIntStateOf(137)
                                },
                            ),
                        ),
                        showTimer = true,
                        onWorkoutSearch = {},
                        onMoveUp = {},
                        onMoveDown = {},
                        onDelete = {},
                    )
                }
            }
        }
    }
}

@Preview(
    group = "Session Builder Item",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Session Builder Item",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SessionBuilderItemPreview() {
    WorkINTheme {
        Surface {
            LazyColumn {
                item {
                    EditSessionDataSubpageBuilderItem(
                        reorderableState = rememberReorderableLazyListState(onMove = { _, _ -> }),
                        thisItem = EditSessionDataSubpageBuilderItemData(
                            id = 0,
                            workout = SessionBuilderWorkout(
                                repetitionCount = remember {
                                    mutableIntStateOf(3)
                                },
                                repetitionType = remember {
                                    mutableStateOf(RepetitionTypes.Repetitions)
                                },
                                weight = remember {
                                    mutableFloatStateOf(1.5F)
                                },
                                weightType = remember {
                                    mutableStateOf(WeightTypes.KGBodyMass)
                                },
                                postRestTime = remember {
                                    mutableIntStateOf(137)
                                },
                            ),
                        ),
                        showTimer = true,
                        onWorkoutSearch = {},
                        onMoveUp = {},
                        onMoveDown = {},
                        onDelete = {},
                    )
                }
            }
        }
    }
}
