package com.kssidll.workin.ui.addsession

import android.annotation.*
import android.content.res.*
import android.icu.util.UniversalTimeScale.*
import android.util.*
import androidx.activity.compose.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.foundation.text.selection.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.sharp.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.modifier.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.core.text.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.ui.shared.*
import com.kssidll.workin.ui.theme.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.burnoutcrew.reorderable.*

/**
 * @param active: Whether this page is visible, required to ensure expected BackHandler behaviour
 */
@Composable
fun SessionBuilderPage(
    onCreate: (MutableList<AddSessionWorkoutData>) -> Unit,
    active: Boolean,
    userWorkouts: Flow<List<Workout>>,
) {
    var isWorkoutSearch: Boolean by remember {
        mutableStateOf(false)
    }

    var searchingForId: Long by remember {
        mutableLongStateOf(0)
    }

    val workouts: MutableList<AddSessionWorkoutData> = remember {
        mutableStateListOf(AddSessionWorkoutData(id = 0))
    }
    var workoutsNextId: Long by remember {
        mutableLongStateOf(1)
    }

    BackHandler(
        enabled = isWorkoutSearch && active
    ) {
        isWorkoutSearch = false
    }

    if (isWorkoutSearch) {
        SelectWorkoutSubpage(
            workouts = userWorkouts,
            onSelect = {
                workouts.find { it.id == searchingForId }
                    ?.apply {
                        this.workoutName.value = it.name
                        this.workoutId = it.id
                    }
                isWorkoutSearch = false
            },
        )
    } else {
        Column {
            Column(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(64.dp))


                Column {
                    val scrollState = rememberScrollState()
                    val reorderableState = rememberReorderableLazyListState(
                        onMove = { from, to ->
                            workouts.apply {
                                add(to.index, removeAt(from.index))
                            }
                        }
                    )

                    BoxWithConstraints {
                        LazyColumn(
                            state = reorderableState.listState,
                            modifier = Modifier
                                .scrollable(
                                    state = scrollState,
                                    orientation = Orientation.Vertical
                                )
                                .fillMaxWidth()
                                .heightIn(this.minHeight, this.maxHeight.minus(60.dp))
                                .reorderable(reorderableState)
                        ) {
                            items(workouts, key = { it.id }) {
                                ReorderableItem(
                                    reorderableState = reorderableState,
                                    key = { it.id },
                                ) { _ ->
                                    SessionBuilderItem(
                                        reorderableState = reorderableState,
                                        workouts = workouts,
                                        thisWorkout = it,
                                        onWorkoutSearch = {
                                            isWorkoutSearch = true
                                            searchingForId = it
                                        },
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        FilledIconButton(
                            onClick = {
                                workouts.add(AddSessionWorkoutData(id = workoutsNextId))
                                workoutsNextId += 1
                            },
                            colors = IconButtonColors(
                                disabledContainerColor = MaterialTheme.colorScheme.secondary,
                                disabledContentColor = MaterialTheme.colorScheme.onSecondary,
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary,
                            ),
                            shape = RoundedCornerShape(18.dp),
                            modifier = Modifier.width(80.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Add,
                                contentDescription = stringResource(id = R.string.add_new_workout_to_session_builder),
                                modifier = Modifier.size(37.dp),
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
                    onClick = {
                        onCreate(workouts)
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
                            text = stringResource(R.string.create),
                            fontSize = 20.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


/// Page Preview ///
@Preview(
    group = "SessionBuilderPage",
    name = "Session Builder Page Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "SessionBuilderPage",
    name = "Session Builder Page Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@SuppressLint("UnrememberedMutableState")
@Composable
fun SessionBuilderPagePreview() {
    WorkINTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,
        ) {
            SessionBuilderPage(
                onCreate = {},
                active = true,
                userWorkouts = flowOf(),
            )
        }
    }
}
