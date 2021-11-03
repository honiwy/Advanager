package com.avc.advanager.extension

import com.avc.advanager.AdvanagerApplication
import com.avc.advanager.factory.ViewModelFactory


fun getVmFactory(): ViewModelFactory {
    val repository = (AdvanagerApplication.appContext as AdvanagerApplication).advanagerRepository
    return ViewModelFactory(repository)
}
