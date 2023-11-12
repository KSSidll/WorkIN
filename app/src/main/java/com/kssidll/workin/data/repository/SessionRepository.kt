package com.kssidll.workin.data.repository

import com.kssidll.workin.data.dao.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.repository.*
import kotlinx.coroutines.flow.*

class SessionRepository(private val dao: SessionDao): ISessionRepository {
    override fun getAllDescFlow(): Flow<List<SessionWithSessionWorkouts>> {
        return dao.getAllDescFlow()
    }

    override suspend fun getById(sessionId: Long): Session? {
        return dao.getById(sessionId)
    }

    override suspend fun getWithSessionWorkoutsById(sessionId: Long): SessionWithSessionWorkouts? {
        return dao.getWithSessionWorkoutsById(sessionId)
    }

    override suspend fun getByWorkoutId(workoutId: Long): List<Session> {
        return dao.getByWorkoutId(workoutId)
    }

    override fun getAllMergedSessionsWithWorkouts(): Flow<List<SessionWithFullSessionWorkouts>> {
        return dao.getAllDescFlow()
            .map { list ->
                list.map {
                    it.merge(dao.getFullSessionWorkouts(it.session.id))
                }
            }
    }

    override suspend fun getMergedSessionWithWorkoutsById(sessionId: Long): SessionWithFullSessionWorkouts? {
        val session = dao.getWithSessionWorkoutsById(sessionId) ?: return null
        return session.merge(
            dao.getFullSessionWorkouts(sessionId)
        )
    }

    override suspend fun insert(session: Session): Long {
        return dao.insert(session)
    }

    override suspend fun insertWorkouts(workouts: List<SessionWorkout>): List<Long> {
        return dao.insertWorkouts(workouts)
    }

    override suspend fun insertSessionLog(log: SessionWorkoutLog): Long {
        return dao.insertSessionWorkoutLog(log)
    }

    override suspend fun getLastWorkoutLogs(
        workoutId: Long,
        amount: Int
    ): List<SessionWorkoutLog> {
        return dao.getLastWorkoutLogs(workoutId, amount)
    }

    override suspend fun getSessionWorkoutsBySessionId(sessionId: Long): List<SessionWorkout> {
        return dao.getSessionWorkoutsBySessionId(sessionId)
    }

    override suspend fun update(session: Session) {
        dao.update(session)
    }

    override suspend fun updateSessionWorkout(sessionWorkout: SessionWorkout) {
        dao.updateSessionWorkout(sessionWorkout)
    }

    override suspend fun deleteWorkouts(workouts: List<SessionWorkout>) {
        dao.deleteWorkouts(workouts)
    }

    override suspend fun delete(session: Session) {
        dao.delete(session)
    }

    override suspend fun delete(sessions: List<Session>) {
        dao.delete(sessions)
    }
}

