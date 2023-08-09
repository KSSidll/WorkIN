package com.kssidll.workin.ui.addsession

import androidx.lifecycle.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.data.repository.*
import com.kssidll.workin.ui.shared.session.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

/// ViewModel ///
@HiltViewModel
class AddSessionViewModel @Inject constructor(
    sessionRepository: SessionRepository,
    workoutRepository: WorkoutRepository
): ViewModel() {
    private val sessionRepository: SessionRepository
    private val workoutRepository: WorkoutRepository

    init {
        this.sessionRepository = sessionRepository
        this.workoutRepository = workoutRepository
    }

    fun addSession(sessionData: EditSessionDataSubpageState) = viewModelScope.launch {
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

    fun getWorkouts(): Flow<List<Workout>> {
        return workoutRepository.getAllDescFlow()
    }
}
