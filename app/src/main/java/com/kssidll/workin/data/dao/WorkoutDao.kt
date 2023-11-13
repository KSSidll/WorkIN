package com.kssidll.workin.data.dao

import androidx.room.*
import com.kssidll.workin.data.data.*
import kotlinx.coroutines.flow.*

@Dao
interface WorkoutDao {
    /**
     * @param workoutId id of the queried workout
     * @return queried workout matching [workoutId], null if none with that id exist
     */
    @Query("SELECT * FROM workout WHERE id = :workoutId")
    suspend fun workoutById(workoutId: Long): Workout?

    /**
     * @return list of all workouts
     */
    @Query("SELECT * FROM workout")
    suspend fun allWorkouts(): List<Workout>

    /**
     * @return list of all workouts as a flow
     */
    @Query("SELECT * FROM workout")
    fun allWorkoutsFlow(): Flow<List<Workout>>

    /**
     * @return list of all workouts sorted by newest first
     */
    @Query("SELECT * FROM workout ORDER BY id DESC")
    suspend fun allWorkoutsByNewestFirst(): List<Workout>

    /**
     * @return list of all workouts sorted by newest first as flow
     */
    @Query("SELECT * FROM workout ORDER BY id DESC")
    fun allWorkoutsByNewestFirstFlow(): Flow<List<Workout>>

    /**
     * @param workout workout to insert
     * @return id of the newly inserted workout
     */
    @Insert
    suspend fun insert(workout: Workout): Long

    /**
     * @param workout workout to update, needs to match some workout by its id
     */
    @Update
    suspend fun update(workout: Workout)

    /**
     * @param workout workout to delete
     */
    @Delete
    suspend fun delete(workout: Workout)
}