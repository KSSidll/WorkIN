package com.kssidll.workin.ui.shared.session

import android.annotation.*
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

/// Data ///
data class EditSessionDataSubpageDaysState(
    val mondayChecked: MutableState<Boolean> = mutableStateOf(false),
    val tuesdayChecked: MutableState<Boolean> = mutableStateOf(false),
    val wednesdayChecked: MutableState<Boolean> = mutableStateOf(false),
    val thursdayChecked: MutableState<Boolean> = mutableStateOf(false),
    val fridayChecked: MutableState<Boolean> = mutableStateOf(false),
    val saturdayChecked: MutableState<Boolean> = mutableStateOf(false),
    val sundayChecked: MutableState<Boolean> = mutableStateOf(false),
)

/// Page ///
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

            Column(
                modifier = Modifier.wrapContentSize(Alignment.Center)
            ) {
                Box(
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures { _ ->
                            state.mondayChecked.value = !state.mondayChecked.value
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = state.mondayChecked.value,
                            onCheckedChange = {
                                state.mondayChecked.value = it
                            },
                            modifier = Modifier.scale(1.1F),
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
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures { _ ->
                            state.tuesdayChecked.value = !state.tuesdayChecked.value
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = state.tuesdayChecked.value,
                            onCheckedChange = {
                                state.tuesdayChecked.value = it
                            },
                            modifier = Modifier.scale(1.1F),
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
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures { _ ->
                            state.wednesdayChecked.value = !state.wednesdayChecked.value
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = state.wednesdayChecked.value,
                            onCheckedChange = {
                                state.wednesdayChecked.value = it
                            },
                            modifier = Modifier.scale(1.1F),
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
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures { _ ->
                            state.thursdayChecked.value = !state.thursdayChecked.value
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = state.thursdayChecked.value,
                            onCheckedChange = {
                                state.thursdayChecked.value = it
                            },
                            modifier = Modifier.scale(1.1F),
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
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures { _ ->
                            state.fridayChecked.value = !state.fridayChecked.value
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = state.fridayChecked.value,
                            onCheckedChange = {
                                state.fridayChecked.value = it
                            },
                            modifier = Modifier.scale(1.1F),
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
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures { _ ->
                            state.saturdayChecked.value = !state.saturdayChecked.value
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = state.saturdayChecked.value,
                            onCheckedChange = {
                                state.saturdayChecked.value = it
                            },
                            modifier = Modifier.scale(1.1F),
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
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures { _ ->
                            state.sundayChecked.value = !state.sundayChecked.value
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = state.sundayChecked.value,
                            onCheckedChange = {
                                state.sundayChecked.value = it
                            },
                            modifier = Modifier.scale(1.1F),
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


/// Page Preview ///
@Preview(
    group = "DaysPage",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "DaysPage",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@SuppressLint("UnrememberedMutableState")
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
