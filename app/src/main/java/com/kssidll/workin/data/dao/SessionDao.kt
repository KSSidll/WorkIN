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

    @Transaction
    @Query("SELECT * FROM sessionworkout WHERE sessionId = :sessionId")
    suspend fun getFullSessionWorkouts(sessionId: Long): List<FullSessionWorkout>

    @Insert
    suspend fun insert(session: Session): Long

    @Insert
    suspend fun insertWorkouts(workouts: List<SessionWorkout>): List<Long>

    @Update
    suspend fun update(session: Session)

    @Delete
    suspend fun delete(session: Session)
}
