package com.kssidll.workin.ui.shared.session

import android.annotation.*
import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.foundation.text.selection.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.rounded.*
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

/// Data ///
data class EditSessionDataSubpageNameState(
    val name: MutableState<String> = mutableStateOf(String()),
    val nameError: MutableState<Boolean> = mutableStateOf(false),
    val description: MutableState<String> = mutableStateOf(String())
)

/// Page ///
@Composable
fun EditSessionDataSubpageName(
    onNext: () -> Unit,
    state: EditSessionDataSubpageNameState,
) {
    Column {
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
                    value = state.name.value,
                    onValueChange = {
                        state.name.value = it
                        state.nameError.value = false
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.session_name),
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
                    isError = state.nameError.value,
                    supportingText = {
                        if (state.nameError.value) {
                            Text(text = stringResource(id = R.string.field_required))
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
                    minLines = 10,
                    value = state.description.value,
                    onValueChange = {
                        state.description.value = it
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
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onNext,
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
                        painter = painterResource(id = R.drawable.swipe_left),
                        contentDescription = stringResource(
                            id = R.string.next
                        ),
                        modifier = Modifier.size(30.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = stringResource(R.string.next),
                        fontSize = 20.sp
                    )

                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowRight,
                        contentDescription = stringResource(
                            R.string
                                .next
                        ),
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}


/// Page Preview ///
@Preview(
    group = "NamePage",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "NamePage",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@SuppressLint("UnrememberedMutableState")
@Composable
fun NamePagePreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            EditSessionDataSubpageName(
                onNext = {},
                state = EditSessionDataSubpageNameState(),
            )
        }
    }
}
