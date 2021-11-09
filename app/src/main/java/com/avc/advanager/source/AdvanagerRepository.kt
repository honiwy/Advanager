package com.avc.advanager.source
import com.avc.advanager.data.AccountInfo
import com.avc.advanager.data.RegisterInfo
import com.avc.advanager.data.Result
import com.avc.advanager.data.Token
import com.avc.advanager.response.DeviceInitialResponse
import com.avc.advanager.response.LoginUserResponse
import com.avc.advanager.response.RegisterUserResponse

interface AdvanagerRepository {

    suspend fun getDeviceIPList(): Result<List<String>>

    suspend fun getDeviceInitialStatus(IP:String): Result<DeviceInitialResponse>

    suspend fun postUserRegister(registerInfo: RegisterInfo): Result<RegisterUserResponse>

    suspend fun postUserLogin(accountInfo: AccountInfo): Result<LoginUserResponse>

    suspend fun postUserLogout(token: Token): Result<Token>
}
