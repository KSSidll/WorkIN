package com.kssidll.workin.data.repository

import com.kssidll.workin.data.dao.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.repository.*
import kotlinx.coroutines.flow.*

class WorkoutRepository(private val workoutDao: WorkoutDao): IWorkoutRepository {
    override fun getAllDescFlow(): Flow<List<Workout>> {
        return workoutDao.getAllDescFlow()
    }

    override suspend fun getById(workoutId: Long): Workout {
        return workoutDao.getById(workoutId)
    }

    override suspend fun insert(workout: Workout): Long {
        return workoutDao.insert(workout)
    }

    override suspend fun update(workout: Workout) {
        workoutDao.update(workout)
    }

    override suspend fun delete(workout: Workout) {
        workoutDao.delete(workout)
    }
}