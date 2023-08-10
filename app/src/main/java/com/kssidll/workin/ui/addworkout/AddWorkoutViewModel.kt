package com.kssidll.workin.ui.addworkout

import android.database.sqlite.*
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

    /**
     * @return id of the newly inserted workout
     * @throws SQLiteConstraintException Attempted to insert a duplicate value,
     * workout name has to be unique
     */
    suspend fun addWorkout(workoutData: AddWorkoutData): Long {
        try {
            return viewModelScope.async {
                workoutRepository.insert(
                    workout = Workout(
                        name = workoutData.name.trim(),
                        description = workoutData.description.trim()
                    )
                )
            }.await()
        } catch (e: SQLiteConstraintException) {
            throw e
        }
    }
}