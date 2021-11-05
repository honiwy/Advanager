package com.avc.advanager.source.remote

import com.avc.advanager.R
import com.avc.advanager.data.Result
import com.avc.advanager.response.DeviceInitialResponse
import com.avc.advanager.source.AdvanagerDataSource
import com.avc.advanager.util.Util.getString
import com.avc.advanager.util.Util.isInternetConnected
import com.avc.advanager.utils.WSDeviceDiscovery
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


object AdvanagerRemoteDataSource : AdvanagerDataSource {

    override suspend fun getDeviceIPList(): Result<List<String>> =
        suspendCoroutine { continuation ->
            val urls = WSDeviceDiscovery.discoverWsDevicesAsUrls()
            val listOfUrl = mutableListOf<String>()
            for (url in urls) {
                listOfUrl.add(url)
            }
            continuation.resume(Result.Success(listOfUrl))
        }


    override suspend fun getDeviceInitialStatus(IP:String): Result<DeviceInitialResponse> {
        if (!isInternetConnected()) {
            return Result.Fail(getString(R.string.internet_warning))
        }
        RetrofitApi.checkRetrofitUpdateRequired(IP)
        val getResultDeferred = RetrofitApi.retrofitService.postUserInitiate()
        return try {
            Result.Success(getResultDeferred.await())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}