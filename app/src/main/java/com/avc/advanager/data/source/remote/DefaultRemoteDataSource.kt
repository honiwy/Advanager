package com.avc.advanager.data.source.remote

import android.util.Log
import com.avc.advanager.R
import com.avc.advanager.data.AccountInfo
import com.avc.advanager.data.RegisterInfo
import com.avc.advanager.data.Result
import com.avc.advanager.data.Token
import com.avc.advanager.di.checkRetrofitUpdateRequired
import com.avc.advanager.response.DeviceInitialResponse
import com.avc.advanager.response.LoginUserResponse
import com.avc.advanager.response.RegisterUserResponse
import com.avc.advanager.util.Util
import com.avc.advanager.util.Util.getString
import com.avc.advanager.util.Util.isInternetConnected
import com.avc.advanager.util.WSDeviceDiscovery
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class DefaultRemoteDataSource @Inject constructor(private val apiService: RetrofitApiService) : RemoteDataSource {
//
    override suspend fun getDeviceIPList(): Result<List<String>> =
        suspendCoroutine { continuation ->
            if (!Util.isWifiEnabled()) {
                continuation.resume(Result.Error(getString(R.string.wifi_warning)))
            }
            val urls = WSDeviceDiscovery.discoverWsDevicesAsUrls()
            val listOfUrl = mutableListOf<String>()
            for (url in urls) {
                listOfUrl.add(url)
            }
            Log.d("AdvanagerRemoteDataSource", "getDeviceIPList: " + listOfUrl.size)

            continuation.resume(Result.Success(listOfUrl))
        }


    override suspend fun getDeviceInitialStatus(IP: String): Response<DeviceInitialResponse> {
        if (!isInternetConnected()) {
            return error(getString(R.string.internet_warning))
        }

        checkRetrofitUpdateRequired(IP)
        return apiService.postDeviceInitiate()
    }

    override suspend fun postUserRegister(registerInfo: RegisterInfo): Response<RegisterUserResponse> {
        if (!isInternetConnected()) {
            return error(getString(R.string.internet_warning))
        }
        return apiService.postUserRegister(registerInfo)
    }


    override suspend fun postUserLogin(accountInfo: AccountInfo): Response<LoginUserResponse> {
        if (!isInternetConnected()) {
            return error(getString(R.string.internet_warning))
        }
        return apiService.postUserLogin(accountInfo)
    }

    override suspend fun postUserLogout(token: Token): Response<Token>{
        if (!isInternetConnected()) {
            return error(getString(R.string.internet_warning))
        }
        return apiService.postUserLogout(token)
    }
}