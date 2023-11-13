package com.kssidll.workin.ui.screen.modify.add.session

import android.database.sqlite.*
import androidx.lifecycle.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.repository.*
import com.kssidll.workin.ui.screen.modify.shared.session.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class AddSessionViewModel @Inject constructor(
    private val sessionRepository: ISessionRepository,
    private val workoutRepository: IWorkoutRepository
): ViewModel() {
    val screenState: ModifySessionScreenState = ModifySessionScreenState()

    /**
     * Tries to add session with current screen state data
     * @return `true` if no repository constraint is violated, `false` otherwise
     */
    suspend fun addSession() = viewModelScope.async {
        screenState.attemptedToSubmit.value = true
        val session = screenState.validateAndExtractSessionOrNull() ?: return@async true

        try {
            val newSessionId = sessionRepository.insertSession(session.session)

            sessionRepository.insertSessionWorkout(session.workouts.map {
                SessionWorkout(
                    sessionId = newSessionId,
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

    fun allWorkouts(): Flow<List<Workout>> {
        return workoutRepository.allWorkoutsFlow()
    }
}
