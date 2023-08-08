package com.kssidll.workin.ui.editworkout

import androidx.lifecycle.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.data.repository.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import javax.inject.*

/// ViewModel ///
@HiltViewModel
class EditWorkoutViewModel @Inject constructor(
    workoutRepository: WorkoutRepository,
): ViewModel() {
    private val workoutRepository: WorkoutRepository

    lateinit var workout: Workout

    init {
        this.workoutRepository = workoutRepository
    }

    suspend fun fetchWorkout(workoutId: Long) {
        workout = workoutRepository.getById(workoutId)
    }

    fun updateWorkout(workout: Workout) = viewModelScope.launch {
        workoutRepository.update(workout)
    }

    fun deleteWorkout() = viewModelScope.launch {
        workoutRepository.delete(workout)
    }
}
