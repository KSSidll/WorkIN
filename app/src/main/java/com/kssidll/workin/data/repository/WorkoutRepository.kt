package com.kssidll.workin.data.repository

import com.kssidll.workin.data.dao.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.domain.repository.*
import kotlinx.coroutines.flow.*

class WorkoutRepository(private val dao: WorkoutDao): IWorkoutRepository {
    override fun getAll(): List<Workout> {
        return dao.getAll()
    }

    override fun getAllDescFlow(): Flow<List<Workout>> {
        return dao.getAllDescFlow()
    }

    override suspend fun getById(workoutId: Long): Workout? {
        return dao.getById(workoutId)
    }

    override suspend fun insert(workout: Workout): Long {
        return dao.insert(workout)
    }

    override suspend fun update(workout: Workout) {
        dao.update(workout)
    }

    override suspend fun delete(workout: Workout) {
        dao.delete(workout)
    }
}