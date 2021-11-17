package com.avc.advanager.util

import androidx.annotation.VisibleForTesting
import com.avc.advanager.data.source.AdvanagerRepository
import com.avc.advanager.data.source.DefaultAdvanagerRepository
import com.avc.advanager.data.source.local.DefaultLocalDataSource
import com.avc.advanager.data.source.local.LocalDataSource
import com.avc.advanager.data.source.remote.DefaultRemoteDataSource

object ServiceLocator {

    @Volatile
    var advanagerRepository: AdvanagerRepository? = null
        @VisibleForTesting set

    fun provideTasksRepository(): AdvanagerRepository {
        synchronized(this) {
            return advanagerRepository
                ?: advanagerRepository
                ?: createAdvanagerRepository()
        }
    }

    private fun createAdvanagerRepository(): AdvanagerRepository {
        return DefaultAdvanagerRepository(
            DefaultRemoteDataSource,
            createLocalDataSource()
        )
    }

    private fun createLocalDataSource(): LocalDataSource {
        return DefaultLocalDataSource()
    }
}