package com.avc.advanager

import android.app.Application
import android.content.Context
import com.avc.advanager.data.source.AdvanagerRepository
import com.avc.advanager.util.ServiceLocator

class AdvanagerApplication : Application() {

    val advanagerRepository: AdvanagerRepository
        get() = ServiceLocator.provideTasksRepository()

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}