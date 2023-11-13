package com.kssidll.workin.domain.repository

import com.kssidll.workin.data.data.*
import kotlinx.coroutines.flow.*

interface ISessionRepository {
    /**
     * @param sessionId id of the queried session
     * @return queried session matching [sessionId], null if none with that id exist
     */
    suspend fun sessionById(sessionId: Long): Session?

    /**
     * @param workoutId id of the workout to match
     * @return list of sessions matching [workoutId]
     */
    suspend fun allSessionsByWorkoutId(workoutId: Long): List<Session>

    /**
     * @return list of all sessions
     */
    suspend fun allSessions(): List<Session>

    /**
     * @return list of all sessions as flow
     */
    fun allSessionsFlow(): Flow<List<Session>>

    /**
     * @return list of all sessions sorted by newest first
     */
    suspend fun allSessionsByNewestFirst(): List<Session>

    /**
     * @return list of all sessions sorted by newest first as flow
     */
    fun allSessionsByNewestFirstFlow(): Flow<List<Session>>

    /**
     * @param session session to insert
     * @return id of the newly inserted session
     */
    suspend fun insertSession(session: Session): Long

    /**
     * @param session session to update, needs to match some session by its id
     */
    suspend fun updateSession(session: Session)

    /**
     * @param session session, or list of sessions to delete
     */
    suspend fun deleteSession(session: Session)

    /**
     * @param sessions session, or list of sessions to delete
     */
    suspend fun deleteSession(sessions: List<Session>)

    /**
     * @param sessionId id of the queried session
     * @return queried session matching [sessionId], null if none with that id exist
     */
    suspend fun sessionWithWorkoutsById(sessionId: Long): SessionWithWorkouts?

    /**
     * @return list of all sessions
     */
    suspend fun allSessionsWithWorkouts(): List<SessionWithWorkouts>

    /**
     * @return list of all sessions as flow
     */
    fun allSessionsWithWorkoutsFlow(): Flow<List<SessionWithWorkouts>>

    /**
     * @return list of all sessions sorted by newest first as flow
     */
    suspend fun allSessionsWithWorkoutsNewestFirst(): List<SessionWithWorkouts>

    /**
     * @return list of all sessions sorted by newest first as flow
     */
    fun allSessionsWithWorkoutsNewestFirstFlow(): Flow<List<SessionWithWorkouts>>

    /**
     * @param workoutId workoutId to match
     * @param amount how many items to return
     * @return list of logs, of [amount] length, matching by [workoutId], sorted by newest first
     */
    suspend fun newestLogsByWorkoutId(
        workoutId: Long,
        amount: Int
    ): List<SessionWorkoutLog>

    /**
     * @param workoutId workoutId to match
     * @param amount how many items to return
     * @return list of logs, of [amount] length, matching by [workoutId], sorted by newest first, as flow
     */
    fun newestLogsByWorkoutIdFlow(
        workoutId: Long,
        amount: Int
    ): Flow<List<SessionWorkoutLog>>

    /**
     * @param log log to insert
     * @return id of the newly inserted log
     */
    suspend fun insertSessionWorkoutLog(log: SessionWorkoutLog): Long

    /**
     * @param sessionId sessionId to match
     * @return list of session workouts matching [sessionId]
     */
    suspend fun allSessionWorkoutsBySessionId(sessionId: Long): List<SessionWorkout>

    /**
     * @param sessionWorkout workout, or list of workouts to insert
     * @return id of the newly inserted workout
     */
    suspend fun insertSessionWorkout(sessionWorkout: SessionWorkout): Long

    /**
     * @param sessionWorkouts workout, or list of workouts to insert
     * @return list of ids of the newly inserted workouts
     */
    suspend fun insertSessionWorkout(sessionWorkouts: List<SessionWorkout>): List<Long>

    /**
     * @param sessionWorkout workout to update
     */
    suspend fun updateSessionWorkout(sessionWorkout: SessionWorkout)

    /**
     * @param sessionWorkout workout, or list of workouts to delete
     */
    suspend fun deleteSessionWorkout(sessionWorkout: SessionWorkout)

    /**
     * @param sessionWorkouts workout, or list of workouts to delete
     */
    suspend fun deleteSessionWorkout(sessionWorkouts: List<SessionWorkout>)
}