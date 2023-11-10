package com.kssidll.workin.data.dao

import androidx.room.*
import com.kssidll.workin.data.data.*
import kotlinx.coroutines.flow.*

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workout ORDER BY id DESC")
    fun getAllDescFlow(): Flow<List<Workout>>

    @Query("SELECT * FROM workout WHERE id = :workoutId")
    suspend fun getById(workoutId: Long): Workout?

    @Insert
    suspend fun insert(workout: Workout): Long

    @Update
    suspend fun update(workout: Workout)

    @Delete
    suspend fun delete(workout: Workout)
}