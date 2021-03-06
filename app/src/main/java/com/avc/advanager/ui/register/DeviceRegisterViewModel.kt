package com.avc.advanager.ui.register

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avc.advanager.AdvanagerApplication
import com.avc.advanager.data.RegisterInfo
import com.avc.advanager.data.source.AdvanagerRepository
import com.avc.advanager.data.source.LoadStatus
import com.avc.advanager.ui.DeviceManager
import com.avc.advanager.util.Constants
import kotlinx.coroutines.launch
import javax.inject.Inject


class DeviceRegisterViewModel @Inject constructor(
    private val repository: AdvanagerRepository
) : ViewModel() {

    val selectedIP: String = DeviceManager.deviceIp ?: ""

    val account = MutableLiveData<String>().apply {
        value = ""
    }

    val password = MutableLiveData<String>().apply {
        value = ""
    }

    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String> = _passwordError

    val repeatedPassword = MutableLiveData<String>().apply {
        value = ""
    }

    private val _repeatedPasswordError = MutableLiveData<String>()
    val repeatedPasswordError: LiveData<String> = _repeatedPasswordError

    private val _registerButtonEnabled = MutableLiveData<Boolean>()
    val registerButtonEnabled: LiveData<Boolean> = _registerButtonEnabled

    private val _loadingStatus = MutableLiveData<LoadStatus>()
    val loadingStatus: LiveData<LoadStatus> = _loadingStatus

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

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
                if (it.length < Constants.PASSWORD_LOWER_LIMIT)
                    _passwordError.value =
                        "Length of password should be more than ${Constants.PASSWORD_LOWER_LIMIT} character"
                else if (it.length > Constants.PASSWORD_UPPER_LIMIT)
                    _passwordError.value =
                        "Length of password should be less than ${Constants.PASSWORD_UPPER_LIMIT} character"
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
                    _passwordError.value = null
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
                    _repeatedPasswordError.value = null
                }
            }
        }
    }

    fun register() {
        _loadingStatus.value = LoadStatus.LOADING
        viewModelScope.launch {
            val registerInfo = RegisterInfo(
                permissionType = Constants.PERMISSION_SUPER_ADMINSTRATOR,
                account = account.value ?: "admin",
                password = password.value ?: "P@ssw0rD"
            )
            val result = repository.postUserRegister(registerInfo)
            when {
                result.isSuccessful && result.body() != null -> {
                    _loadingStatus.value = LoadStatus.DONE
                    _error.value = null
                    DeviceManager.deviceAccount = registerInfo.account
                    DeviceManager.devicePassword = registerInfo.password
                    navigateToLogin()
                }
                else -> {
                    _loadingStatus.value = LoadStatus.ERROR
                    _error.value = result.errorBody().toString()
                }
            }
        }
    }

    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean> = _navigateToLogin

    private fun navigateToLogin() {
        _navigateToLogin.value = true
        Toast.makeText(
            AdvanagerApplication.appContext,
            "Register success!",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun onSucceeded() {
        _navigateToLogin.value = null
    }

}