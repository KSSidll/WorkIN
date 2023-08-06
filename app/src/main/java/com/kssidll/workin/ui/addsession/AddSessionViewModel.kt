package com.kssidll.workin.ui.addsession

import androidx.compose.runtime.*
import androidx.compose.ui.res.*
import androidx.lifecycle.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.data.repository.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

/// Data ///
/**
 * @param id: id used to encode this in the datobase, has to be unique, and can never be changed,
 *            we could use the ordinal, but i think having that set explicitly is less dangerous
 *            because it won't be accidentally sorted
 */
enum class RepetitionTypes(val id: Int) {
    Repetitions(0),
    Seconds(1),
    RiR(2)

    ;

    companion object {
        private val idMap = RepetitionTypes.values()
            .associateBy { it.id }

        fun getById(id: Int) = idMap[id]
    }
}

@Composable
fun RepetitionTypes.getTranslation(): String {
    return when (this) {
        RepetitionTypes.Repetitions -> stringResource(id = R.string.repetitions)
        RepetitionTypes.Seconds -> stringResource(id = R.string.seconds)
        RepetitionTypes.RiR -> stringResource(id = R.string.rir)
    }
}

/**
 * @param id: id used to encode this in the datobase, has to be unique, and can never be changed,
 *            we could use the ordinal, but i think having that set explicitly is less dangerous
 *            because it won't be accidentally sorted
 */
enum class WeightTypes(
    val id: Int,
    val hideWeight: Boolean = false
) {
    KG(0),
    LB(1),
    BodyMass(2, true),
    KGBodyMass(3),
    LBBodyMass(4),

    ;

    companion object {
        private val idMap = WeightTypes.values()
            .associateBy { it.id }

        fun getById(id: Int) = idMap[id]
    }
}

@Composable
fun WeightTypes.getTranslation(): String {
    return when (this) {
        WeightTypes.KG -> "KG"
        WeightTypes.LB -> "LB"
        WeightTypes.BodyMass -> stringResource(id = R.string.body_mass)
        WeightTypes.KGBodyMass -> "KG + ${stringResource(id = R.string.body_mass)}"
        WeightTypes.LBBodyMass -> "LB + ${stringResource(id = R.string.body_mass)}"
    }
}

// this shouldn't handle everything so it's only temporary
data class AddSessionWorkoutData(
    // id to keep track of reordering
    val id: Long,
    val workoutName: MutableState<String> = mutableStateOf(String()),
    val isError: MutableState<Boolean> = mutableStateOf(false),

    var workoutId: Long = 0,
    val repetitionCount: MutableState<Int> = mutableIntStateOf(0),
    val repetitionType: MutableState<RepetitionTypes> = mutableStateOf(RepetitionTypes.Repetitions),
    val weight: MutableState<Float> = mutableFloatStateOf(0F),
    val weightType: MutableState<WeightTypes> = mutableStateOf(WeightTypes.KG),
    val postRestTime: MutableState<Int> = mutableIntStateOf(0),
)

data class AddSessionData(
    var name: String,
    var description: String,
    var days: Byte,
    var workouts: List<AddSessionWorkoutData>
)


/// ViewModel ///
@HiltViewModel
class AddSessionViewModel @Inject constructor(
    sessionRepository: SessionRepository,
    workoutRepository: WorkoutRepository
): ViewModel() {
    private val sessionRepository: SessionRepository
    private val workoutRepository: WorkoutRepository

    init {
        this.sessionRepository = sessionRepository
        this.workoutRepository = workoutRepository
    }

    fun addSession(sessionData: AddSessionData) = viewModelScope.launch {
        val sessionId = sessionRepository.insert(
            session = Session(
                name = sessionData.name.trim(),
                description = sessionData.description.trim(),
                days = sessionData.days,
            )
        )

        sessionRepository.insertWorkouts(
            sessionData.workouts.mapIndexed { index, it ->
                SessionWorkout(
                    sessionId = sessionId,
                    workoutId = it.workoutId,
                    repetitionCount = it.repetitionCount.value,
                    repetitionType = it.repetitionType.value.id,
                    weight = it.weight.value,
                    weightType = it.weightType.value.id,
                    order = index,
                    restTime = it.postRestTime.value,
                )
            }
        )
    }

    fun getWorkouts(): Flow<List<Workout>> {
        return workoutRepository.getAllDescFlow()
    }
}
