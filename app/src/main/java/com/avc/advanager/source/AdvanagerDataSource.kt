package com.avc.advanager.source
import com.avc.advanager.data.Result
import com.avc.advanager.response.DeviceInitialResponse


interface AdvanagerDataSource {
    suspend fun getDeviceIPList(): Result<List<String>>

    suspend fun getDeviceInitialStatus(IP:String): Result<DeviceInitialResponse>
}