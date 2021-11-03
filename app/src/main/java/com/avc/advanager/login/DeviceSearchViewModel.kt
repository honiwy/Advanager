package com.avc.advanager.login

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avc.advanager.AdvanagerApplication
import com.avc.advanager.R
import com.avc.advanager.data.Result
import com.avc.advanager.source.AdvanagerRepository
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
        scanDevice()
    }

    private val _ipList = MutableLiveData<List<String>>()

    val ipList: LiveData<List<String>>
        get() = _ipList


    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun scanDevice() {
        coroutineScope.launch {
            if (!Util.isWifiEnabled()) {
                Toast.makeText(
                    AdvanagerApplication.appContext,
                    getString(R.string.info_scan_warning_no_wifi),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val result = repository.getDeviceIPList()
                _ipList.value = when (result) {
                    is Result.Success -> {
                        result.data
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


}