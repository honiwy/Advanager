package com.avc.advanager.extenstion

import com.avc.advanager.AdvanagerApplication
import com.avc.advanager.util.ViewModelFactory

fun getVmFactory(): ViewModelFactory {
    val repository = (AdvanagerApplication.appContext as AdvanagerApplication).advanagerRepository
    return ViewModelFactory(repository)
}
