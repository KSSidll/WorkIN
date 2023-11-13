package com.kssidll.workin.ui.screen.modify.edit.session

import android.database.sqlite.*
import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.repository.*
import com.kssidll.workin.ui.screen.modify.shared.session.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class EditSessionViewModel @Inject constructor(
    private val sessionRepository: ISessionRepository,
    private val workoutRepository: IWorkoutRepository,
): ViewModel() {
    val screenState: ModifySessionScreenState = ModifySessionScreenState()

    /**
     * Tries to update session with provided [sessionId] with current screen state data
     * @return `true` if no repository constraint is violated, `false` otherwise
     */
    suspend fun updateSession(sessionId: Long) = viewModelScope.async {
        screenState.attemptedToSubmit.value = true
        val session = screenState.validateAndExtractSessionOrNull(sessionId) ?: return@async true

        try {
            sessionRepository.deleteSessionWorkout(
                sessionRepository.allSessionWorkoutsBySessionId(
                    sessionId
                )
            )

            sessionRepository.updateSession(session.session)

            sessionRepository.insertSessionWorkout(session.workouts.map {
                SessionWorkout(
                    sessionId = sessionId,
                    workoutId = it.workout.id,
                    repetitionCount = it.repetitionCount,
                    repetitionType = it.repetitionType,
                    weight = it.weight,
                    weightType = it.weightType,
                    order = it.order,
                    restTime = it.restTime,
                )
            })

        } catch (e: SQLiteConstraintException) {
            screenState.nameDuplicateError.value = true
            return@async false
        }

        return@async true
    }
        .await()

    /**
     * Tries to delete session with provided [sessionId]
     * @return `true` when session gets deleted
     */
    suspend fun deleteSession(sessionId: Long) = viewModelScope.async {
        // return true if no such session exists
        val session = sessionRepository.sessionById(sessionId) ?: return@async true

        sessionRepository.deleteSessionWorkout(
            sessionRepository.allSessionWorkoutsBySessionId(
                sessionId
            )
        )
        sessionRepository.deleteSession(session)
        return@async true
    }
        .await()

    /**
     * Updates data in the screen state
     * @return true if provided [sessionId] wos valid, false otherwise
     */
    suspend fun updateState(sessionId: Long) = viewModelScope.async {
        screenState.loadingName.value = true
        screenState.loadingDescription.value = true
        screenState.loadingDays.value = true
        screenState.loadingWorkouts.value = true

        val dispose = {
            screenState.loadingName.value = false
            screenState.loadingDescription.value = false
            screenState.loadingDays.value = false
            screenState.loadingWorkouts.value = false
        }

        val session = sessionRepository.sessionWithWorkoutsById(sessionId)
        if (session == null) {
            dispose()
            return@async false
        }

        screenState.name.value = session.session.name
        screenState.description.value = session.session.description
        screenState.encodedDays.value = session.session.days
        screenState.workouts.clear()
        screenState.workouts.addAll(session.workouts.mapIndexed { index, it ->
            SessionBuilderWorkout(
                id = index.toLong(),
                workout = mutableStateOf(it.workout),
                repetitionCount = mutableIntStateOf(it.repetitionCount),
                repetitionType = mutableStateOf(RepetitionTypes.getById(it.repetitionType)!!),
                weight = mutableFloatStateOf(it.weight),
                weightType = mutableStateOf(WeightTypes.getById(it.weightType)!!),
                postRestTime = mutableIntStateOf(it.restTime),
            )
        })

        dispose()
        return@async true
    }
        .await()

    fun allWorkoutsFlow(): Flow<List<Workout>> {
        return workoutRepository.allWorkoutsFlow()
    }
}
