package com.mbs.workoutplanner.di

import android.app.Application
import com.mbs.workoutplanner.dataBase.AppDataBase
import com.mbs.workoutplanner.dataBase.UserDao
import com.mbs.workoutplanner.dataBase.UserInfoDataBase
import com.mbs.workoutplanner.dataBase.WorkoutDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(application: Application): AppDataBase {
        return AppDataBase.getInstance(application)
    }
    @Provides
    @Singleton
    fun provideUserInfoDatabase(application: Application): UserInfoDataBase {
        return UserInfoDataBase.getInstance(application)
    }
    @Provides
    @Singleton
    fun provideHabitDao(dataBase: AppDataBase): WorkoutDao {
        return dataBase.workoutDao()
    }
    @Provides
    @Singleton
    fun provideUserDao(dataBase: UserInfoDataBase): UserDao {
        return dataBase.userInfoDao()
    }
}