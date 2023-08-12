package com.kssidll.workin.data.repository

import com.kssidll.workin.data.dao.*
import com.kssidll.workin.data.data.*
import kotlinx.coroutines.flow.*

class SessionRepository(private val sessionDao: SessionDao) {
    fun getAllMergedSessionsWithWorkouts(): Flow<List<SessionWithFullSessionWorkouts>> {
        return sessionDao.getAllDescFlow()
            .map { list ->
                list.map {
                    it.merge(sessionDao.getFullSessionWorkouts(it.session.id))
                }
            }
    }

    suspend fun getMergedSessionWithWorkoutsById(sessionId: Long): SessionWithFullSessionWorkouts {
        return sessionDao.getById(sessionId)
            .merge(
                sessionDao.getFullSessionWorkouts(sessionId)
            )
    }

    suspend fun insert(session: Session): Long {
        return sessionDao.insert(session)
    }

    suspend fun insertWorkouts(workouts: List<SessionWorkout>): List<Long> {
        return sessionDao.insertWorkouts(workouts)
    }

    suspend fun insertSessionLog(log: SessionWorkoutLog): Long {
        return sessionDao.insertSessionWorkoutLog(log)
    }

    suspend fun update(session: Session) {
        sessionDao.update(session)
    }

    suspend fun updateSessionWorkout(sessionWorkout: SessionWorkout) {
        sessionDao.updateSessionWorkout(sessionWorkout)
    }

    suspend fun deleteWorkouts(workouts: List<SessionWorkout>) {
        sessionDao.deleteWorkouts(workouts)
    }

    suspend fun delete(session: Session) {
        sessionDao.delete(session)
    }
}

