package com.kssidll.workin.ui.editsession

import androidx.lifecycle.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.data.repository.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

/// ViewModel ///
@HiltViewModel
class EditSessionViewModel @Inject constructor(
    sessionRepository: SessionRepository,
    workoutRepository: WorkoutRepository,
): ViewModel() {
    private val sessionRepository: SessionRepository
    private val workoutRepository: WorkoutRepository

    lateinit var session: SessionWithFullSessionWorkouts
    private lateinit var _originalSession: SessionWithFullSessionWorkouts

    init {
        this.sessionRepository = sessionRepository
        this.workoutRepository = workoutRepository
    }

    suspend fun fetchSession(sessionId: Long) {
        session = sessionRepository.getMergedSessionWithWorkoutsById(sessionId)
        _originalSession = session.copy()
    }

    fun updateSession(session: SessionWithFullSessionWorkouts) = viewModelScope.launch {
        sessionRepository.update(session.session)

        sessionRepository.deleteWorkouts(_originalSession.workouts.map {
            it.sessionWorkout
        })

        sessionRepository.insertWorkouts(session.workouts.mapIndexed { index, it ->
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

    fun getWorkouts(): Flow<List<Workout>> {
        return workoutRepository.getAllDescFlow()
    }
}
