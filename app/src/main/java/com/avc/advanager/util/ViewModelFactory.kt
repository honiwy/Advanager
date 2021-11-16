package com.avc.advanager.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avc.advanager.data.source.AdvanagerRepository
import com.avc.advanager.ui.dashboard.DashboardViewModel
import com.avc.advanager.ui.landing.LandingViewModel
import com.avc.advanager.ui.login.DeviceLoginViewModel
import com.avc.advanager.ui.register.DeviceRegisterViewModel
import com.avc.advanager.ui.search.DeviceSearchViewModel

class ViewModelFactory(
    private val advanagerRepository: AdvanagerRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(LandingViewModel::class.java) ->
                    LandingViewModel(advanagerRepository)

                isAssignableFrom(DeviceLoginViewModel::class.java) ->
                    DeviceLoginViewModel(advanagerRepository)

                isAssignableFrom(DeviceRegisterViewModel::class.java) ->
                    DeviceRegisterViewModel(advanagerRepository)

                isAssignableFrom(DeviceSearchViewModel::class.java) ->
                    DeviceSearchViewModel(advanagerRepository)

                isAssignableFrom(DashboardViewModel::class.java) ->
                    DashboardViewModel(advanagerRepository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
