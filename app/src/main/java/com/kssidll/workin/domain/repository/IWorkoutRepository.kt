package com.kssidll.workin.domain.repository

import com.kssidll.workin.data.data.*
import kotlinx.coroutines.flow.*

interface IWorkoutRepository {
    fun getAll(): List<Workout>
    fun getAllDescFlow(): Flow<List<Workout>>
    suspend fun getById(workoutId: Long): Workout?
    suspend fun insert(workout: Workout): Long
    suspend fun update(workout: Workout)
    suspend fun delete(workout: Workout)
}