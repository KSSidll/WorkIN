package com.kssidll.workin.domain.repository

import com.kssidll.workin.data.data.*
import kotlinx.coroutines.flow.*

interface IWorkoutRepository {
    /**
     * @param workoutId id of the queried workout
     * @return queried workout matching [workoutId], null if none with that id exist
     */
    suspend fun workoutById(workoutId: Long): Workout?

    /**
     * @return list of all workouts
     */
    suspend fun allWorkouts(): List<Workout>

    /**
     * @return list of all workouts as a flow
     */
    fun allWorkoutsFlow(): Flow<List<Workout>>

    /**
     * @return list of all workouts sorted by newest first
     */
    suspend fun allWorkoutsByNewestFirst(): List<Workout>

    /**
     * @return list of all workouts sorted by newest first as flow
     */
    fun allWorkoutsByNewestFirstFlow(): Flow<List<Workout>>

    /**
     * @param workout workout to insert
     * @return id of the newly inserted workout
     */
    suspend fun insertWorkout(workout: Workout): Long

    /**
     * @param workout workout to update, needs to match some workout by its id
     */
    suspend fun updateWorkout(workout: Workout)

    /**
     * @param workout workout to delete
     */
    suspend fun deleteWorkout(workout: Workout)
}