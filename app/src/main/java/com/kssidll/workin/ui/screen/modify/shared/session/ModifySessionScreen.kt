package com.kssidll.workin.ui.screen.modify.shared.session


import android.content.res.Configuration.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.rounded.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.ui.component.*
import com.kssidll.workin.ui.screen.modify.shared.session.component.*
import com.kssidll.workin.ui.theme.*
import kotlinx.coroutines.*
import org.burnoutcrew.reorderable.*

enum class ModifySessionScreenPages {
    // Value ordinal affects page order
    Name,
    Days,
    SessionBuilder,
    ;

    companion object {
        private val pages = entries.associateBy { it.ordinal }

        /**
         * @throws IllegalAccessException when value matching [ordinal] doesn't exist
         */
        fun get(ordinal: Int): ModifySessionScreenPages {
            return pages[ordinal]
                ?: throw IllegalAccessException("Tried to access ordinal $ordinal of ${ModifySessionScreenPages::name}, which doesn't exist")
        }

        fun initialPage(): ModifySessionScreenPages {
            return Name
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ModifySessionScreen(
    state: ModifySessionScreenState,
    onBack: () -> Unit,
    onSubmit: () -> Unit,
    onDelete: (() -> Unit)? = null,
    submitButtonText: String,
    submitButtonIcon: ImageVector,
    allWorkouts: List<Workout>,
) {
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        initialPage = ModifySessionScreenPages.initialPage().ordinal,
        initialPageOffsetFraction = 0F,
        pageCount = { ModifySessionScreenPages.entries.size },
    )

    Scaffold(
        topBar = {
            WorkINTopAppBar(
                title = {
                    Row {
                        repeat(pagerState.pageCount) { iteration ->
                            val color = if (pagerState.currentPage == iteration)
                                MaterialTheme.colorScheme.tertiary
                            else
                                MaterialTheme.colorScheme.onSurface

                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .size(16.dp)
                            )
                        }
                    }
                },
                navigationIcon = if (pagerState.currentPage == 0) {
                    navigationIcon(
                        type = NavigationIcon.Types.Close,
                        onClick = onBack,
                        contentDescription = stringResource(id = R.string.cancel_add_session),
                    )
                } else navigationIcon(
                    type = NavigationIcon.Types.Back,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    },
                    contentDescription = stringResource(id = R.string.previous_page_session_builder),
                ),
                actions = {
                    if (onDelete != null) {
                        IconButton(
                            onClick = {
                                onDelete()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.DeleteForever,
                                contentDescription = stringResource(R.string.delete_session),
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(27.dp),
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.height(70.dp)) {
                    val scrollableLeft = pagerState.currentPage != 0

                    val leftButtonShape = RoundedCornerShape(
                        topStart = 23.dp,
                        bottomStart = 23.dp,
                        topEnd = 10.dp,
                        bottomEnd = 10.dp
                    )
                    val rightButtonShape = if (scrollableLeft)
                        RoundedCornerShape(
                            topStart = 10.dp,
                            bottomStart = 10.dp,
                            topEnd = 23.dp,
                            bottomEnd = 23.dp
                        )
                    else
                        RoundedCornerShape(23.dp)

                    if (scrollableLeft) {
                        Button(
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                }
                            },
                            shape = leftButtonShape,
                            colors = ButtonDefaults.buttonColors(
                                contentColor = MaterialTheme.colorScheme.primary,
                                containerColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                            modifier = Modifier
                                .fillMaxHeight()
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                                contentDescription = null,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))
                    }

                    Button(
                        onClick = {
                            if (pagerState.canScrollForward) {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            } else {
                                onSubmit()
                            }
                        },
                        shape = rightButtonShape,
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.primary,
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxHeight()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            if (pagerState.currentPage != pagerState.pageCount - 1) {
                                Icon(
                                    painter = painterResource(id = R.drawable.swipe_left),
                                    contentDescription = null,
                                    modifier = Modifier.size(32.dp)
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Text(
                                    text = stringResource(R.string.next),
                                    style = Typography.titleLarge,
                                )

                                Spacer(modifier = Modifier.width(3.dp))

                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                    contentDescription = null,
                                    modifier = Modifier.size(32.dp)
                                )
                            } else {
                                Icon(
                                    imageVector = submitButtonIcon,
                                    contentDescription = null,
                                    modifier = Modifier.size(26.dp)
                                )

                                Spacer(modifier = Modifier.width(6.dp))

                                Text(
                                    text = submitButtonText,
                                    style = Typography.titleLarge,
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            HorizontalPager(
                state = pagerState,
                beyondBoundsPageCount = pagerState.pageCount,
            ) { page ->
                when (ModifySessionScreenPages.get(page)) {
                    ModifySessionScreenPages.Name -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            Spacer(modifier = Modifier.height(64.dp))

                            ModifySessionNamePage(
                                nameEnabled = !state.loadingName.value,
                                nameErrorMessage = if (state.attemptedToSubmit.value) {
                                    if (state.nameBlankError.value) stringResource(id = R.string.field_required)
                                    else if (state.nameDuplicateError.value) stringResource(id = R.string.session_name_duplicate)
                                    else null
                                } else null,
                                name = state.name.value,
                                onNameChange = {
                                    state.name.value = it
                                    state.validateName()
                                    state.nameDuplicateError.value = false
                                },
                                descriptionEnabled = !state.loadingDescription.value,
                                description = state.description.value,
                                onDescriptionChange = {
                                    state.description.value = it
                                },
                            )
                        }
                    }

                    ModifySessionScreenPages.Days -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            Spacer(modifier = Modifier.height(64.dp))

                            ModifySessionDaysPage(
                                enabled = !state.loadingDays.value,
                                encodedDays = state.encodedDays.value,
                                onDaysChange = {
                                    state.encodedDays.value = it
                                }
                            )
                        }
                    }

                    ModifySessionScreenPages.SessionBuilder -> {
                        val reorderableState = rememberReorderableLazyListState(
                            onMove = { from, to ->
                                state.workouts.apply {
                                    add(to.index, removeAt(from.index))
                                }
                            }
                        )

                        ModifySessionBuilderPage(
                            enabled = !state.loadingWorkouts.value,
                            onScreen = ModifySessionScreenPages.get(page) == ModifySessionScreenPages.SessionBuilder,
                            reorderableState = reorderableState,
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
                            sessionWorkouts = state.workouts.toList(),
                            onSessionWorkoutAdd = {
                                val id =
                                    if (state.workouts.isEmpty()) 0 else state.workouts.maxOf { it.id } + 1
                                state.workouts.add(SessionBuilderWorkout(id = id))
                            },
                            onSessionWorkoutRemove = {
                                state.workouts.remove(it)
                            },
                            workouts = allWorkouts,
                        )
                    }
                }
            }
        }
    }
}

data class SessionBuilderWorkout(
    override val id: Long,
    override val workout: MutableState<Workout?> = mutableStateOf(null),
    override val isError: MutableState<Boolean> = mutableStateOf(false),
    override val repetitionCount: MutableState<Int> = mutableIntStateOf(0),
    override val repetitionType: MutableState<RepetitionTypes> = mutableStateOf(RepetitionTypes.Repetitions),
    override val weight: MutableState<Float> = mutableFloatStateOf(0F),
    override val weightType: MutableState<WeightTypes> = mutableStateOf(WeightTypes.KG),
    override val postRestTime: MutableState<Int> = mutableIntStateOf(0),
): SessionBuilderWorkoutItem

data class ModifySessionScreenState(
    val name: MutableState<String> = mutableStateOf(String()),
    val description: MutableState<String> = mutableStateOf(String()),
    val encodedDays: MutableState<Byte> = mutableStateOf(0),
    val workouts: SnapshotStateList<SessionBuilderWorkout> = mutableStateListOf(),

    val nameBlankError: MutableState<Boolean> = mutableStateOf(false),
    val nameDuplicateError: MutableState<Boolean> = mutableStateOf(false),

    val attemptedToSubmit: MutableState<Boolean> = mutableStateOf(false),

    val loadingName: MutableState<Boolean> = mutableStateOf(false),
    val loadingDescription: MutableState<Boolean> = mutableStateOf(false),
    val loadingDays: MutableState<Boolean> = mutableStateOf(false),
    val loadingWorkouts: MutableState<Boolean> = mutableStateOf(false),
)

/**
 * Validates name field and updates its error flag
 * @return `true` if field is of correct value, `false` otherwise
 */
fun ModifySessionScreenState.validateName(): Boolean {
    return name.value.isNotBlank()
        .also { nameBlankError.value = !it }
}

/**
 * Validates state fields and updates state flags
 * @return `true` if all fields are of correct value, `false` otherwise
 */
fun ModifySessionScreenState.validate(): Boolean {
    var workoutsValidated = true
    workouts.forEach {
        it.validate()
        if (it.isError.value) workoutsValidated = false
    }

    val nameValidated = validateName()

    return workoutsValidated && nameValidated
}

/**
 * Performs data validation and tries to extract embedded data
 * @return `null` if validation sets error flags, extracted data otherwise
 */
fun ModifySessionScreenState.validateAndExtractSessionOrNull(sessionId: Long = 0): SessionWithWorkouts? {
    name.value = name.value.trim()
    description.value = description.value.trim()

    if (!validate()) return null

    return SessionWithWorkouts(
        session = Session(
            id = sessionId,
            name = name.value,
            description = description.value,
            days = encodedDays.value,
        ),
        workouts = workouts.mapIndexed { index, it ->
            FullSessionWorkout(
                sessionId = sessionId,
                workout = it.workout.value!!,
                repetitionCount = it.repetitionCount.value,
                repetitionType = it.repetitionType.value,
                weight = it.weight.value,
                weightType = it.weightType.value,
                order = index,
                restTime = it.postRestTime.value,
            )
        }
    )
}

@Preview(
    group = "Modify Session Screen",
    name = "Dark",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    group = "Modify Session Screen",
    name = "Light",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
private fun ModifySessionScreenPreview() {
    WorkINTheme {
        Surface {
            ModifySessionScreen(
                state = ModifySessionScreenState(),
                onBack = {},
                onSubmit = {},
                onDelete = {},
                submitButtonText = "Submit",
                submitButtonIcon = Icons.Default.Edit,
                allWorkouts = listOf(),
            )
        }
    }
}

@Preview(
    group = "Modify Session Screen",
    name = "Dark No Delete",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    group = "Modify Session Screen",
    name = "Light No Delete",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
private fun ModifySessionScreenNoDeletePreview() {
    WorkINTheme {
        Surface {
            ModifySessionScreen(
                state = ModifySessionScreenState(),
                onBack = {},
                onSubmit = {},
                submitButtonText = "Submit",
                submitButtonIcon = Icons.Default.Edit,
                allWorkouts = listOf(),
            )
        }
    }
}

