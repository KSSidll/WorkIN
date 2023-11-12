package com.kssidll.workin.ui.screen.modify.shared.session.component

import android.content.res.*
import androidx.activity.compose.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.ui.component.*
import com.kssidll.workin.ui.theme.*
import org.burnoutcrew.reorderable.*

/**
 * @param enabled controls the enabled state of the page, interactions are disabled when false
 * @param onScreen information for the component about whether it's visible on screen or not, required for proper back gesture handling
 * @param reorderableState the reorderable state of the workout list in the component, handles reordering animations
 * @param onMoveUp callback for handling session workout move up reorder request. The requested for reorder workout comes as a parameter for the callback
 * @param onMoveDown callback for handling session workout move down reorder request. The requested for reorder workout comes as a parameter for the callback
 * @param sessionWorkouts builders' session workouts
 * @param onSessionWorkoutAdd callback for handling builder session workout addition
 * @param onSessionWorkoutRemove callback for handling builder session workout removal. The requested for removal workout comes as a parameter for the callback
 * @param workouts list of available workouts to build the session from
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ModifySessionBuilderPage(
    enabled: Boolean,
    onScreen: Boolean,
    reorderableState: ReorderableLazyListState,
    onMoveUp: (workout: SessionBuilderWorkoutItem) -> Unit,
    onMoveDown: (workout: SessionBuilderWorkoutItem) -> Unit,
    sessionWorkouts: List<SessionBuilderWorkoutItem>,
    onSessionWorkoutAdd: () -> Unit,
    onSessionWorkoutRemove: (workout: SessionBuilderWorkoutItem) -> Unit,
    workouts: List<Workout>,
) {
    var isWorkoutSearch by remember {
        mutableStateOf(false)
    }

    var workoutSearchingId by remember {
        mutableLongStateOf(-1)
    }

    BackHandler(enabled = isWorkoutSearch && onScreen) {
        isWorkoutSearch = false
    }

    Scaffold(
        floatingActionButton = {
            if (!isWorkoutSearch) {
                FloatingActionButton(
                    onClick = {
                        if (enabled) {
                            onSessionWorkoutAdd()
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = stringResource(id = R.string.add_new_workout_to_session_builder),
                        modifier = Modifier.size(37.dp)
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (isWorkoutSearch) {
                SelectWorkoutSubpage(
                    workouts = workouts,
                    onSelect = {
                        sessionWorkouts.find { it.id == workoutSearchingId }
                            ?.apply {
                                this.workout.value = it
                            }

                        isWorkoutSearch = false
                    },
                )
            } else {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth()
                ) {
                    LazyColumn(
                        state = reorderableState.listState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .reorderable(reorderableState)
                    ) {
                        items(sessionWorkouts, key = { it.id }) {
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
                                    ModifySessionBuilderItem(
                                        item = it,
                                        reorderableState = reorderableState,
                                        onWorkoutSearch = {
                                            workoutSearchingId = it.id
                                            isWorkoutSearch = true
                                        },
                                        onDelete = {
                                            onSessionWorkoutRemove(it)
                                        },
                                        onMoveUp = {
                                            onMoveUp(it)
                                        },
                                        onMoveDown = {
                                            onMoveDown(it)
                                        },
                                        showTimer = it != sessionWorkouts.last(),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    group = "Modify Session Builder Page",
    name = "Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Modify Session Builder Page",
    name = "Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun ModifySessionBuilderPagePreview() {
    WorkINTheme {
        Surface {
            ModifySessionBuilderPage(
                enabled = false,
                onScreen = true,
                reorderableState = rememberReorderableLazyListState(onMove = { _, _ -> }),
                onMoveUp = {},
                onMoveDown = {},
                sessionWorkouts = listOf(),
                onSessionWorkoutAdd = {},
                onSessionWorkoutRemove = {},
                workouts = listOf(),
            )
        }
    }
}
