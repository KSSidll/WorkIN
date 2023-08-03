package com.kssidll.workin.ui.addworkout

import androidx.lifecycle.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.data.repository.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import javax.inject.*

/// Data ///
data class AddWorkoutData(
    val name: String,
    val description: String,
)


/// ViewModel ///
@HiltViewModel
class AddWorkoutViewModel @Inject constructor(
    workoutRepository: WorkoutRepository,
): ViewModel() {
    private val workoutRepository: WorkoutRepository

    init {
        this.workoutRepository = workoutRepository
    }

    fun addWorkout(workoutData: AddWorkoutData) = viewModelScope.launch {
        workoutRepository.insert(
            workout = Workout(
                name = workoutData.name.trim(),
                description = workoutData.description.trim()
            )
        )
    }
}