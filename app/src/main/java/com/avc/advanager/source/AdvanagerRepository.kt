package com.avc.advanager.source
import com.avc.advanager.data.Result

interface AdvanagerRepository {

    suspend fun getDeviceIPList(): Result<List<String>>

}
