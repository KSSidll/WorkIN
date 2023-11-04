package com.kssidll.workin.ui.screen.session.component

import android.content.res.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.R
import com.kssidll.workin.ui.theme.*

data class EditSessionDataSubpageDaysState(
    val mondayChecked: MutableState<Boolean> = mutableStateOf(false),
    val tuesdayChecked: MutableState<Boolean> = mutableStateOf(false),
    val wednesdayChecked: MutableState<Boolean> = mutableStateOf(false),
    val thursdayChecked: MutableState<Boolean> = mutableStateOf(false),
    val fridayChecked: MutableState<Boolean> = mutableStateOf(false),
    val saturdayChecked: MutableState<Boolean> = mutableStateOf(false),
    val sundayChecked: MutableState<Boolean> = mutableStateOf(false),
)

@Composable
fun EditSessionDataSubpageDays(
    onNext: () -> Unit,
    state: EditSessionDataSubpageDaysState
) {
    Column {
        Column(
            modifier = Modifier
                .weight(1F)
                .align(Alignment.CenterHorizontally)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Column(modifier = Modifier.wrapContentSize(Alignment.Center)) {
                Box(
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures { _ ->
                                state.mondayChecked.value = !state.mondayChecked.value
                            }
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 40.dp)
                    ) {
                        Checkbox(
                            checked = state.mondayChecked.value,
                            onCheckedChange = {
                                state.mondayChecked.value = it
                            },
                            modifier = Modifier.scale(1.1F)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.monday),
                            fontSize = 18.sp,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures { _ ->
                                state.tuesdayChecked.value = !state.tuesdayChecked.value
                            }
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 40.dp)
                    ) {
                        Checkbox(
                            checked = state.tuesdayChecked.value,
                            onCheckedChange = {
                                state.tuesdayChecked.value = it
                            },
                            modifier = Modifier.scale(1.1F)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.tuesday),
                            fontSize = 18.sp,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures { _ ->
                                state.wednesdayChecked.value = !state.wednesdayChecked.value
                            }
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 40.dp)
                    ) {
                        Checkbox(
                            checked = state.wednesdayChecked.value,
                            onCheckedChange = {
                                state.wednesdayChecked.value = it
                            },
                            modifier = Modifier.scale(1.1F)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.wednesday),
                            fontSize = 18.sp,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures { _ ->
                                state.thursdayChecked.value = !state.thursdayChecked.value
                            }
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 40.dp)
                    ) {
                        Checkbox(
                            checked = state.thursdayChecked.value,
                            onCheckedChange = {
                                state.thursdayChecked.value = it
                            },
                            modifier = Modifier.scale(1.1F)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.thursday),
                            fontSize = 18.sp,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures { _ ->
                                state.fridayChecked.value = !state.fridayChecked.value
                            }
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 40.dp)
                    ) {
                        Checkbox(
                            checked = state.fridayChecked.value,
                            onCheckedChange = {
                                state.fridayChecked.value = it
                            },
                            modifier = Modifier.scale(1.1F)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.friday),
                            fontSize = 18.sp,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures { _ ->
                                state.saturdayChecked.value = !state.saturdayChecked.value
                            }
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 40.dp)
                    ) {
                        Checkbox(
                            checked = state.saturdayChecked.value,
                            onCheckedChange = {
                                state.saturdayChecked.value = it
                            },
                            modifier = Modifier.scale(1.1F)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.saturday),
                            fontSize = 18.sp,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures { _ ->
                                state.sundayChecked.value = !state.sundayChecked.value
                            }
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 40.dp)
                    ) {
                        Checkbox(
                            checked = state.sundayChecked.value,
                            onCheckedChange = {
                                state.sundayChecked.value = it
                            },
                            modifier = Modifier.scale(1.1F)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.sunday),
                            fontSize = 18.sp,
                        )
                    }
                }

            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Button(
                onClick = onNext,
                shape = RoundedCornerShape(23.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.primary,
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 32.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
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
                        fontSize = 20.sp,
                    )

                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
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

@Preview(
    group = "Days Page",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Days Page",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun DaysPagePreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            EditSessionDataSubpageDays(
                onNext = {},
                state = EditSessionDataSubpageDaysState(),
            )
        }
    }
}
