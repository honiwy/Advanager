package com.avc.advanager.data.source.remote

import com.avc.advanager.data.AccountInfo
import com.avc.advanager.data.RegisterInfo
import com.avc.advanager.data.Token
import com.avc.advanager.response.DeviceInitialResponse
import com.avc.advanager.response.LoginUserResponse
import com.avc.advanager.response.RegisterUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitApiService {

    @POST("users/initial")
    @Headers("Content-Type: application/json")
    suspend fun postDeviceInitiate(): Response<DeviceInitialResponse>

    @POST("users/register")
    @Headers("Content-Type: application/json")
    suspend fun postUserRegister(@Body registerInfo: RegisterInfo): Response<RegisterUserResponse>

    @POST("users/login")
    @Headers("Content-Type: application/json")
    suspend fun postUserLogin(
        @Body accountInfo: AccountInfo
    ): Response<LoginUserResponse>

    @POST("users/logout")
    @Headers("Content-Type: application/json")
    suspend fun postUserLogout(
        @Body token: Token
    ): Response<Token>

}
