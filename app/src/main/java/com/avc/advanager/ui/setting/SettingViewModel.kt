package com.avc.advanager.ui.setting

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avc.advanager.AdvanagerApplication
import com.avc.advanager.R
import com.avc.advanager.data.Result
import com.avc.advanager.data.Token
import com.avc.advanager.fragment.DeviceManager
import com.avc.advanager.data.source.AdvanagerRepository
import com.avc.advanager.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SettingViewModel (private val repository: AdvanagerRepository): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is setting Fragment"
    }
    val text: LiveData<String> = _text


    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun logout() {
//        Toast.makeText(
//            AdvanagerApplication.appContext,
//            "Logout!",
//            Toast.LENGTH_SHORT
//        ).show()
//        coroutineScope.launch {
//            if (!Util.isWifiEnabled()) {
//                Toast.makeText(
//                    AdvanagerApplication.appContext,
//                    Util.getString(R.string.wifi_warning),
//                    Toast.LENGTH_SHORT
//                ).show()
//            } else {
//                DeviceManager.deviceToken?.let {
//                    val result = repository.postUserLogout(Token(it))
//                    when (result) {
//                        is Result.Success -> {
//                            result.data
//                            navigateToDeviceSearch()
//                        }
//                        is Result.Fail -> {
//                            null
//                        }
//                        is Result.Error -> {
//                            null
//                        }
//                        else -> {
//                            null
//                        }
//                    }
//                }
//            }
//        }
    }

    private val _navigateToDeviceSearch = MutableLiveData<Boolean>()

    val navigateToDeviceSearch: LiveData<Boolean>
        get() = _navigateToDeviceSearch

    private fun navigateToDeviceSearch() {
        _navigateToDeviceSearch.value = true
//        Toast.makeText(
//            AdvanagerApplication.appContext,
//            "Logout success!",
//            Toast.LENGTH_SHORT
//        ).show()
    }

    private fun emptyLoginInfo() {
        DeviceManager.devicePassword=""
        DeviceManager.deviceAccount=""
        DeviceManager.deviceIp=""
    }

    fun onSucceeded() {
        emptyLoginInfo()
//        _navigateToDeviceSearch.value = null
    }
}