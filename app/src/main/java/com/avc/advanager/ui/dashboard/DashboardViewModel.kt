package com.avc.advanager.ui.dashboard

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avc.advanager.AdvanagerApplication
import com.avc.advanager.data.Token
import com.avc.advanager.data.source.AdvanagerRepository
import com.avc.advanager.ui.DeviceManager
import kotlinx.coroutines.launch

class DashboardViewModel(private val repository: AdvanagerRepository) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            DeviceManager.deviceToken?.let {
                val result = repository.postUserLogout(Token(it))
                when {
                    result.isSuccessful && result.body() != null -> {
                        navigateToDeviceSearch()
                    }
                    else -> {
//                    _error.value = result.errorBody().toString()
                    }
                }
            }
        }
    }

    private val _navigateToDeviceSearch = MutableLiveData<Boolean>()
    val navigateToDeviceSearch: LiveData<Boolean> = _navigateToDeviceSearch

    private fun navigateToDeviceSearch() {
        _navigateToDeviceSearch.value = true
        Toast.makeText(
            AdvanagerApplication.appContext,
            "Logout success!",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun emptyLoginInfo() {
        DeviceManager.devicePassword = ""
        DeviceManager.deviceAccount = ""
        DeviceManager.deviceIp = ""
    }

    fun onSucceeded() {
        emptyLoginInfo()
        _navigateToDeviceSearch.value = null
    }
}