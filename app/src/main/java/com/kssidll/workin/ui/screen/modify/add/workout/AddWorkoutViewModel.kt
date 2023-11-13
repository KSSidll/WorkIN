package com.kssidll.workin.ui.screen.modify.add.workout

import android.database.sqlite.*
import androidx.lifecycle.*
import com.kssidll.workin.domain.repository.*
import com.kssidll.workin.ui.screen.modify.shared.workout.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import javax.inject.*

@HiltViewModel
class AddWorkoutViewModel @Inject constructor(
    private val workoutRepository: IWorkoutRepository,
): ViewModel() {
    val screenState: ModifyWorkoutScreenState = ModifyWorkoutScreenState()

    /**
     * Tries to add item to the repository
     * @return Id of the newly inserted row, null if operation failed
     */
    suspend fun addWorkout() = viewModelScope.async {
        screenState.attemptedToSubmit.value = true
        val workout = screenState.validateAndExtractWorkoutOrNull() ?: return@async null

        try {
            return@async workoutRepository.insertWorkout(workout)
        } catch (e: SQLiteConstraintException) {
            screenState.nameDuplicateError.value = true
            return@async null
        }
    }
        .await()
}