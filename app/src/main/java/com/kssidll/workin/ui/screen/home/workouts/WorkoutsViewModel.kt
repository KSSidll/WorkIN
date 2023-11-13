package com.kssidll.workin.ui.screen.home.workouts

import androidx.lifecycle.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.repository.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class WorkoutsViewModel @Inject constructor(
    private val workoutRepository: IWorkoutRepository,
    private val sessionRepository: ISessionRepository,
): ViewModel() {
    fun allWorkoutsByNewestFirstFlow(): Flow<List<Workout>> {
        return workoutRepository.allWorkoutsByNewestFirstFlow()
    }

    fun allSessionsByNewestFirstFlow(): Flow<List<SessionWithWorkouts>> {
        return sessionRepository.allSessionsWithWorkoutsNewestFirstFlow()
    }
}
