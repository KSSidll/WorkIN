package com.kssidll.workin.ui.session

import androidx.lifecycle.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.data.repository.*
import dagger.hilt.android.lifecycle.*
import javax.inject.*

@HiltViewModel
class SessionViewModel @Inject constructor(
    sessionRepository: SessionRepository,
): ViewModel() {
    private val sessionRepository: SessionRepository

    lateinit var session: SessionWithFullSessionWorkouts

    init {
        this.sessionRepository = sessionRepository
    }

    suspend fun fetchSession(sessionId: Long) {
        session = sessionRepository.getMergedSessionWithWorkoutsById(sessionId)
    }
}
