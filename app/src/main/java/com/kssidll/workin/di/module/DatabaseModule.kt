package com.kssidll.workin.di.module

import android.content.*
import androidx.room.*
import com.kssidll.workin.data.dao.*
import com.kssidll.workin.data.database.*
import com.kssidll.workin.data.repository.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.android.qualifiers.*
import dagger.hilt.components.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            AppDatabase::class.java,
            "workin_database"
        )
            .build()
    }

    @Provides
    fun provideWorkoutDao(appDatabase: AppDatabase): WorkoutDao {
        return appDatabase.getWorkoutDao()
    }

    @Provides
    fun provideWorkoutRepository(workoutDao: WorkoutDao): WorkoutRepository {
        return WorkoutRepository(workoutDao)
    }

    @Provides
    fun provideSessionDao(appDatabase: AppDatabase): SessionDao {
        return appDatabase.getSessionDao()
    }

    @Provides
    fun provideSessionRepository(sessionDao: SessionDao): SessionRepository {
        return SessionRepository(sessionDao)
    }
}