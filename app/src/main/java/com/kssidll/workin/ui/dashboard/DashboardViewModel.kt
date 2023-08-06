package com.kssidll.workin.ui.dashboard

import androidx.lifecycle.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.data.repository.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class DashboardViewModel @Inject constructor(
    sessionRepository: SessionRepository,
): ViewModel() {
    private val sessionRepository: SessionRepository

    init {
        this.sessionRepository = sessionRepository
    }

    fun getAllSessionsDescFlow(): Flow<List<SessionWithFullSessionWorkouts>> {
        return sessionRepository.getAllMergedSessionsWithWorkouts()
    }
}
