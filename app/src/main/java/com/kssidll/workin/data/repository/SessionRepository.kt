package com.kssidll.workin.data.repository

import com.kssidll.workin.data.dao.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.repository.*
import kotlinx.coroutines.flow.*

class SessionRepository(private val dao: SessionDao): ISessionRepository {
    override suspend fun sessionById(sessionId: Long): Session? {
        return dao.sessionById(sessionId)
    }

    override suspend fun allSessionsByWorkoutId(workoutId: Long): List<Session> {
        return dao.allSessionsByWorkoutId(workoutId)
    }

    override suspend fun allSessions(): List<Session> {
        return dao.allSessions()
    }

    override fun allSessionsFlow(): Flow<List<Session>> {
        return dao.allSessionsFlow()
    }

    override suspend fun allSessionsByNewestFirst(): List<Session> {
        return dao.allSessionsByNewestFirst()
    }

    override fun allSessionsByNewestFirstFlow(): Flow<List<Session>> {
        return dao.allSessionsByNewestFirstFlow()
    }

    override suspend fun allSessionsWithWorkoutsNewestFirst(): List<SessionWithWorkouts> {
        return dao.allSessionsWithWorkoutsNewestFirst()
    }

    override fun allSessionsWithWorkoutsNewestFirstFlow(): Flow<List<SessionWithWorkouts>> {
        return dao.allSessionsWithWorkoutsNewestFirstFlow()
    }

    override suspend fun insertSession(session: Session): Long {
        return dao.insertSession(session)
    }

    override suspend fun insertSessionWorkoutLog(log: SessionWorkoutLog): Long {
        return dao.insertSessionWorkoutLog(log)
    }

    override suspend fun insertSessionWorkout(sessionWorkout: SessionWorkout): Long {
        return dao.insertSessionWorkout(sessionWorkout)
    }

    override suspend fun insertSessionWorkout(sessionWorkouts: List<SessionWorkout>): List<Long> {
        return dao.insertSessionWorkout(sessionWorkouts)
    }

    override suspend fun updateSession(session: Session) {
        return dao.updateSession(session)
    }

    override suspend fun updateSessionWorkout(sessionWorkout: SessionWorkout) {
        return dao.updateSessionWorkout(sessionWorkout)
    }

    override suspend fun deleteSession(session: Session) {
        return dao.deleteSession(session)
    }

    override suspend fun deleteSession(sessions: List<Session>) {
        return dao.deleteSession(sessions)
    }

    override suspend fun deleteSessionWorkout(sessionWorkout: SessionWorkout) {
        return dao.deleteSessionWorkout(sessionWorkout)
    }

    override suspend fun deleteSessionWorkout(sessionWorkouts: List<SessionWorkout>) {
        return dao.deleteSessionWorkout(sessionWorkouts)
    }

    override suspend fun sessionWithWorkoutsById(sessionId: Long): SessionWithWorkouts? {
        return dao.sessionWithWorkoutsById(sessionId)
    }

    override suspend fun allSessionsWithWorkouts(): List<SessionWithWorkouts> {
        return dao.allSessionsWithWorkouts()
    }

    override fun allSessionsWithWorkoutsFlow(): Flow<List<SessionWithWorkouts>> {
        return dao.allSessionsWithWorkoutsFlow()
    }

    override suspend fun newestLogsByWorkoutId(
        workoutId: Long,
        amount: Int
    ): List<SessionWorkoutLog> {
        return dao.newestLogsByWorkoutId(workoutId, amount)
    }

    override fun newestLogsByWorkoutIdFlow(
        workoutId: Long,
        amount: Int
    ): Flow<List<SessionWorkoutLog>> {
        return dao.newestLogsByWorkoutIdFlow(workoutId, amount)
    }

    override suspend fun allSessionWorkoutsBySessionId(sessionId: Long): List<SessionWorkout> {
        return dao.allSessionWorkoutsBySessionId(sessionId)
    }

}

