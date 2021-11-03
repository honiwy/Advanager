package com.avc.advanager.source
import com.avc.advanager.data.Result


interface AdvanagerDataSource {
    suspend fun getDeviceIPList(): Result<List<String>>
}