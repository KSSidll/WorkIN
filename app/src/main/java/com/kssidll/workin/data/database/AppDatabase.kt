package com.kssidll.workin.data.database

import androidx.room.*
import com.kssidll.workin.data.dao.*
import com.kssidll.workin.data.data.*

@Database(
    entities = [
        Workout::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getWorkoutDao(): WorkoutDao
}