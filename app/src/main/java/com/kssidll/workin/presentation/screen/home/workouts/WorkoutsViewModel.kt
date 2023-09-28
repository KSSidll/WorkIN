package com.kssidll.workin.presentation.screen.home.workouts

import androidx.lifecycle.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.repository.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.flow.*
import javax.inject.*

/// ViewModel ///
@HiltViewModel
class WorkoutsViewModel @Inject constructor(
    workoutRepository: IWorkoutRepository,
    sessionRepository: ISessionRepository,
): ViewModel() {
    private val workoutRepository: IWorkoutRepository
    private val sessionRepository: ISessionRepository

    init {
        this.workoutRepository = workoutRepository
        this.sessionRepository = sessionRepository
    }

    fun getAllWorkoutsDescFlow(): Flow<List<Workout>> {
        return workoutRepository.getAllDescFlow()
    }

    fun getAllSessionsDescFlow(): Flow<List<SessionWithFullSessionWorkouts>> {
        return sessionRepository.getAllMergedSessionsWithWorkouts()
    }
}
