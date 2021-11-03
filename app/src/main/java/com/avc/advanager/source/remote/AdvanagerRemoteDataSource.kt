package com.avc.advanager.source.remote

import android.util.Log
import com.avc.advanager.data.Result
import com.avc.advanager.source.AdvanagerDataSource
import com.avc.advanager.utils.WSDeviceDiscovery
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


object AdvanagerRemoteDataSource : AdvanagerDataSource {

    override suspend fun getDeviceIPList(): Result<List<String>> =
        suspendCoroutine { continuation ->
            val urls = WSDeviceDiscovery.discoverWsDevicesAsUrls()
            val listOfUrl = mutableListOf<String>()
            for(url in urls) {
                listOfUrl.add(url)
            }
            continuation.resume(Result.Success(listOfUrl))
        }
}