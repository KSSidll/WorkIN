package com.kssidll.workin.ui.screen.addsession

import android.database.sqlite.*
import androidx.lifecycle.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.repository.*
import com.kssidll.workin.ui.screen.session.component.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class AddSessionViewModel @Inject constructor(
    private val sessionRepository: ISessionRepository,
    private val workoutRepository: IWorkoutRepository
): ViewModel() {
    /**
     * @throws SQLiteConstraintException Attempted to insert a duplicate value,
     * session name has to be unique
     */
    suspend fun addSession(sessionData: EditSessionDataSubpageState) {
        try {
            viewModelScope.async {
                val sessionId = sessionRepository.insert(
                    session = Session(
                        name = sessionData.name.trim(),
                        description = sessionData.description.trim(),
                        days = sessionData.days,
                    )
                )

                sessionRepository.insertWorkouts(
                    sessionData.workouts.mapIndexed { index, it ->
                        SessionWorkout(
                            sessionId = sessionId,
                            workoutId = it.workoutId,
                            repetitionCount = it.repetitionCount.value,
                            repetitionType = it.repetitionType.value.id,
                            weight = it.weight.value,
                            weightType = it.weightType.value.id,
                            order = index,
                            restTime = it.postRestTime.value,
                        )
                    }
                )
            }
                .await()
        } catch (e: SQLiteConstraintException) {
            throw e
        }
    }

    fun getWorkouts(): Flow<List<Workout>> {
        return workoutRepository.getAllDescFlow()
    }
}
