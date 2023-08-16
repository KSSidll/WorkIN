package com.kssidll.workin.ui.session

import androidx.lifecycle.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.data.repository.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import javax.inject.*

/// ViewModel ///
@HiltViewModel
class SessionViewModel @Inject constructor(
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

    fun updateWorkoutSettings(sessionWorkout: SessionWorkout) = viewModelScope.launch {
        sessionRepository.updateSessionWorkout(sessionWorkout)
    }

    fun addLog(log: SessionWorkoutLog) = viewModelScope.launch {
        sessionRepository.insertSessionLog(log)
    }

    suspend fun getLastWorkoutLogs(workoutId: Long): List<SessionWorkoutLog> {
        return sessionRepository.getLastWorkoutLogs(workoutId, 3)
    }
}
