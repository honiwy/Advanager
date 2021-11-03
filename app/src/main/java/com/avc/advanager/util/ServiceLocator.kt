package com.avc.advanager.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.avc.advanager.source.AdvanagerDataSource
import com.avc.advanager.source.AdvanagerRepository
import com.avc.advanager.source.DefaultAdvanagerRepository
import com.avc.advanager.source.local.AdvanagerLocalDataSource
import com.avc.advanager.source.remote.AdvanagerRemoteDataSource

object ServiceLocator {

    @Volatile
    var advanagerRepository: AdvanagerRepository? = null
        @VisibleForTesting set

    fun provideTasksRepository(context: Context): AdvanagerRepository {
        synchronized(this) {
            return advanagerRepository
                ?: advanagerRepository
                ?: createLiTsapRepository(context)
        }
    }

    private fun createLiTsapRepository(context: Context): AdvanagerRepository {
        return DefaultAdvanagerRepository(
            AdvanagerRemoteDataSource,
            createLocalDataSource(context)
        )
    }

    private fun createLocalDataSource(context: Context): AdvanagerDataSource {
        return AdvanagerLocalDataSource(context)
    }
}