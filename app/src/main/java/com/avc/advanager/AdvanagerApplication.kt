package com.avc.advanager

import android.app.Application
import android.content.Context
import com.avc.advanager.source.AdvanagerRepository
import com.avc.advanager.util.ServiceLocator
import kotlin.properties.Delegates

class AdvanagerApplication : Application() {

    // Depends on the flavor,
    val advanagerRepository: AdvanagerRepository
        get() = ServiceLocator.provideTasksRepository(this)

    companion object {
        var instance: AdvanagerApplication by Delegates.notNull()
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appContext = applicationContext
    }
}