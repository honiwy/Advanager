package com.avc.advanager.data.source.remote

import android.util.Log
import com.avc.advanager.R
import com.avc.advanager.data.AccountInfo
import com.avc.advanager.data.RegisterInfo
import com.avc.advanager.data.Result
import com.avc.advanager.data.Token
import com.avc.advanager.data.response.DeviceInitialResponse
import com.avc.advanager.data.response.LoginUserResponse
import com.avc.advanager.data.response.RegisterUserResponse
import com.avc.advanager.util.Util
import com.avc.advanager.util.Util.getString
import com.avc.advanager.util.Util.isInternetConnected
import com.avc.advanager.data.source.WSDeviceDiscovery
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


object DefaultRemoteDataSource : RemoteDataSource {
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

        RetrofitApi.checkRetrofitUpdateRequired(IP)
        return RetrofitApi.retrofitService.postDeviceInitiate()
    }

    override suspend fun postUserRegister(registerInfo: RegisterInfo): Response<RegisterUserResponse> {
        if (!isInternetConnected()) {
            return error(getString(R.string.internet_warning))
        }
        return RetrofitApi.retrofitService.postUserRegister(registerInfo)
    }


    override suspend fun postUserLogin(accountInfo: AccountInfo): Response<LoginUserResponse> {
        if (!isInternetConnected()) {
            return error(getString(R.string.internet_warning))
        }
        return RetrofitApi.retrofitService.postUserLogin(accountInfo)
    }

    override suspend fun postUserLogout(token: Token): Response<Token>{
        if (!isInternetConnected()) {
            return error(getString(R.string.internet_warning))
        }
        return RetrofitApi.retrofitService.postUserLogout(token)
    }
}