package com.avc.advanager.source

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import com.avc.advanager.data.Result
import com.avc.advanager.response.DeviceInitialResponse

class DefaultAdvanagerRepository(
    private val remoteDataSource: AdvanagerDataSource,
    private val localDataSource: AdvanagerDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AdvanagerRepository {

    override suspend fun getDeviceIPList(): Result<List<String>> {
        return remoteDataSource.getDeviceIPList()
    }

    override suspend fun getDeviceInitialStatus(IP:String): Result<DeviceInitialResponse>{
        return remoteDataSource.getDeviceInitialStatus(IP)
    }
}