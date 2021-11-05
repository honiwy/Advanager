package com.avc.advanager.source

import com.avc.advanager.data.RegisterInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import com.avc.advanager.data.Result
import com.avc.advanager.response.DeviceInitialResponse
import com.avc.advanager.response.RegisterUserResponse

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

    override suspend fun postUserRegister(registerInfo: RegisterInfo): Result<RegisterUserResponse>{
        return remoteDataSource.postUserRegister(registerInfo)
    }
}