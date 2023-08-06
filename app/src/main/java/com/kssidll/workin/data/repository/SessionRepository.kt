package com.kssidll.workin.data.repository

import com.kssidll.workin.data.dao.*
import com.kssidll.workin.data.data.*
import kotlinx.coroutines.flow.*

class SessionRepository(private val sessionDao: SessionDao) {
    fun getAllDescFlow(): Flow<List<SessionWithSessionWorkouts>> {
        return sessionDao.getAllDescFlow()
    }

    fun getAllMergedSessionsWithWorkouts(): Flow<List<SessionWithFullSessionWorkouts>> {
        return sessionDao.getAllDescFlow()
            .map { list ->
                list.map {
                    it.merge(sessionDao.getFullSessionWorkouts(it.session.id))
                }
            }
    }

    suspend fun insert(session: Session): Long {
        return sessionDao.insert(session)
    }

    suspend fun insertWorkouts(workouts: List<SessionWorkout>): List<Long> {
        return sessionDao.insertWorkouts(workouts)
    }

    suspend fun update(session: Session) {
        sessionDao.update(session)
    }

    suspend fun delete(session: Session) {
        sessionDao.delete(session)
    }
}

