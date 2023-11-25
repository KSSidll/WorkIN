package com.kssidll.workin.ui.component


import android.content.res.Configuration.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.R
import com.kssidll.workin.ui.theme.*

/**
 * @param displayed whether to show the dialog or not, component handles disposing of itself
 * @param onDelete function to call when dialog warning confirmation button is clicked
 * @param warningMessage message to show in the dialog
 * @param warningConfirmed confirmation flag, camponent handles it by itself
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteAlertDialog(
    displayed: MutableState<Boolean>,
    onDelete: () -> Unit,
    warningMessage: String,
    warningConfirmed: MutableState<Boolean>,
) {
    if (displayed.value) {
        BasicAlertDialog(
            onDismissRequest = {},
            modifier = Modifier
                .width(360.dp)
                .heightIn(min = 200.dp)
        ) {
            Surface(
                shape = ShapeDefaults.ExtraLarge,
                color = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurface,
                tonalElevation = 1.dp,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(IntrinsicSize.Min)
                ) {
                    Text(
                        text = warningMessage,
                        style = Typography.bodyLarge,
                        modifier = Modifier.weight(1F)
                    )

                    val warningConfirmationInteractionSource =
                        remember { MutableInteractionSource() }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = warningConfirmationInteractionSource,
                                indication = null
                            ) {
                                warningConfirmed.value = !warningConfirmed.value
                            }
                    ) {
                        Checkbox(
                            checked = warningConfirmed.value,
                            onCheckedChange = {
                                warningConfirmed.value = !warningConfirmed.value
                            },
                            interactionSource = warningConfirmationInteractionSource,
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                        )

                        Text(
                            text = stringResource(id = R.string.destructive_action_confirmation_text),
                            style = Typography.bodyLarge,
                        )
                    }

                    Row {
                        Button(
                            onClick = {
                                displayed.value = false
                                warningConfirmed.value = false
                            },
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .weight(1f)
                        ) {
                            Text(
                                text = stringResource(id = R.string.destructive_action_cancel),
                                style = Typography.bodyLarge,
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(
                            enabled = warningConfirmed.value,
                            onClick = {
                                onDelete()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer,
                                contentColor = MaterialTheme.colorScheme.onErrorContainer,
                                disabledContainerColor = MaterialTheme.colorScheme.errorContainer.copy(
                                    disabledAlpha
                                ),
                                disabledContentColor = MaterialTheme.colorScheme.onErrorContainer.copy(
                                    disabledAlpha
                                ),
                            ),
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .weight(1f)
                        ) {
                            Text(
                                text = stringResource(id = R.string.destructive_action_confirm),
                                style = Typography.bodyLarge,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    group = "DeleteAlertDialog",
    name = "Dark",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    group = "DeleteAlertDialog",
    name = "Light",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
private fun DeleteAlertDialogPreview() {
    WorkINTheme {
        Surface {
            DeleteAlertDialog(
                displayed = remember { mutableStateOf(true) },
                onDelete = {},
                warningMessage = "test message",
                warningConfirmed = remember { mutableStateOf(false) },
            )
        }
    }
}
