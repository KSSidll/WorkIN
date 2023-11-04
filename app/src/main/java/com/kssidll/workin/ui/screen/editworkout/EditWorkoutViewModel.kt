package com.kssidll.workin.ui.screen.editworkout

import android.database.sqlite.*
import androidx.lifecycle.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.repository.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import javax.inject.*

@HiltViewModel
class EditWorkoutViewModel @Inject constructor(
    private val workoutRepository: IWorkoutRepository,
): ViewModel() {
    lateinit var workout: Workout

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
            }
                .await()
        } catch (e: SQLiteConstraintException) {
            throw e
        }
    }

    fun deleteWorkout() = viewModelScope.launch {
        workoutRepository.delete(workout)
    }
}
