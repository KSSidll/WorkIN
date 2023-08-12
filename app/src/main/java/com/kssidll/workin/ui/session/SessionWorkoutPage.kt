package com.kssidll.workin.ui.session

import android.content.res.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.foundation.text.selection.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.sharp.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.core.text.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.ui.theme.*
import kotlinx.coroutines.*

/// Data ///
data class SessionWorkoutPageState(
    var repetitionCount: Int = 0,
    var repetitionType: RepetitionTypes = RepetitionTypes.Repetitions,
    var weight: Float = 0F,
    var weightType: WeightTypes = WeightTypes.KG,
    var changeSessionWorkoutRepsSettings: MutableState<Boolean> = mutableStateOf(false),
    var changeSessionWorkoutWeightSettings: MutableState<Boolean> = mutableStateOf(false),
)


/// Page ///
/**
 * @param onLogSubmit: Event to call when user wants to submit the log into history of this workout,
 * provided state contains information on whether the user wants to change the repetition and/or weight values of this workout as well
 */
@Composable
fun SessionWorkoutPage(
    workout: FullSessionWorkout,
    onLogSubmit: (SessionWorkoutPageState) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val state = remember { SessionWorkoutPageState() }

    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = workout.workout.name,
                    fontSize = 28.sp
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val description = workout.workout.description
                if (description.isNotBlank()) {
                    Text(
                        text = description,
                        fontSize = 20.sp,
                        modifier = Modifier.alpha(0.8F),
                    )
                }
            }
        }

        Column {

            Column(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Column(
                    modifier = Modifier.wrapContentSize(Alignment.Center)
                ) {

                    Box(
                        modifier = Modifier.pointerInput(Unit) {
                            detectTapGestures { _ ->
                                state.changeSessionWorkoutRepsSettings.value =
                                    !state.changeSessionWorkoutRepsSettings.value
                            }
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Checkbox(
                                checked = state.changeSessionWorkoutRepsSettings.value,
                                onCheckedChange = {
                                    state.changeSessionWorkoutRepsSettings.value = it
                                },
                                modifier = Modifier.scale(1.1F),
                            )

                            Text(
                                text = stringResource(id = R.string.session_change_workout_reps_on_log_submit),
                                fontSize = 15.sp,
                            )
                        }
                    }


                    Box(
                        modifier = Modifier.pointerInput(Unit) {
                            detectTapGestures { _ ->
                                state.changeSessionWorkoutWeightSettings.value =
                                    !state.changeSessionWorkoutWeightSettings.value
                            }
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Checkbox(
                                checked = state.changeSessionWorkoutWeightSettings.value,
                                onCheckedChange = {
                                    state.changeSessionWorkoutWeightSettings.value = it
                                },
                                modifier = Modifier.scale(1.1F),
                            )

                            Text(
                                text = stringResource(id = R.string.session_change_workout_weight_on_log_submit),
                                fontSize = 16.sp,
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
                var repCountText: String by remember {
                    mutableStateOf(state.repetitionCount.toString())
                }

                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier
                        .width(70.dp)
                        .height(65.dp),
                    value = repCountText,
                    onValueChange = { newValue ->
                        if (newValue.isBlank()) {
                            state.repetitionCount = 0
                            repCountText = newValue
                        } else if (newValue.isDigitsOnly()) {
                            state.repetitionCount = newValue.toInt()
                            repCountText = state.repetitionCount.toString()
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
                            state.repetitionCount = state.repetitionCount.inc()
                            repCountText = state.repetitionCount.toString()
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
                            if (state.repetitionCount != 0) {
                                state.repetitionCount = state.repetitionCount.dec()
                                repCountText = state.repetitionCount.toString()
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
                        text = state.repetitionType.getTranslation(),
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
                            if (item == state.repetitionType) continue

                            DropdownMenuItem(
                                text = {
                                    Text(text = item.getTranslation())
                                },
                                onClick = {
                                    menuExpanded = false
                                    state.repetitionType = item
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
                    var weightTargetAlpha: Float by remember {
                        if (state.weightType.hideWeight) mutableFloatStateOf(0F)
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
                            mutableStateOf(state.weight.toString())
                        }


                        OutlinedTextField(
                            singleLine = true,
                            modifier = Modifier
                                .width(70.dp)
                                .height(65.dp),
                            value = weightText,
                            onValueChange = { newValue ->
                                if (newValue.isBlank()) {
                                    state.weight = 0F
                                    weightText = String()
                                } else if (newValue.matches(Regex("""\d+?\.?\d{0,2}"""))) {
                                    state.weight = newValue.toFloat()
                                    weightText = state.weight.toString()
                                        .dropLast(
                                            if (newValue.last() == '.') 1
                                            else if (
                                                state.weight.toString()
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
                        )

                        Spacer(modifier = Modifier.width(2.dp))

                        Column(modifier = Modifier.height(65.dp)) {
                            Spacer(modifier = Modifier.height(8.dp))
                            FilledIconButton(
                                modifier = Modifier
                                    .weight(1F)
                                    .width(18.dp),
                                onClick = {
                                    state.weight =
                                        state.weight.plus(0.5F)
                                    weightText = state.weight.toString()
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
                                    if (state.weight != 0F) {
                                        state.weight = state.weight.minus(0.5F)
                                        weightText = state.weight.toString()
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

                    var menuExpanded: Boolean by remember {
                        mutableStateOf(false)
                    }

                    Box(
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        val targetWidth: Dp =
                            if (state.weightType.hideWeight) {
                                223.dp
                            } else {
                                120.dp
                            }

                        val currentWidth: Dp by animateDpAsState(
                            targetValue = targetWidth,
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
                                text = state.weightType.getTranslation(),
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
                                    if (item == state.weightType) continue

                                    DropdownMenuItem(
                                        text = {
                                            Text(text = item.getTranslation())
                                        },
                                        onClick = {
                                            if (item.hideWeight) {
                                                weightTargetAlpha = 0F
                                            } else if (state.weightType.hideWeight) {
                                                scope.launch {
                                                    delay(50)
                                                    weightTargetAlpha = 1F
                                                }
                                            }
                                            menuExpanded = false
                                            state.weightType = item
                                        },
                                    )
                                }
                            }
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        onLogSubmit(state)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(horizontal = 32.dp),
                    shape = RoundedCornerShape(23.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = if (state.changeSessionWorkoutRepsSettings.value || state.changeSessionWorkoutWeightSettings.value)
                                stringResource(R.string.session_log_workout_complete_and_change)
                            else
                                stringResource(id = R.string.session_log_workout_complete),
                            fontSize = if (state.changeSessionWorkoutRepsSettings.value || state.changeSessionWorkoutWeightSettings.value)
                                20.sp
                            else
                                24.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}


/// Page Preview ///
@Preview(
    group = "SessionWorkoutPage",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "SessionWorkoutPage",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SessionWorkoutPagePreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SessionWorkoutPage(
                workout = FullSessionWorkout(
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
                onLogSubmit = {},
            )
        }
    }
}