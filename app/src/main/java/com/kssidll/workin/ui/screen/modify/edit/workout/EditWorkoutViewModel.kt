package com.kssidll.workin.ui.screen.modify.edit.workout

import android.database.sqlite.*
import androidx.lifecycle.*
import com.kssidll.workin.domain.repository.*
import com.kssidll.workin.ui.screen.modify.shared.workout.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import javax.inject.*

@HiltViewModel
class EditWorkoutViewModel @Inject constructor(
    private val workoutRepository: IWorkoutRepository,
): ViewModel() {
    val screenState: ModifyWorkoutScreenState = ModifyWorkoutScreenState()

    /**
     * Tries to update workout with provided [workoutId] with current screen state data
     * @return `true` if no repository constraint is violated, `false` otherwise
     */
    suspend fun updateWorkout(workoutId: Long) = viewModelScope.async {
        screenState.attemptedToSubmit.value = true
        val workout = screenState.validateAndExtractWorkoutOrNull(workoutId) ?: return@async true

        try {
            workoutRepository.update(workout)
        } catch (e: SQLiteConstraintException) {
            screenState.nameDuplicateError.value = true
            return@async false
        }

        return@async true
    }
        .await()

    /**
     * Tries to delete workout with provided [workoutId]
     * @return `true` when workout gets deleted
     */
    suspend fun deleteWorkout(workoutId: Long) = viewModelScope.async {
        val workout = workoutRepository.getById(workoutId) ?: return@async true

        workoutRepository.delete(workout)
        return@async true
    }
        .await()

    /**
     * Updates data in the screen state
     * @return true if provided [workoutId] wos valid, false otherwise
     */
    suspend fun updateState(workoutId: Long) = viewModelScope.async {
        screenState.loadingName.value = true
        screenState.loadingDescription.value = true

        val dispose = {
            screenState.loadingName.value = false
            screenState.loadingDescription.value = false
        }

        val workout = workoutRepository.getById(workoutId)
        if (workout == null) {
            dispose()
            return@async false
        }

        screenState.name.value = workout.name
        screenState.description.value = workout.description

        dispose()
        return@async true
    }
        .await()

}
