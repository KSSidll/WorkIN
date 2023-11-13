package com.kssidll.workin.data.dao

import androidx.room.*
import com.kssidll.workin.data.data.*
import kotlinx.coroutines.flow.*

@Dao
interface SessionDao {
    /**
     * @param sessionId id of the queried session
     * @return queried session matching [sessionId], null if none with that id exist
     */
    @Query("SELECT * FROM session WHERE id = :sessionId")
    suspend fun sessionById(sessionId: Long): Session?

    /**
     * @param workoutId id of the workout to match
     * @return list of sessions matching [workoutId]
     */
    @Query("SELECT session.* FROM session INNER JOIN sessionworkout ON session.id = sessionworkout.sessionId WHERE workoutId = :workoutId")
    suspend fun allSessionsByWorkoutId(workoutId: Long): List<Session>

    /**
     * @return list of all sessions
     */
    @Query("SELECT * FROM session")
    suspend fun allSessions(): List<Session>

    /**
     * @return list of all sessions as flow
     */
    @Query("SELECT * FROM session")
    fun allSessionsFlow(): Flow<List<Session>>

    /**
     * @return list of all sessions sorted by newest first
     */
    @Query("SELECT * FROM session ORDER BY id DESC")
    suspend fun allSessionsByNewestFirst(): List<Session>

    /**
     * @return list of all sessions sorted by newest first as flow
     */
    @Query("SELECT * FROM session ORDER BY id DESC")
    fun allSessionsByNewestFirstFlow(): Flow<List<Session>>

    /**
     * @param session session to insert
     * @return id of the newly inserted session
     */
    @Insert
    suspend fun insertSession(session: Session): Long

    /**
     * @param session session to update, needs to match some session by its id
     */
    @Update
    suspend fun updateSession(session: Session)

    /**
     * @param session session, or list of sessions to delete
     */
    @Delete
    suspend fun deleteSession(session: Session)

    /**
     * @param sessions session, or list of sessions to delete
     */
    @Delete
    suspend fun deleteSession(sessions: List<Session>)

    @Query("SELECT * FROM workout WHERE id = :workoutId")
    suspend fun workoutById(workoutId: Long): Workout?

    /**
     * @param sessionId id of the queried session
     * @return queried session matching [sessionId], null if none with that id exist
     */
    @Transaction
    suspend fun sessionWithWorkoutsById(sessionId: Long): SessionWithWorkouts? {
        val session = sessionById(sessionId) ?: return null

        return SessionWithWorkouts(
            session = session,
            workouts = allSessionWorkoutsBySessionId(sessionId).map {
                FullSessionWorkout(
                    id = it.id,
                    sessionId = it.sessionId,
                    workout = workoutById(it.workoutId) ?: return null,
                    repetitionCount = it.repetitionCount,
                    repetitionType = it.repetitionType,
                    weight = it.weight,
                    weightType = it.weightType,
                    order = it.order,
                    restTime = it.restTime,
                )
            }
                .sortedBy { it.order }
        )
    }

    /**
     * @return list of all sessions
     */
    @Transaction
    suspend fun allSessionsWithWorkouts(): List<SessionWithWorkouts> {
        return allSessions().map {
            sessionWithWorkoutsById(it.id)!!
        }
    }

    /**
     * @return list of all sessions as flow
     */
    fun allSessionsWithWorkoutsFlow(): Flow<List<SessionWithWorkouts>> {
        return allSessionsFlow().map { sessions ->
            sessions.map {
                sessionWithWorkoutsById(it.id)!!
            }
        }
    }

    /**
     * @return list of all sessions sorted by newest first as flow
     */
    @Transaction
    suspend fun allSessionsWithWorkoutsNewestFirst(): List<SessionWithWorkouts> {
        return allSessionsByNewestFirst().map {
            sessionWithWorkoutsById(it.id)!!
        }
    }

    /**
     * @return list of all sessions sorted by newest first as flow
     */
    fun allSessionsWithWorkoutsNewestFirstFlow(): Flow<List<SessionWithWorkouts>> {
        return allSessionsByNewestFirstFlow().map { sessions ->
            sessions.map {
                sessionWithWorkoutsById(it.id)!!
            }
        }
    }

    /**
     * @param workoutId workoutId to match
     * @param amount how many items to return
     * @return list of logs, of [amount] length, matching by [workoutId], sorted by newest first
     */
    @Query("SELECT * FROM sessionworkoutlog WHERE workoutId = :workoutId ORDER BY id DESC LIMIT :amount")
    suspend fun newestLogsByWorkoutId(
        workoutId: Long,
        amount: Int
    ): List<SessionWorkoutLog>

    /**
     * @param workoutId workoutId to match
     * @param amount how many items to return
     * @return list of logs, of [amount] length, matching by [workoutId], sorted by newest first, as flow
     */
    @Query("SELECT * FROM sessionworkoutlog WHERE workoutId = :workoutId ORDER BY id DESC LIMIT :amount")
    fun newestLogsByWorkoutIdFlow(
        workoutId: Long,
        amount: Int
    ): Flow<List<SessionWorkoutLog>>

    /**
     * @param log log to insert
     * @return id of the newly inserted log
     */
    @Insert
    suspend fun insertSessionWorkoutLog(log: SessionWorkoutLog): Long

    /**
     * @param sessionId sessionId to match
     * @return list of session workouts matching [sessionId]
     */
    @Query("SELECT * FROM sessionworkout WHERE sessionId = :sessionId")
    suspend fun allSessionWorkoutsBySessionId(sessionId: Long): List<SessionWorkout>

    /**
     * @param sessionWorkout workout, or list of workouts to insert
     * @return id of the newly inserted workout
     */
    @Insert
    suspend fun insertSessionWorkout(sessionWorkout: SessionWorkout): Long

    /**
     * @param sessionWorkouts workout, or list of workouts to insert
     * @return list of ids of the newly inserted workouts
     */
    @Insert
    suspend fun insertSessionWorkout(sessionWorkouts: List<SessionWorkout>): List<Long>

    /**
     * @param sessionWorkout workout to update
     */
    @Update
    suspend fun updateSessionWorkout(sessionWorkout: SessionWorkout)

    /**
     * @param sessionWorkout workout, or list of workouts to delete
     */
    @Delete
    suspend fun deleteSessionWorkout(sessionWorkout: SessionWorkout)

    /**
     * @param sessionWorkouts workout, or list of workouts to delete
     */
    @Delete
    suspend fun deleteSessionWorkout(sessionWorkouts: List<SessionWorkout>)
}
