package com.kssidll.workin.ui.shared

import android.content.res.*
import android.database.sqlite.SQLiteConstraintException
import android.util.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.foundation.text.selection.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.R
import com.kssidll.workin.ui.theme.*
import kotlinx.coroutines.*

/// Data ///
data class EditWorkoutDataSubpageState(
    var name: String = String(),
    var description: String = String(),
)


/// Subpage ///
@Composable
fun EditWorkoutDataSubpage(
    onBack: () -> Unit,
    submitButtonContent: @Composable () -> Unit,
    onSubmit: suspend (EditWorkoutDataSubpageState) -> Unit,
    startState: EditWorkoutDataSubpageState = EditWorkoutDataSubpageState(),
    headerText: @Composable () -> Unit = {},
    headerAdditionalContent: @Composable BoxScope.() -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    var nameText: String by remember {
        mutableStateOf(startState.name)
    }
    var nameBlankError: Boolean by remember {
        mutableStateOf(false)
    }
    var nameDuplicateError: Boolean by remember {
        mutableStateOf(false)
    }

    var descriptionText: String by remember {
        mutableStateOf(startState.description)
    }

    Column {
        SecondaryTopHeader(
            onIconClick = onBack,
            additionalContent = headerAdditionalContent,
        ) {
            headerText()
        }

        Column(
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
        ) {

            Spacer(modifier = Modifier.height(64.dp))

            val nameFocusRequester = remember { FocusRequester() }
            val descriptionFocusRequester = remember { FocusRequester() }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                OutlinedTextField(
                    maxLines = 4,
                    modifier = Modifier.focusRequester(focusRequester = nameFocusRequester),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            nameFocusRequester.freeFocus()
                            descriptionFocusRequester.requestFocus()
                        }
                    ),
                    value = nameText,
                    onValueChange = {
                        nameText = it
                        nameBlankError = false
                        nameDuplicateError = false
                    },
                    label = {
                        Text(
                            text = stringResource(id = com.kssidll.workin.R.string.workout_name),
                            fontSize = 16.sp,
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = MaterialTheme.colorScheme.onBackground,
                        selectionColors = TextSelectionColors(
                            handleColor = MaterialTheme.colorScheme.tertiary,
                            backgroundColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4F)
                        ),
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(
                            alpha = 0.7F
                        ),
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.9F),
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground.copy(
                            alpha = 0.75F
                        ),
                        focusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(
                            alpha = 0.85F
                        ),
                        focusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    ),
                    isError = nameBlankError || nameDuplicateError,
                    supportingText = {
                        if (nameBlankError) {
                            Text(text = stringResource(id = R.string.field_required))
                        } else if (nameDuplicateError) {
                            Text(text = stringResource(id = R.string.workout_name_duplicate))
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                OutlinedTextField(
                    modifier = Modifier.focusRequester(descriptionFocusRequester),
                    minLines = 5,
                    value = descriptionText,
                    onValueChange = {
                        descriptionText = it
                    },
                    label = {
                        Text(
                            text = stringResource(id = com.kssidll.workin.R.string.workout_description),
                            fontSize = 16.sp,
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = MaterialTheme.colorScheme.onBackground,
                        selectionColors = TextSelectionColors(
                            handleColor = MaterialTheme.colorScheme.tertiary,
                            backgroundColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4F)
                        ),
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(
                            alpha =
                            0.7F
                        ),
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.9F),
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground.copy(
                            alpha =
                            0.75F
                        ),
                        focusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(
                            alpha =
                            0.85F
                        ),
                        focusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    ),
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight(0.45F)
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    nameBlankError = nameText.isBlank()

                    if (!nameBlankError) {
                        scope.launch {
                            try {
                                onSubmit(
                                    EditWorkoutDataSubpageState(
                                        name = nameText,
                                        description = descriptionText,
                                    )
                                )
                            } catch (_: SQLiteConstraintException) {
                                nameDuplicateError = true
                            }
                        }
                    }
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
                    submitButtonContent()
                }
            }
        }
    }
}


/// Subpage Preview ///
@Preview(
    group = "EditWorkoutDataSubpage",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "EditWorkoutDataSubpage",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun EditWorkoutDataSubpagePreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            EditWorkoutDataSubpage(
                submitButtonContent = {
                    Text(text = "Submit Button", fontSize = 20.sp)
                },
                onBack = {},
                onSubmit = {},
            )
        }
    }
}
