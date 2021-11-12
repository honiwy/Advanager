package com.avc.advanager.data.source.local

import androidx.lifecycle.LiveData
import com.avc.advanager.data.*

interface LocalDataSource {

    suspend fun getDevices(): Result<List<Device>>

    suspend fun getDevice(deviceIp: String): Result<Device>

    suspend fun insertDevices(devices:List<Device>)

    fun observeDevices(): LiveData<Result<List<Device>>>

//    suspend fun getDeviceInitialStatus(IP: String): Result<DeviceInitialResponse>
//
//    suspend fun postUserRegister(registerInfo: RegisterInfo): Result<RegisterUserResponse>
//
//    suspend fun postUserLogin(accountInfo: AccountInfo): Result<LoginUserResponse>
//
//    suspend fun postUserLogout(token: Token): Result<Token>
}