package com.avc.advanager.device.login

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avc.advanager.AdvanagerApplication
import com.avc.advanager.R
import com.avc.advanager.data.LoginInfo
import com.avc.advanager.data.RegisterInfo
import com.avc.advanager.data.Result
import com.avc.advanager.source.AdvanagerRepository
import com.avc.advanager.source.LoadStatus
import com.avc.advanager.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class DeviceLoginViewModel(private val repository: AdvanagerRepository) : ViewModel() {

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val account= MutableLiveData<String>().apply {
        value = ""
    }

    val password= MutableLiveData<String>().apply {
        value = ""
    }

    fun login() {
        coroutineScope.launch {
            Log.d("DeviceLoginViewModel", "login:  ")
            if (!Util.isWifiEnabled()) {
                Toast.makeText(
                    AdvanagerApplication.appContext,
                    Util.getString(R.string.wifi_warning),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val loginInfo = LoginInfo(
                    account = account.value ?: "admin",
                    password = password.value ?: "P@ssw0rD"
                )
                val result = repository.postUserLogin(loginInfo)
                when (result) {
                    is Result.Success -> {
                        result.data
                        //TODO display success toast
                        Log.d("DeviceLoginViewModel", "login: Success "+result.data.token)
                        navigateToHomePage()
                    }
                    is Result.Fail -> {
                        null
                    }
                    is Result.Error -> {
                        null
                    }
                    else -> {
                        null
                    }
                }
            }
        }
    }

    // Handle leave login
    private val _navigateToHomePage = MutableLiveData<Boolean>()

    val navigateToHomePage: LiveData<Boolean>
        get() = _navigateToHomePage

    private fun navigateToHomePage() {
        _navigateToHomePage.value = true
//        Toast.makeText(
//            AdvanagerApplication.appContext,
//            AdvanagerApplication.instance.getString(R.string.login_success),
//            Toast.LENGTH_SHORT
//        ).show()
    }

    fun onSucceeded() {
        _navigateToHomePage.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}