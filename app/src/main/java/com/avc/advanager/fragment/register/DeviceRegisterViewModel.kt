package com.avc.advanager.fragment.register

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avc.advanager.AdvanagerApplication
import com.avc.advanager.R
import com.avc.advanager.data.AccountInfo
import com.avc.advanager.data.RegisterInfo
import com.avc.advanager.data.Result
import com.avc.advanager.fragment.DeviceManager
import com.avc.advanager.data.source.AdvanagerRepository
import com.avc.advanager.data.source.LoadStatus
import com.avc.advanager.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


class DeviceRegisterViewModel@Inject constructor(
    private val repository: AdvanagerRepository
) : ViewModel() {

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val selectedIP: String = DeviceManager.deviceIp?:""

    val account = MutableLiveData<String>().apply {
        value = ""
    }

    val password = MutableLiveData<String>().apply {
        value = ""
    }

    private val _passwordError = MutableLiveData<String>()

    val passwordError: LiveData<String>
        get() = _passwordError

    val repeatedPassword = MutableLiveData<String>().apply {
        value = ""
    }

    private val _repeatedPasswordError = MutableLiveData<String>()

    val repeatedPasswordError: LiveData<String>
        get() = _repeatedPasswordError

    private val _registerButtonEnabled = MutableLiveData<Boolean>()

    val registerButtonEnabled: LiveData<Boolean>
        get() = _registerButtonEnabled

    init {
        //help to fill in
        if (!DeviceManager.deviceAccount.isNullOrEmpty())
            account.value = DeviceManager.deviceAccount
        if (!DeviceManager.devicePassword.isNullOrEmpty())
            password.value = DeviceManager.devicePassword
    }

    fun checkPasswordLegal() {
        password.value?.let {
            if (it.isNotEmpty()) {
                if (it.length < PASSWORD_LOWER_LIMIT)
                    _passwordError.value =
                        "Length of password should be more than $PASSWORD_LOWER_LIMIT character"
                else if (it.length > PASSWORD_UPPER_LIMIT)
                    _passwordError.value =
                        "Length of password should be less than $PASSWORD_UPPER_LIMIT character"
                else if (it == it.lowercase())
                    _passwordError.value = "Password should contain at least one uppercase"
                else if (it == it.uppercase())
                    _passwordError.value = "Password should contain at least one lowercase"
                else if (!it.matches(Regex(".*\\d.*")))
                    _passwordError.value = "Password should contain at least one number"
                else if (it.indexOfAny(
                        charArrayOf(
                            '#',
                            '%',
                            '&',
                            '`',
                            '"',
                            '\\',
                            '/',
                            '<',
                            '>',
                            ' '
                        )
                    ) > 0
                )
                    _passwordError.value =
                        "Password should not contain #, %, &, `, \", \\, /, <, >, and space"
                else if (it.indexOfAny(
                        charArrayOf(
                            '!',
                            '$',
                            '\'',
                            '(',
                            ')',
                            '*',
                            '+',
                            ',',
                            '.',
                            ':',
                            ';',
                            '=',
                            '?',
                            '@',
                            '^',
                            '_',
                            '{',
                            '|',
                            '}',
                            '~',
                            '-'
                        )
                    ) < 0
                )
                    _passwordError.value = "Password should contain at least one symbol"
                else {
//                    _passwordError.value = null
                }
            }
        }
    }

    fun checkPasswordConsistent() {
        repeatedPassword.value?.let {
            if (it.isNotEmpty()) {
                if (it != password.value) {
                    _registerButtonEnabled.value = false
                    _repeatedPasswordError.value = "Passwords don't match"
                } else {
                    _registerButtonEnabled.value = true
//                    _repeatedPasswordError.value = null
                }
            }
        }
    }

    fun register() {
//        coroutineScope.launch {
//            if (!Util.isWifiEnabled()) {
//                Toast.makeText(
//                    AdvanagerApplication.appContext,
//                    Util.getString(R.string.wifi_warning),
//                    Toast.LENGTH_SHORT
//                ).show()
//            } else {
//
//                val registerInfo = RegisterInfo(
//                    permissionType = RegisterInfo.SUPER_ADMINSTRATOR,
//                    account = account.value ?: "admin",
//                    password = password.value ?: "P@ssw0rD"
//                )
//                val result = repository.postUserRegister(registerInfo)
//                when (result) {
//                    is Result.Success -> {
//                        result.data
//                        DeviceManager.deviceAccount = registerInfo.account
//                        DeviceManager.devicePassword = registerInfo.password
//                        navigateToLogin()
//                    }
//                    is Result.Fail -> {
//                        null
//                    }
//                    is Result.Error -> {
//                        null
//                    }
//                    else -> {
//                        null
//                    }
//                }
//            }
//        }
    }

    private val _navigateToLogin = MutableLiveData<Boolean>()

    val navigateToLogin: LiveData<Boolean>
        get() = _navigateToLogin

    private fun navigateToLogin() {
        _navigateToLogin.value = true
        Toast.makeText(
            AdvanagerApplication.appContext,
            "Register success!",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun onSucceeded() {
//        _navigateToLogin.value = null
    }

    companion object {
        const val PASSWORD_LOWER_LIMIT = 8
        const val PASSWORD_UPPER_LIMIT = 16
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}