package com.avc.advanager.data.source
import androidx.lifecycle.LiveData
import com.avc.advanager.data.*
import com.avc.advanager.data.response.DeviceInitialResponse
import com.avc.advanager.data.response.LoginUserResponse
import com.avc.advanager.data.response.RegisterUserResponse
import retrofit2.Response

interface AdvanagerRepository {

    suspend fun refreshDevices()

    suspend fun getDevices(askFromWSDiscovery: Boolean): Result<List<Device>>

    suspend fun getDevice(deviceIp: String): Result<Device>

    fun observeDevices(): LiveData<Result<List<Device>>>

    suspend fun getDeviceInitialStatus(IP:String): Response<DeviceInitialResponse>

    suspend fun postUserRegister(registerInfo: RegisterInfo): Response<RegisterUserResponse>

    suspend fun postUserLogin(accountInfo: AccountInfo): Response<LoginUserResponse>

    suspend fun postUserLogout(token: Token): Response<Token>
}
