package com.kssidll.workin.ui.workouts

import androidx.lifecycle.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.data.repository.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class WorkoutsViewModel @Inject constructor(
    workoutRepository: WorkoutRepository,
    sessionRepository: SessionRepository,
): ViewModel() {
    private val workoutRepository: WorkoutRepository
    private val sessionRepository: SessionRepository

    init {
        this.workoutRepository = workoutRepository
        this.sessionRepository = sessionRepository
    }

    fun getAllWorkoutsDescFlow(): Flow<List<Workout>> {
        return workoutRepository.getAllDescFlow()
    }

    fun getAllSessionsDescFlow(): Flow<List<SessionWithWorkouts>> {
        return sessionRepository.getAllDescFlow()
    }
}
