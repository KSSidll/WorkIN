package com.kssidll.workin.ui.addsession

import android.annotation.*
import android.content.res.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.rounded.*
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
import kotlinx.coroutines.*

@Composable
fun DaysPage(
    onNext: () -> Unit,
    mondayChecked: MutableState<Boolean>,
    tuesdayChecked: MutableState<Boolean>,
    wednesdayChecked: MutableState<Boolean>,
    thursdayChecked: MutableState<Boolean>,
    fridayChecked: MutableState<Boolean>,
    saturdayChecked: MutableState<Boolean>,
    sundayChecked: MutableState<Boolean>,
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
                            mondayChecked.value = !mondayChecked.value
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = mondayChecked.value,
                            onCheckedChange = {
                                mondayChecked.value = it
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
                            tuesdayChecked.value = !tuesdayChecked.value
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = tuesdayChecked.value,
                            onCheckedChange = {
                                tuesdayChecked.value = it
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
                            wednesdayChecked.value = !wednesdayChecked.value
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = wednesdayChecked.value,
                            onCheckedChange = {
                                wednesdayChecked.value = it
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
                            thursdayChecked.value = !thursdayChecked.value
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = thursdayChecked.value,
                            onCheckedChange = {
                                thursdayChecked.value = it
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
                            fridayChecked.value = !fridayChecked.value
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = fridayChecked.value,
                            onCheckedChange = {
                                fridayChecked.value = it
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
                            saturdayChecked.value = !saturdayChecked.value
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = saturdayChecked.value,
                            onCheckedChange = {
                                saturdayChecked.value = it
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
                            sundayChecked.value = !sundayChecked.value
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = sundayChecked.value,
                            onCheckedChange = {
                                sundayChecked.value = it
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
    group = "DaysPage",
    name = "Days Page Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "DaysPage",
    name = "Days Page Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@SuppressLint("UnrememberedMutableState")
@Composable
fun DaysPagePreview() {
    WorkINTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,
        ) {
            DaysPage(
                onNext = {},
                mondayChecked = mutableStateOf(false),
                tuesdayChecked = mutableStateOf(false),
                wednesdayChecked = mutableStateOf(false),
                thursdayChecked = mutableStateOf(false),
                fridayChecked = mutableStateOf(false),
                saturdayChecked = mutableStateOf(false),
                sundayChecked = mutableStateOf(false),
            )
        }
    }
}
