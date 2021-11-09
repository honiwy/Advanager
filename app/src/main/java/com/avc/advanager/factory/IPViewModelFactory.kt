package com.avc.advanager.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avc.advanager.source.AdvanagerRepository

class IPViewModelFactory(
    private val selectedIP: String,
    private val advanagerRepository: AdvanagerRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
//                isAssignableFrom(DeviceRegisterViewModel::class.java) ->
//                    DeviceRegisterViewModel(selectedIP, advanagerRepository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}