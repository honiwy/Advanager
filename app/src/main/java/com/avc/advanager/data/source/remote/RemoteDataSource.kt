package com.avc.advanager.data.source.remote
import com.avc.advanager.data.*
import com.avc.advanager.response.DeviceInitialResponse
import com.avc.advanager.response.LoginUserResponse
import com.avc.advanager.response.RegisterUserResponse
import retrofit2.Response


interface RemoteDataSource {
    suspend fun getDeviceIPList(): Result<List<String>>

    suspend fun getDeviceInitialStatus(IP:String): Response<DeviceInitialResponse>

    suspend fun postUserRegister(registerInfo: RegisterInfo): Response<RegisterUserResponse>

    suspend fun postUserLogin(accountInfo: AccountInfo): Response<LoginUserResponse>

    suspend fun postUserLogout(token: Token): Response<Token>
}