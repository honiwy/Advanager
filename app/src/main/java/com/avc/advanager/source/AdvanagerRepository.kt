package com.avc.advanager.source
import com.avc.advanager.data.LoginInfo
import com.avc.advanager.data.RegisterInfo
import com.avc.advanager.data.Result
import com.avc.advanager.response.DeviceInitialResponse
import com.avc.advanager.response.LoginUserResponse
import com.avc.advanager.response.RegisterUserResponse

interface AdvanagerRepository {

    suspend fun getDeviceIPList(): Result<List<String>>

    suspend fun getDeviceInitialStatus(IP:String): Result<DeviceInitialResponse>

    suspend fun postUserRegister(registerInfo: RegisterInfo): Result<RegisterUserResponse>

    suspend fun postUserLogin(loginInfo: LoginInfo): Result<LoginUserResponse>
}
