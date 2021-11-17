package com.avc.advanager.ui.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avc.advanager.data.source.AdvanagerRepository
import com.avc.advanager.ui.DeviceManager
import com.avc.advanager.util.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class LandingViewModel @Inject constructor(private val repository: AdvanagerRepository) :
    ViewModel() {

    private val _deviceStatus = MutableLiveData<Int>()
    val deviceStatus: LiveData<Int> = _deviceStatus

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        viewModelScope.launch {
            delay(2000)
            checkUserLoginStatus()
        }
    }

    private fun checkUserLoginStatus() {
        val lastIP = DeviceManager.deviceIp
        if (lastIP.isNullOrEmpty()) {
            _deviceStatus.value = Constants.NAV_SEARCH
        } else {
            checkDeviceStatus(lastIP)
        }
    }

    private fun checkDeviceStatus(IP: String) {
        viewModelScope.launch {
            val result = repository.getDeviceInitialStatus(IP)
            when {
                result.isSuccessful && result.body() != null -> {
                    result.body()?.let {
                        _deviceStatus.value = it.initial_status
                    }
                }
                else -> {
                    _deviceStatus.value = Constants.NAV_SEARCH
                }
            }

        }
    }
}
