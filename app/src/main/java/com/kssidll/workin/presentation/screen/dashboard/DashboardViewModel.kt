package com.kssidll.workin.presentation.screen.dashboard

import androidx.lifecycle.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.repository.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.flow.*
import javax.inject.*


/// ViewModel ///
@HiltViewModel
class DashboardViewModel @Inject constructor(
    sessionRepository: ISessionRepository,
): ViewModel() {
    private val sessionRepository: ISessionRepository

    init {
        this.sessionRepository = sessionRepository
    }

    fun getAllSessionsDescFlow(): Flow<List<SessionWithFullSessionWorkouts>> {
        return sessionRepository.getAllMergedSessionsWithWorkouts()
    }
}
