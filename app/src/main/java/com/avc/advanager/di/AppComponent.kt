package com.avc.advanager.di

import android.content.Context
import com.avc.advanager.fragment.landing.LandingFragment
import com.avc.advanager.fragment.login.DeviceLoginFragment
import com.avc.advanager.fragment.register.DeviceRegisterFragment
import com.avc.advanager.fragment.search.DeviceSearchFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, SourceModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: LandingFragment)
    fun inject(fragment: DeviceSearchFragment)
    fun inject(fragment: DeviceRegisterFragment)
    fun inject(fragment: DeviceLoginFragment)

}