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
    private val sessionRepository: ISessionRepository,
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
            workoutRepository.updateWorkout(workout)
        } catch (e: SQLiteConstraintException) {
            screenState.nameDuplicateError.value = true
            return@async false
        }

        return@async true
    }
        .await()

    /**
     * Tries to delete workout with provided [workoutId], sets showDeleteWarning flag in state if operation would require deleting foreign constrained data,
     * state deleteWarningConfirmed flag needs to be set to start foreign constrained data deletion
     * @return `true` when workout gets deleted
     */
    suspend fun deleteWorkout(workoutId: Long) = viewModelScope.async {
        // return true if no such workout exists
        val workout = workoutRepository.workoutById(workoutId) ?: return@async true

        val sessions = sessionRepository.allSessionsByWorkoutId(workoutId)

        if (sessions.isNotEmpty() && !screenState.deleteWarningConfirmed.value) {
            screenState.showDeleteWarning.value = true
            return@async false
        } else {
            sessionRepository.deleteSession(sessions)
            workoutRepository.deleteWorkout(workout)
            return@async true
        }
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

        val workout = workoutRepository.workoutById(workoutId)
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
