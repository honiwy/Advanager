package com.avc.advanager.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.avc.advanager.data.source.remote.RemoteDataSource
import com.avc.advanager.data.source.AdvanagerRepository
import com.avc.advanager.data.source.DefaultAdvanagerRepository
import com.avc.advanager.data.source.local.DefaultLocalDataSource
import com.avc.advanager.data.source.remote.DefaultRemoteDataSource

object ServiceLocator {

//    @Volatile
//    var advanagerRepository: AdvanagerRepository? = null
//        @VisibleForTesting set
//
//    fun provideTasksRepository(context: Context): AdvanagerRepository {
//        synchronized(this) {
//            return advanagerRepository
//                ?: advanagerRepository
//                ?: createLiTsapRepository(context)
//        }
//    }
//
//    private fun createLiTsapRepository(context: Context): AdvanagerRepository {
//        return DefaultAdvanagerRepository(
//            DefaultRemoteDataSource,
//            createLocalDataSource(context)
//        )
//    }
//
//    private fun createLocalDataSource(context: Context): RemoteDataSource {
//        return DefaultLocalDataSource(context)
//    }
}