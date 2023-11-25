package com.kssidll.workin.ui.screen.modify.shared.workout

import android.content.res.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.foundation.text.selection.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.ui.component.*
import com.kssidll.workin.ui.theme.*

/**
 * @param state state of the component, should be held in a ViewModel
 * @param onBack function to call when requesting a navigate back action
 * @param onDelete optional function to call when requesting deletion, should handle warning state through [state] if provided
 * @param onSubmit function to call when requesting data submission
 * @param submitButtonText text displayed in the [onSubmit] button
 * @param submitButtonIcon icon displayed to the left of [submitButtonText] in the [onSubmit] button
 * @param nameFocusRequester optional [FocusRequester] for name text field
 * @param descriptionFocusRequester optional [FocusRequester] for description text field
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyWorkoutScreen(
    state: ModifyWorkoutScreenState,
    onBack: () -> Unit,
    onDelete: (() -> Unit)? = null,
    onSubmit: () -> Unit,
    submitButtonText: String,
    submitButtonIcon: ImageVector,
    nameFocusRequester: FocusRequester = remember { FocusRequester() },
    descriptionFocusRequester: FocusRequester = remember { FocusRequester() },
) {
    Box {
        if (onDelete != null) {
            DeleteAlertDialog(
                displayed = state.showDeleteWarning,
                onDelete = onDelete,
                warningMessage = stringResource(id = R.string.workout_delete_warning_text),
                warningConfirmed = state.deleteWarningConfirmed,
            )
        }

        Scaffold(
            topBar = {
                WorkINTopAppBar(
                    navigationIcon = navigationIcon(
                        type = NavigationIcon.Types.Back,
                        contentDescription = stringResource(id = R.string.navigate_back),
                        onClick = onBack,
                    ),
                    actions = {
                        if (onDelete != null) {
                            IconButton(
                                onClick = {
                                    onDelete()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.DeleteForever,
                                    contentDescription = stringResource(R.string.delete_workout),
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(27.dp),
                                )
                            }
                        }
                    }
                )
            }
        ) {
            ModifyWorkoutScreenContent(
                state = state,
                onSubmit = onSubmit,
                submitButtonText = submitButtonText,
                submitButtonIcon = submitButtonIcon,
                modifier = Modifier.padding(it),
                nameFocusRequester = nameFocusRequester,
                descriptionFocusRequester = descriptionFocusRequester,
            )
        }
    }
}

private val SubmitButtonHeight: Dp = 70.dp
private val SubmitButtonMaxBottomPadding: Dp = 288.dp
private val SubmitButtonMinBottomPadding: Dp = 6.dp
private val SubmitButtonMaxTopPadding: Dp = 20.dp
private val SubmitButtonMinTopPadding: Dp = 14.dp
private val SubmitButtonHorizontalPadding: Dp = 20.dp

@Composable
private fun ModifyWorkoutScreenContent(
    state: ModifyWorkoutScreenState,
    onSubmit: () -> Unit,
    submitButtonText: String,
    submitButtonIcon: ImageVector,
    modifier: Modifier,
    nameFocusRequester: FocusRequester,
    descriptionFocusRequester: FocusRequester,
) {
    BoxWithConstraints(
        modifier = modifier,
    ) {
        val boxMaxHeight = maxHeight

        Column {
            val minHeight = boxMaxHeight.minus(SubmitButtonHeight + SubmitButtonMaxBottomPadding)
            val maxHeight =
                boxMaxHeight.minus(SubmitButtonHeight + SubmitButtonMinTopPadding + SubmitButtonMinBottomPadding)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(
                        min = minHeight,
                        max = maxHeight
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(64.dp))

                OutlinedTextField(
                    enabled = !state.loadingName.value,
                    maxLines = 4,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            nameFocusRequester.freeFocus()
                            descriptionFocusRequester.requestFocus()
                        }
                    ),
                    value = state.name.value,
                    onValueChange = {
                        state.name.value = it
                        state.validateName()
                        state.nameDuplicateError.value = false
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.workout_name),
                            style = Typography.bodyLarge,
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = MaterialTheme.colorScheme.onBackground,
                        selectionColors = TextSelectionColors(
                            handleColor = MaterialTheme.colorScheme.tertiary,
                            backgroundColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4F)
                        ),
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7F),
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.9F),
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75F),
                        focusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85F),
                        focusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    ),
                    isError = if (state.attemptedToSubmit.value) {
                        state.nameBlankError.value || state.nameDuplicateError.value
                    } else false,
                    supportingText = if (state.attemptedToSubmit.value) {
                        if (state.nameBlankError.value) {
                            { Text(text = stringResource(id = R.string.field_required)) }
                        } else if (state.nameDuplicateError.value) {
                            { Text(text = stringResource(id = R.string.workout_name_duplicate)) }
                        } else null
                    } else null,
                    modifier = Modifier
                        .focusRequester(focusRequester = nameFocusRequester)
                )

                Spacer(modifier = Modifier.height(18.dp))

                OutlinedTextField(
                    enabled = !state.loadingDescription.value,
                    minLines = 5,
                    value = state.description.value,
                    onValueChange = {
                        state.description.value = it
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.workout_description),
                            style = Typography.bodyLarge,
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = MaterialTheme.colorScheme.onBackground,
                        selectionColors = TextSelectionColors(
                            handleColor = MaterialTheme.colorScheme.tertiary,
                            backgroundColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4F)
                        ),
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7F),
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.9F),
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75F),
                        focusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85F),
                        focusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    ),
                    modifier = Modifier.focusRequester(descriptionFocusRequester)
                )

                Spacer(modifier = Modifier.height(SubmitButtonMaxTopPadding - SubmitButtonMinTopPadding))
            }

            Spacer(modifier = Modifier.height(SubmitButtonMinTopPadding))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onSubmit,
                    shape = RoundedCornerShape(23.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(SubmitButtonHeight)
                        .padding(
                            start = SubmitButtonHorizontalPadding,
                            end = SubmitButtonHorizontalPadding,
                        )
                ) {
                    Icon(
                        imageVector = submitButtonIcon,
                        contentDescription = null,
                        modifier = Modifier.size(26.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = submitButtonText,
                        style = Typography.titleLarge,
                    )
                }
            }
        }
    }
}

data class ModifyWorkoutScreenState(
    val name: MutableState<String> = mutableStateOf(String()),
    val description: MutableState<String> = mutableStateOf(String()),

    val nameBlankError: MutableState<Boolean> = mutableStateOf(false),
    val nameDuplicateError: MutableState<Boolean> = mutableStateOf(false),

    val attemptedToSubmit: MutableState<Boolean> = mutableStateOf(false),

    val loadingName: MutableState<Boolean> = mutableStateOf(false),
    val loadingDescription: MutableState<Boolean> = mutableStateOf(false),

    val showDeleteWarning: MutableState<Boolean> = mutableStateOf(false),
    val deleteWarningConfirmed: MutableState<Boolean> = mutableStateOf(false),
)

/**
 * Validates name field and updates its error flag
 * @return `true` if field is of correct value, `false` otherwise
 */
