package com.kssidll.workin.data.repository

import com.kssidll.workin.data.dao.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.repository.*
import kotlinx.coroutines.flow.*

class SessionRepository(private val sessionDao: SessionDao): ISessionRepository {
    override fun getAllMergedSessionsWithWorkouts(): Flow<List<SessionWithFullSessionWorkouts>> {
        return sessionDao.getAllDescFlow()
            .map { list ->
                list.map {
                    it.merge(sessionDao.getFullSessionWorkouts(it.session.id))
                }
            }
    }

    override suspend fun getMergedSessionWithWorkoutsById(sessionId: Long): SessionWithFullSessionWorkouts {
        return sessionDao.getById(sessionId)
            .merge(
                sessionDao.getFullSessionWorkouts(sessionId)
            )
    }

    override suspend fun insert(session: Session): Long {
        return sessionDao.insert(session)
    }

    override suspend fun insertWorkouts(workouts: List<SessionWorkout>): List<Long> {
        return sessionDao.insertWorkouts(workouts)
    }

    override suspend fun insertSessionLog(log: SessionWorkoutLog): Long {
        return sessionDao.insertSessionWorkoutLog(log)
    }

    override suspend fun getLastWorkoutLogs(
        workoutId: Long,
        amount: Int
    ): List<SessionWorkoutLog> {
        return sessionDao.getLastWorkoutLogs(workoutId, amount)
    }

    override suspend fun update(session: Session) {
        sessionDao.update(session)
    }

    override suspend fun updateSessionWorkout(sessionWorkout: SessionWorkout) {
        sessionDao.updateSessionWorkout(sessionWorkout)
    }

    override suspend fun deleteWorkouts(workouts: List<SessionWorkout>) {
        sessionDao.deleteWorkouts(workouts)
    }

    override suspend fun delete(session: Session) {
        sessionDao.delete(session)
    }
}

