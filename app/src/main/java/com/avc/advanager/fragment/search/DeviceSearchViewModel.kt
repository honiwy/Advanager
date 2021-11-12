package com.avc.advanager.fragment.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avc.advanager.data.Device
import com.avc.advanager.data.Result
import com.avc.advanager.data.source.AdvanagerRepository
import com.avc.advanager.fragment.DeviceManager
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class DeviceSearchViewModel @Inject constructor(private val repository: AdvanagerRepository) :
    ViewModel() {

    private val _ipList = MutableLiveData<List<Device>>()
    val ipList: LiveData<List<Device>> = _ipList

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _deviceStatus = MutableLiveData<Number>()
    val deviceStatus: LiveData<Number> = _deviceStatus

    var typedIP = MutableLiveData<String>().apply {
        value = ""
    }

    private fun loadIpOfDevices(askFromWSDiscovery: Boolean) {
        _dataLoading.value = true
        viewModelScope.launch {
            val result = repository.getDevices(askFromWSDiscovery)
            _ipList.value = when (result) {
                is Result.Success -> {
                    result.data
                }
                else -> {
                    null
                }
            }
        }
        _dataLoading.value = false
    }

    init {
        loadIpOfDevices(true)
    }

    fun refresh() {
        if (_dataLoading.value == false) {
            loadIpOfDevices(true)
        }
    }

    fun checkDeviceStatus(IP: String) {
        DeviceManager.deviceIp = IP
        viewModelScope.launch {
            Timber.d("checkDeviceStatus $IP")
            val result = repository.getDeviceInitialStatus(IP)
            when {
                result.isSuccessful && result.body() != null -> {
                    result.body()?.let {
                        _deviceStatus.value = it.initial_status
                    }
                }
                else -> {
//                    _error.value = result.errorBody().toString()
                }
            }

        }
    }



}