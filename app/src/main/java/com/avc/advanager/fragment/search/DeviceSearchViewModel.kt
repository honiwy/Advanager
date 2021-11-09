package com.avc.advanager.fragment.search

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avc.advanager.AdvanagerApplication
import com.avc.advanager.R
import com.avc.advanager.data.Result
import com.avc.advanager.fragment.DeviceManager
import com.avc.advanager.source.AdvanagerRepository
import com.avc.advanager.source.LoadStatus
import com.avc.advanager.util.Util
import com.avc.advanager.util.Util.getString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DeviceSearchViewModel(private val repository: AdvanagerRepository) : ViewModel() {

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        val lastIP = DeviceManager.deviceIp
        if (!lastIP.isNullOrEmpty())//has login before
            checkDeviceStatus(lastIP)
        scanDevice()
    }

    private val _ipList = MutableLiveData<List<String>>()

    val ipList: LiveData<List<String>>
        get() = _ipList

    var typedIP = MutableLiveData<String>().apply {
        value = ""
    }

    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    private val _deviceStatus = MutableLiveData<Number>()

    val deviceStatus: LiveData<Number>
        get() = _deviceStatus

    private val _status = MutableLiveData<LoadStatus>()

    val status: LiveData<LoadStatus>
        get() = _status

    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun refresh() {
        if (status.value != LoadStatus.LOADING) {
            scanDevice()
        }
    }

    private fun scanDevice() {
        coroutineScope.launch {
            if (!Util.isWifiEnabled()) {
                Toast.makeText(
                    AdvanagerApplication.appContext,
                    getString(R.string.wifi_warning),
                    Toast.LENGTH_SHORT
                ).show()
                _status.value = LoadStatus.ERROR
            } else {
                _status.value = LoadStatus.LOADING
                val result = repository.getDeviceIPList()
                _ipList.value = when (result) {
                    is Result.Success -> {
                        _status.value = LoadStatus.DONE
                        if (result.data.isEmpty()) {
                            Toast.makeText(
                                AdvanagerApplication.appContext,
                                "No available device is detected!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        result.data
                    }
                    is Result.Fail -> {
                        _status.value = LoadStatus.ERROR
                        null
                    }
                    is Result.Error -> {
                        _status.value = LoadStatus.ERROR
                        null
                    }
                    else -> {
                        _status.value = LoadStatus.ERROR
                        null
                    }
                }
                _refreshStatus.value = false
            }
        }
    }

    fun checkDeviceStatus(IP: String) {
        DeviceManager.deviceIp = IP
        coroutineScope.launch {
            val result = repository.getDeviceInitialStatus(IP)
            _deviceStatus.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    result.data.initial_status
                }
                is Result.Fail -> {
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    null
                }
                else -> {
                    null
                }
            }
        }
    }


}