fun ModifyWorkoutScreenState.validateName(): Boolean {
    return name.value.isNotBlank()
        .also { nameBlankError.value = !it }
}

/**
 * Validates state fields and updates state flags
 * @return `true` if all fields are of correct value, `false` otherwise
 */
fun ModifyWorkoutScreenState.validate(): Boolean {
    return validateName()
}

/**
 * Performs data validation and tries to extract embedded data
 * @return `null` if validation sets error flags, extracted data otherwise
 */
fun ModifyWorkoutScreenState.validateAndExtractWorkoutOrNull(workoutId: Long = 0): Workout? {
    name.value = name.value.trim()
    description.value = description.value.trim()

    if (!validate()) return null

    return Workout(
        id = workoutId,
        name = name.value,
        description = description.value,
    )
}

@Preview(
    group = "Modify Workout Screen",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Modify Workout Screen",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun ModifyWorkoutScreenPreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ModifyWorkoutScreen(
                state = ModifyWorkoutScreenState(),
                onBack = {},
                onSubmit = {},
                submitButtonText = stringResource(id = R.string.confirm_edit_workout_text),
                submitButtonIcon = Icons.Default.Edit,
                onDelete = {},
            )
        }
    }
}

@Preview(
    group = "Modify Workout Screen",
    name = "Dark No Delete",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Modify Workout Screen",
    name = "Light No Delete",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun ModifyWorkoutScreenNoDeletePreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ModifyWorkoutScreen(
                state = ModifyWorkoutScreenState(),
                onBack = {},
                onSubmit = {},
                submitButtonText = stringResource(id = R.string.confirm_edit_workout_text),
                submitButtonIcon = Icons.Default.Edit,
                onDelete = {},
            )
        }
    }
}
