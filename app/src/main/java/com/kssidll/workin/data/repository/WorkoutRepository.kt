package com.kssidll.workin.data.repository

import com.kssidll.workin.data.dao.*
import com.kssidll.workin.data.data.*
import kotlinx.coroutines.flow.*

class WorkoutRepository(private val workoutDao: WorkoutDao) {
    fun getAllDescFlow(): Flow<List<Workout>> {
        return workoutDao.getAllDescFlow()
    }

    suspend fun getById(workoutId: Long): Workout {
        return workoutDao.getById(workoutId)
    }

    suspend fun insert(workout: Workout): Long {
        return workoutDao.insert(workout)
    }

    suspend fun update(workout: Workout) {
        workoutDao.update(workout)
    }

    suspend fun delete(workout: Workout) {
        workoutDao.delete(workout)
    }
}