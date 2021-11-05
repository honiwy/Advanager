package com.avc.advanager.extension

import com.avc.advanager.AdvanagerApplication
import com.avc.advanager.factory.IPViewModelFactory
import com.avc.advanager.factory.ViewModelFactory


fun getVmFactory(): ViewModelFactory {
    val repository = (AdvanagerApplication.appContext as AdvanagerApplication).advanagerRepository
    return ViewModelFactory(repository)
}

fun getVmFactory(selectedIP:String): IPViewModelFactory {
    val repository = (AdvanagerApplication.appContext as AdvanagerApplication).advanagerRepository
    return IPViewModelFactory(selectedIP, repository)
}
