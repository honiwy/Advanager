package com.avc.advanager.data.source.remote

import com.avc.advanager.data.AccountInfo
import com.avc.advanager.data.RegisterInfo
import com.avc.advanager.data.Token
import com.avc.advanager.data.response.DeviceInitialResponse
import com.avc.advanager.data.response.LoginUserResponse
import com.avc.advanager.data.response.RegisterUserResponse
import com.avc.advanager.util.Constants.API_TIMEOUT
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

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

private val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
    .readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
    .writeTimeout(API_TIMEOUT, TimeUnit.SECONDS)
    .connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

var BASE_URL = "http://192.168.1.168/apis/androvideo/"
private var retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

object RetrofitApi {
    var retrofitService: RetrofitApiService =
        retrofit.create(
            RetrofitApiService::class.java
        )

    fun checkRetrofitUpdateRequired(newIP: String) {
        val newUrl = "http://$newIP/apis/androvideo/"
        if (BASE_URL != newUrl) {
            BASE_URL = newUrl
            retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(BASE_URL)
                .client(client)
                .build()
            retrofitService = retrofit.create(RetrofitApiService::class.java)
        }
    }
}



