package com.mbs.workoutplanner.di

import com.mbs.workoutplanner.repository.UserInfoInterface
import com.mbs.workoutplanner.repository.UserInfoRepository
import com.mbs.workoutplanner.repository.WorkoutInterface
import com.mbs.workoutplanner.repository.WorkoutRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideWorkoutRepository(impl: WorkoutRepository): WorkoutInterface

    @Binds
    @Singleton
    abstract fun provideUserInfoRepository(impl: UserInfoRepository): UserInfoInterface
}