package com.kssidll.workin.data.dao

import androidx.room.*
import com.kssidll.workin.data.data.*
import kotlinx.coroutines.flow.*

@Dao
interface SessionDao {

    @Transaction
    @Query("SELECT * FROM session ORDER BY id DESC")
    fun getAllDescFlow(): Flow<List<SessionWithSessionWorkouts>>

    @Transaction
    @Query("SELECT * FROM session WHERE id = :sessionId")
    suspend fun getById(sessionId: Long): SessionWithSessionWorkouts

    @Query("SELECT session.* FROM session INNER JOIN sessionworkout ON session.id = sessionId WHERE workoutId = :workoutId")
    suspend fun getByWorkoutId(workoutId: Long): List<Session>

    @Transaction
    @Query("SELECT * FROM sessionworkout WHERE sessionId = :sessionId")
    suspend fun getFullSessionWorkouts(sessionId: Long): List<FullSessionWorkout>

    @Query("SELECT * FROM sessionworkoutlog WHERE workoutId = :workoutId ORDER BY id DESC LIMIT :amount")
    suspend fun getLastWorkoutLogs(
        workoutId: Long,
        amount: Int
    ): List<SessionWorkoutLog>

    @Insert
    suspend fun insert(session: Session): Long

    @Insert
    suspend fun insertWorkouts(workouts: List<SessionWorkout>): List<Long>

    @Insert
    suspend fun insertSessionWorkoutLog(log: SessionWorkoutLog): Long

    @Update
    suspend fun update(session: Session)

    @Update
    suspend fun updateSessionWorkout(sessionWorkout: SessionWorkout)

    @Delete
    suspend fun delete(session: Session)

    @Delete
    suspend fun delete(sessions: List<Session>)

    @Delete
    suspend fun deleteWorkouts(workouts: List<SessionWorkout>)
}
