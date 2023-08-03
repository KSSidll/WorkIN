package com.kssidll.workin.ui.addworkout

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.foundation.text.selection.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import com.kssidll.workin.R
import com.kssidll.workin.ui.shared.*
import com.kssidll.workin.ui.theme.*

/// Route ///
@Composable
fun AddWorkoutRoute(
    onBack: () -> Unit
) {
    val addWorkoutViewModel: AddWorkoutViewModel = hiltViewModel()

    AddWorkoutScreen(
        onBack = onBack,
        onWorkoutAdd = {
            addWorkoutViewModel.addWorkout(it)
            onBack()
        },
    )
}


/// Screen ///
@Composable
fun AddWorkoutScreen(
    onBack: () -> Unit,
    onWorkoutAdd: (AddWorkoutData) -> Unit
) {
    var nameText: String by remember {
        mutableStateOf(String())
    }
    var nameError: Boolean by remember {
        mutableStateOf(false)
    }

    var descriptionText: String by remember {
        mutableStateOf(String())
    }

    Column {
        SecondaryTopHeader(onBack = onBack) {

        }

        Column(
            modifier = Modifier
                .fillMaxHeight(0.6F)
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
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.workout_name),
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
                    isError = nameError
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
                    minLines = 3,
                    value = descriptionText,
                    onValueChange = {
                        descriptionText = it
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.workout_description),
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
                .fillMaxHeight(0.4F)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    nameError = nameText.isBlank()

                    if (!nameError) {
                        onWorkoutAdd(
                            AddWorkoutData(
                                name = nameText,
                                description = descriptionText,
                            )
                        )
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
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(
                            R.string
                                .confirm_add_workout_description
                        ),
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.confirm_add_workout_text),
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}


/// Screen Preview ///
@Preview(
    group = "AddWorkoutScreen",
    name = "Add Workout Screen Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "AddWorkoutScreen",
    name = "Add Workout Screen Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun AddWorkoutScreenPreview() {
    WorkINTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            AddWorkoutScreen(
                onBack = {},
                onWorkoutAdd = {},
            )
        }
    }
}

