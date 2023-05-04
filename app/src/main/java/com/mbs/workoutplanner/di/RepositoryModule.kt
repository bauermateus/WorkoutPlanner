
package com.mbs.workoutplanner.di

import com.mbs.workoutplanner.repository.UserDataInterface
import com.mbs.workoutplanner.repository.UserDataRepository
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
    abstract fun provideUserDataRepository(impl: UserDataRepository): UserDataInterface

}