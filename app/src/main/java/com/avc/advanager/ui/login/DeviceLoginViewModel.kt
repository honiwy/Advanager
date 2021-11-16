package com.avc.advanager.ui.login

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avc.advanager.AdvanagerApplication
import com.avc.advanager.data.AccountInfo
import com.avc.advanager.data.source.AdvanagerRepository
import com.avc.advanager.data.source.LoadStatus
import com.avc.advanager.ui.DeviceManager
import kotlinx.coroutines.launch


class DeviceLoginViewModel(private val repository: AdvanagerRepository) : ViewModel() {

    val selectedIP: String = DeviceManager.deviceIp ?: ""

    val account = MutableLiveData<String>().apply {
        value = ""
    }

    val password = MutableLiveData<String>().apply {
        value = ""
    }

    private val _loadingStatus = MutableLiveData<LoadStatus>()
    val loadingStatus: LiveData<LoadStatus> = _loadingStatus

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        if (!DeviceManager.deviceAccount.isNullOrEmpty() && !DeviceManager.devicePassword.isNullOrEmpty()) {
            account.value = DeviceManager.deviceAccount
            password.value = DeviceManager.devicePassword
        }
    }

    fun login() {
        _loadingStatus.value = LoadStatus.LOADING
        viewModelScope.launch {
            val loginInfo = AccountInfo(
                account = account.value ?: "admin",
                password = password.value ?: "P@ssw0rD"
            )
            val result = repository.postUserLogin(loginInfo)
            when {
                result.isSuccessful && result.body() != null -> {
                    _loadingStatus.value = LoadStatus.DONE
                    _error.value = null
                    DeviceManager.deviceAccount = loginInfo.account
                    DeviceManager.devicePassword = loginInfo.password
                    DeviceManager.deviceToken = result.body()?.token
                    navigateToDashboard()
                }
                else -> {
                    _loadingStatus.value = LoadStatus.ERROR
                    _error.value = result.errorBody().toString()
                }
            }
        }
    }

    // Handle leave login
    private val _navigateToDashboard = MutableLiveData<Boolean>()
    val navigateToDashboard: LiveData<Boolean> = _navigateToDashboard

    private fun navigateToDashboard() {
        _navigateToDashboard.value = true
        Toast.makeText(
            AdvanagerApplication.appContext,
            "Login Success!",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun onSucceeded() {
        _navigateToDashboard.value = null
    }

}