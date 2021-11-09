package com.avc.advanager.fragment.login

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avc.advanager.AdvanagerApplication
import com.avc.advanager.R
import com.avc.advanager.data.AccountInfo
import com.avc.advanager.data.Result
import com.avc.advanager.fragment.DeviceManager
import com.avc.advanager.source.AdvanagerRepository
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

    val selectedIP: String = DeviceManager.deviceIp?:""

    val account= MutableLiveData<String>().apply {
        value = ""
    }

    val password= MutableLiveData<String>().apply {
        value = ""
    }

    init {
        //help to fill in
        if (!DeviceManager.deviceAccount.isNullOrEmpty() && !DeviceManager.devicePassword.isNullOrEmpty()) {
            account.value = DeviceManager.deviceAccount
            password.value = DeviceManager.devicePassword
//            login()
        }
    }

    fun login() {
        coroutineScope.launch {
            if (!Util.isWifiEnabled()) {
                Toast.makeText(
                    AdvanagerApplication.appContext,
                    Util.getString(R.string.wifi_warning),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val loginInfo = AccountInfo(
                    account = account.value ?: "admin",
                    password = password.value ?: "P@ssw0rD"
                )
                val result = repository.postUserLogin(loginInfo)
                when (result) {
                    is Result.Success -> {
                        result.data
                        DeviceManager.deviceAccount = loginInfo.account
                        DeviceManager.devicePassword = loginInfo.password
                        DeviceManager.deviceToken = result.data.token
                        navigateToHomePage()
                    }
                    is Result.Fail -> {
                        null
                    }
                    is Result.Error -> {
                        null
                        Toast.makeText(
                            AdvanagerApplication.appContext,
                            "Wrong Password!",
                            Toast.LENGTH_SHORT
                        ).show()
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
        Toast.makeText(
            AdvanagerApplication.appContext,
            "Login Success!",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun onSucceeded() {
        _navigateToHomePage.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}