package com.kssidll.workin.domain.repository

import com.kssidll.workin.data.data.*
import kotlinx.coroutines.flow.*

interface ISessionRepository {
    fun getAllDescFlow(): Flow<List<SessionWithSessionWorkouts>>
    suspend fun getById(sessionId: Long): Session?
    suspend fun getWithSessionWorkoutsById(sessionId: Long): SessionWithSessionWorkouts?
    suspend fun getByWorkoutId(workoutId: Long): List<Session>
    fun getAllMergedSessionsWithWorkouts(): Flow<List<SessionWithFullSessionWorkouts>>
    suspend fun getMergedSessionWithWorkoutsById(sessionId: Long): SessionWithFullSessionWorkouts?
    suspend fun insert(session: Session): Long
    suspend fun insertWorkouts(workouts: List<SessionWorkout>): List<Long>
    suspend fun insertSessionLog(log: SessionWorkoutLog): Long
    suspend fun getLastWorkoutLogs(
        workoutId: Long,
        amount: Int
    ): List<SessionWorkoutLog>

    suspend fun getSessionWorkoutsBySessionId(sessionId: Long): List<SessionWorkout>
    suspend fun update(session: Session)
    suspend fun updateSessionWorkout(sessionWorkout: SessionWorkout)
    suspend fun deleteWorkouts(workouts: List<SessionWorkout>)
    suspend fun delete(session: Session)
    suspend fun delete(sessions: List<Session>)
}