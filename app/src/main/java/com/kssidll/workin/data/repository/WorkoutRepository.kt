package com.kssidll.workin.data.repository

import com.kssidll.workin.data.dao.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.repository.*
import kotlinx.coroutines.flow.*

class WorkoutRepository(private val dao: WorkoutDao): IWorkoutRepository {
    override suspend fun workoutById(workoutId: Long): Workout? {
        return dao.workoutById(workoutId)
    }

    override suspend fun allWorkouts(): List<Workout> {
        return dao.allWorkouts()
    }

    override fun allWorkoutsFlow(): Flow<List<Workout>> {
        return dao.allWorkoutsFlow()
    }

    override suspend fun allWorkoutsByNewestFirst(): List<Workout> {
        return dao.allWorkoutsByNewestFirst()
    }

    override fun allWorkoutsByNewestFirstFlow(): Flow<List<Workout>> {
        return dao.allWorkoutsByNewestFirstFlow()
    }

    override suspend fun insertWorkout(workout: Workout): Long {
        return dao.insert(workout)
    }

    override suspend fun updateWorkout(workout: Workout) {
        return dao.update(workout)
    }

    override suspend fun deleteWorkout(workout: Workout) {
        return dao.delete(workout)
    }
}