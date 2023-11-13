package com.kssidll.workin.ui.screen.home.dashboard

import androidx.lifecycle.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.repository.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val sessionRepository: ISessionRepository,
): ViewModel() {
    fun allSessionsWithWorkoutsFlow(): Flow<List<SessionWithWorkouts>> {
        return sessionRepository.allSessionsWithWorkoutsFlow()
    }
}
