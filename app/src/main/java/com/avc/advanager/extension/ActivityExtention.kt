package com.avc.advanager.extension

import android.app.Activity
import android.view.Gravity
import android.widget.Toast
import com.avc.advanager.AdvanagerApplication
import com.avc.advanager.factory.ViewModelFactory

fun Activity.getVmFactory(): ViewModelFactory {
    val repository = (applicationContext as AdvanagerApplication).advanagerRepository
    return ViewModelFactory(repository)
}

fun Activity?.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}