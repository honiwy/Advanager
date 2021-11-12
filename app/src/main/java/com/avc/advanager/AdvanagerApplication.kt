package com.avc.advanager

import android.app.Application
import android.content.Context
import com.avc.advanager.di.AppComponent
import com.avc.advanager.di.DaggerAppComponent
import timber.log.Timber
import kotlin.properties.Delegates

class AdvanagerApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        appContext = applicationContext
    }
}