package com.kssidll.workin.ui.shared.session

import android.annotation.*
import android.content.res.*
import androidx.activity.compose.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.ui.shared.*
import com.kssidll.workin.ui.theme.*
import kotlinx.coroutines.*
import org.burnoutcrew.reorderable.*

/// Data ///
data class EditSessionDataSubpageBuilderState(
    val workouts: SnapshotStateList<EditSessionDataSubpageBuilderItemData> = mutableStateListOf(),
    val isWorkoutSearch: MutableState<Boolean> = mutableStateOf(false),
    val searchingWithId: MutableState<Int> = mutableIntStateOf(0)
) {
    constructor(workouts: List<SessionBuilderWorkout>): this(
        workouts = workouts.let {
            if (it.isEmpty()) {
                mutableStateListOf(EditSessionDataSubpageBuilderItemData(id = 0))
            } else {
                it.mapIndexed { index, item ->
                    EditSessionDataSubpageBuilderItemData(
                        id = index,
                        workout = item,
                    )
                }
                    .toMutableStateList()
            }
        }
    )
}


/**
 * @param id: Unique identifier for reordering
 */
data class EditSessionDataSubpageBuilderItemData(
    val id: Int,
    val workout: SessionBuilderWorkout = SessionBuilderWorkout()
)

/// Page ///
/**
 * @param onScreen: Whether this page is visible, required to ensure expected BackHandler behaviour
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EditSessionDataSubpageBuilder(
    onSubmit: () -> Unit,
    submitButtonContent: @Composable () -> Unit,
    onScreen: Boolean,
    userWorkouts: List<Workout>,
    state: EditSessionDataSubpageBuilderState,
) {
    val scope = rememberCoroutineScope()

    BackHandler(
        enabled = state.isWorkoutSearch.value && onScreen
    ) {
        state.isWorkoutSearch.value = false
    }

    val reorderableState = rememberReorderableLazyListState(
        onMove = { from, to ->
            state.workouts.apply {
                add(to.index, removeAt(from.index))
            }
        }
    )

    if (state.isWorkoutSearch.value) {
        SelectWorkoutSubpage(
            workouts = userWorkouts,
            onSelect = {
                state.workouts.find { it.id == state.searchingWithId.value }
                    ?.apply {
                        this.workout.workoutName.value = it.name
                        this.workout.workoutId = it.id
                        this.workout.isError.value = false
                    }
                state.isWorkoutSearch.value = false
            },
        )
    } else {
        Column {
            Column(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                Column {

                    BoxWithConstraints {
                        LazyColumn(
                            state = reorderableState.listState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(this.minHeight, this.maxHeight.minus(60.dp))
                                .reorderable(reorderableState)
                        ) {
                            items(state.workouts, key = { it.id }) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(350.dp)
                                        .animateItemPlacement()
                                ) {
                                    ReorderableItem(
                                        reorderableState = reorderableState,
                                        key = { it.id },
                                    ) { _ ->
                                        EditSessionDataSubpageBuilderItem(
                                            reorderableState = reorderableState,
                                            thisItem = it,
                                            onWorkoutSearch = {
                                                state.isWorkoutSearch.value = true
                                                state.searchingWithId.value = it
                                            },
                                            showTimer = it != state.workouts.last(),
                                            onMoveUp = {
                                                state.workouts.apply {
                                                    val index = indexOf(it)
                                                    if (index == 0) return@apply
                                                    add(index, removeAt(index - 1))
                                                    scope.launch {
                                                        delay(150)
                                                        reorderableState.listState.animateScrollToItem(
                                                            index - 1
                                                        )
                                                    }
                                                }
                                            },
                                            onMoveDown = {
                                                state.workouts.apply {
                                                    val index = indexOf(it)
                                                    if (index == lastIndex) return@apply
                                                    add(index, removeAt(index + 1))
                                                    scope.launch {
                                                        delay(150)
                                                        reorderableState.listState.animateScrollToItem(
                                                            index + 1
                                                        )
                                                    }
                                                }
                                            },
                                            onDelete = {
                                                state.workouts.remove(it)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        FilledIconButton(
                            onClick = {
                                state.workouts.add(EditSessionDataSubpageBuilderItemData(id = state.workouts.size))
                                scope.launch {
                                    reorderableState.listState.animateScrollToItem(state.workouts.size - 1)
                                }
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
                        onSubmit()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(horizontal = 32.dp),
                    shape = RoundedCornerShape(23.dp)
                ) {
                    submitButtonContent()
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


/// Page Preview ///
@Preview(
    group = "SessionBuilderPage",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "SessionBuilderPage",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@SuppressLint("UnrememberedMutableState")
@Composable
fun SessionBuilderPagePreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            EditSessionDataSubpageBuilder(
                onSubmit = {},
                submitButtonContent = {},
                onScreen = true,
                userWorkouts = listOf(),
                state = EditSessionDataSubpageBuilderState(
                    workouts = mutableStateListOf(
                        EditSessionDataSubpageBuilderItemData(
                            id = 0,
                            workout = SessionBuilderWorkout(

                            )
                        ),
                        EditSessionDataSubpageBuilderItemData(
                            id = 1,
                            workout = SessionBuilderWorkout(

                            )
                        ),
                    )
                ),
            )
        }
    }
}
