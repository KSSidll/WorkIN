package com.kssidll.workin.ui.screen.addworkout

import android.database.sqlite.*
import androidx.lifecycle.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.repository.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import javax.inject.*

data class AddWorkoutData(
    val name: String,
    val description: String,
)

@HiltViewModel
class AddWorkoutViewModel @Inject constructor(
    workoutRepository: IWorkoutRepository,
): ViewModel() {
    private val workoutRepository: IWorkoutRepository

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
            }
                .await()
        } catch (e: SQLiteConstraintException) {
            throw e
        }
    }
}