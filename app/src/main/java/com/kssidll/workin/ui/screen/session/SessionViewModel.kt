package com.kssidll.workin.ui.screen.session

import androidx.lifecycle.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.repository.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionRepository: ISessionRepository,
): ViewModel() {
    lateinit var session: SessionWithWorkouts

    suspend fun fetchSession(sessionId: Long) {
        session = sessionRepository.sessionWithWorkoutsById(sessionId)!!
    }

    fun updateWorkoutSettings(sessionWorkout: FullSessionWorkout) = viewModelScope.launch {
        sessionRepository.updateSessionWorkout(
            SessionWorkout(
                sessionId = sessionWorkout.sessionId,
                workoutId = sessionWorkout.workout.id,
                repetitionCount = sessionWorkout.repetitionCount,
                repetitionType = sessionWorkout.repetitionType,
                weight = sessionWorkout.weight,
                weightType = sessionWorkout.weightType,
                order = sessionWorkout.order,
                restTime = sessionWorkout.restTime,
            )
        )
    }

    fun addLog(log: SessionWorkoutLog) = viewModelScope.launch {
        sessionRepository.insertSessionWorkoutLog(log)
    }

    fun newestLogsByWorkoutIdFlow(workoutId: Long): Flow<List<SessionWorkoutLog>> {
        return sessionRepository.newestLogsByWorkoutIdFlow(workoutId, 3)
    }
}
