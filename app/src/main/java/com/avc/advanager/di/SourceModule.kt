package com.avc.advanager.di

import com.avc.advanager.data.source.AdvanagerRepository
import com.avc.advanager.data.source.DefaultAdvanagerRepository
import com.avc.advanager.data.source.local.DefaultLocalDataSource
import com.avc.advanager.data.source.local.LocalDataSource
import com.avc.advanager.data.source.remote.DefaultRemoteDataSource
import com.avc.advanager.data.source.remote.RemoteDataSource
import dagger.Binds
import dagger.Module

@Module
abstract class SourceModule {

    @Binds
    abstract fun provideLocalDataSource(localDataSource: DefaultLocalDataSource): LocalDataSource

    @Binds
    abstract fun provideRemoteDataSource(remoteDataSource: DefaultRemoteDataSource): RemoteDataSource

    @Binds
    abstract fun provideNewsRepository(pupilRepository: DefaultAdvanagerRepository): AdvanagerRepository

}