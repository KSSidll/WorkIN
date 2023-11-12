package com.kssidll.workin.ui.screen.modify.shared.session.component

import android.content.res.*
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

/**
 * @param nameEnabled controls the enabled state of [name] text field
 * @param nameErrorMessage error message to show under the [name] text field, double acts as error flag, if set to null, error flag is false, if set to some string value, error flag is true, set to empty string to trigger error flag without any error message
 * @param name name text field value
 * @param onNameChange the callback that is triggered when the input service updates the [name] text field text. An updated text comes as a parameter of the callback
 * @param descriptionEnabled controls the enabled state of [description] text field
 * @param description name text field value
 * @param onDescriptionChange the callback that is triggered when the input service updates the [description] text field text. An updated text comes as a parameter of the callback
 * @param nameFocusRequester optional [FocusRequester] for name text field
 * @param descriptionFocusRequester optional [FocusRequester] for description text field
 */
@Composable
fun ModifySessionNamePage(
    nameEnabled: Boolean,
    nameErrorMessage: String?,
    name: String,
    onNameChange: (newValue: String) -> Unit,
    descriptionEnabled: Boolean,
    description: String,
    onDescriptionChange: (newValue: String) -> Unit,
    nameFocusRequester: FocusRequester = remember { FocusRequester() },
    descriptionFocusRequester: FocusRequester = remember { FocusRequester() },
) {
    Column {
        OutlinedTextField(
            enabled = nameEnabled,
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
            value = name,
            onValueChange = onNameChange,
            label = {
                Text(
                    text = stringResource(id = R.string.session_name),
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
            isError = nameErrorMessage != null,
            supportingText = if (nameErrorMessage != null) {
                {
                    Text(
                        text = nameErrorMessage,
                        style = Typography.bodyLarge,
                    )
                }
            } else null,
            modifier = Modifier
                .focusRequester(focusRequester = nameFocusRequester)
        )

        Spacer(modifier = Modifier.height(18.dp))

        OutlinedTextField(
            enabled = descriptionEnabled,
            minLines = 5,
            value = description,
            onValueChange = onDescriptionChange,
            label = {
                Text(
                    text = stringResource(id = R.string.session_description),
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
    }
}

@Preview(
    group = "Modify Session Name Page",
    name = "Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Modify Session Name Page",
    name = "Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun ModifySessionNamePagePreview() {
    WorkINTheme {
        Surface {
            ModifySessionNamePage(
                nameEnabled = true,
                nameErrorMessage = null,
                name = String(),
                onNameChange = {},
                descriptionEnabled = true,
                description = String(),
                onDescriptionChange = {},
            )
        }
    }
}
