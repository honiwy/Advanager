package com.avc.advanager.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avc.advanager.MainViewModel
import com.avc.advanager.device.search.DeviceSearchViewModel
import com.avc.advanager.device.login.DeviceLoginViewModel
import com.avc.advanager.device.register.DeviceRegisterViewModel
import com.avc.advanager.source.AdvanagerRepository

class ViewModelFactory(
    private val advanagerRepository: AdvanagerRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(DeviceLoginViewModel::class.java) ->
                    DeviceLoginViewModel(advanagerRepository)

                isAssignableFrom(DeviceRegisterViewModel::class.java) ->
                    DeviceRegisterViewModel(advanagerRepository)

                isAssignableFrom(DeviceSearchViewModel::class.java) ->
                    DeviceSearchViewModel(advanagerRepository)

                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel()

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}