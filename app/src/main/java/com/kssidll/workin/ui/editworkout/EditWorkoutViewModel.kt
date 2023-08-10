package com.kssidll.workin.ui.editworkout

import android.database.sqlite.*
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

    /**
     * @throws SQLiteConstraintException Attempted to insert a duplicate value,
     * workout name has to be unique
     */
    suspend fun updateWorkout(workout: Workout) {
        try {
            viewModelScope.async {
                workoutRepository.update(workout)
            }.await()
        } catch (e: SQLiteConstraintException) {
            throw e
        }
    }

    fun deleteWorkout() = viewModelScope.launch {
        workoutRepository.delete(workout)
    }
}
