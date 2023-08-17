package com.kssidll.workin.ui.session

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.*
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

    private var lastUsedWorkoutId: Long = 0
    var workoutLogs: SnapshotStateList<SessionWorkoutLog> = mutableStateListOf()

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
        if (log.workoutId == lastUsedWorkoutId) getLastWorkoutLogs(lastUsedWorkoutId)
    }

    suspend fun getLastWorkoutLogs(workoutId: Long) {
        lastUsedWorkoutId = workoutId
        workoutLogs.clear()
        workoutLogs.addAll(sessionRepository.getLastWorkoutLogs(workoutId, 3))
    }
}
