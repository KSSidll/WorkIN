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
): ViewModel() {
    private val workoutRepository: WorkoutRepository

    init {
        this.workoutRepository = workoutRepository
    }

    fun getAllDescFlow(): Flow<List<Workout>> {
        return workoutRepository.getAllDescFlow()
    }
}
