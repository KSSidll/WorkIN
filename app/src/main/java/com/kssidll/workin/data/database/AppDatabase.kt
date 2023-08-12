package com.kssidll.workin.data.database

import androidx.room.*
import com.kssidll.workin.data.dao.*
import com.kssidll.workin.data.data.*

@Database(
    version = 2,
    entities = [
        Workout::class,
        Session::class,
        SessionWorkout::class,
        SessionWorkoutLog::class,
    ],
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getWorkoutDao(): WorkoutDao
    abstract fun getSessionDao(): SessionDao
}