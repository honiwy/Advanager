package com.avc.advanager.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avc.advanager.data.Device
import com.avc.advanager.data.Result
import com.avc.advanager.data.source.AdvanagerRepository
import com.avc.advanager.data.source.LoadStatus
import com.avc.advanager.ui.DeviceManager
import kotlinx.coroutines.launch

class DeviceSearchViewModel(private val repository: AdvanagerRepository) :
    ViewModel() {

    private val _ipList = MutableLiveData<List<Device>>()
    val ipList: LiveData<List<Device>> = _ipList

    private val _deviceStatus = MutableLiveData<Int>()
    val deviceStatus: LiveData<Int> = _deviceStatus

    var typedIP = MutableLiveData<String>().apply {
        value = ""
    }

    private val _loadingStatus = MutableLiveData<LoadStatus>()
    val loadingStatus: LiveData<LoadStatus> = _loadingStatus

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private fun loadIpOfDevices(askFromWSDiscovery: Boolean) {
        _loadingStatus.value = LoadStatus.LOADING
        viewModelScope.launch {
            val result = repository.getDevices(askFromWSDiscovery)
            _ipList.value = when (result) {
                is Result.Success -> {
                    _loadingStatus.value = LoadStatus.DONE
                    _error.value = null
                    result.data
                }
                is Result.Fail -> {
                    _loadingStatus.value = LoadStatus.ERROR
                    _error.value = result.error
                    null
                }
                is Result.Error -> {
                    _loadingStatus.value = LoadStatus.ERROR
                    _error.value = result.exception.toString()
                    null
                }
                else -> {
                    _loadingStatus.value = LoadStatus.ERROR
                    _error.value = "You don't know what happen"
                    null
                }
            }
        }
    }

    init {
        loadIpOfDevices(true)
    }

    fun refresh() {
        if (_loadingStatus.value != LoadStatus.LOADING) {
            loadIpOfDevices(true)
        }
    }

    fun checkTypedIp() {
        typedIP.value?.let {
            checkDeviceStatus(it)
        }
    }

    fun checkDeviceStatus(IP: String) {
        DeviceManager.deviceIp = IP
        _loadingStatus.value = LoadStatus.LOADING
        viewModelScope.launch {
            val result = repository.getDeviceInitialStatus(IP)
            when {
                result.isSuccessful && result.body() != null -> {
                    _loadingStatus.value = LoadStatus.DONE
                    _error.value = null
                    result.body()?.let {
                        _deviceStatus.value = it.initial_status
                    }
                }
                else -> {
                    _loadingStatus.value = LoadStatus.ERROR
                    _error.value = result.errorBody().toString()
                }
            }

        }
    }

    fun onSucceeded() {
        _deviceStatus.value = null
    }


}