package com.kssidll.workin.ui.editsession

import androidx.lifecycle.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.data.repository.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import javax.inject.*

/// ViewModel ///
@HiltViewModel
class EditSessionViewModel @Inject constructor(
    sessionRepository: SessionRepository,
): ViewModel() {
    private val sessionRepository: SessionRepository

    lateinit var session: SessionWithFullSessionWorkouts

    init {
        this.sessionRepository = sessionRepository
    }

    suspend fun fetchSession(sessionId: Long) {
        session = sessionRepository.getMergedSessionWithWorkoutsById(sessionId)
    }

    fun updateSession(session: SessionWithFullSessionWorkouts) = viewModelScope.launch {
        sessionRepository.update(session.session)
        sessionRepository.updateWorkouts(session.workouts.mapIndexed { index, it ->
            SessionWorkout(
                sessionId = it.sessionWorkout.sessionId,
                workoutId = it.workout.id,
                repetitionCount = it.sessionWorkout.repetitionCount,
                repetitionType = it.sessionWorkout.repetitionType,
                weight = it.sessionWorkout.weight,
                weightType = it.sessionWorkout.weightType,
                order = index,
                restTime = it.sessionWorkout.restTime,
            )
        })
    }

    fun deleteSession() = viewModelScope.launch {
        sessionRepository.delete(session.session)
    }
}
