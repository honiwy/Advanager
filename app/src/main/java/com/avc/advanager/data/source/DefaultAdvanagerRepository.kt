package com.avc.advanager.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import com.avc.advanager.data.*
import com.avc.advanager.data.source.local.LocalDataSource
import com.avc.advanager.data.source.remote.RemoteDataSource
import com.avc.advanager.data.response.DeviceInitialResponse
import com.avc.advanager.data.response.LoginUserResponse
import com.avc.advanager.data.response.RegisterUserResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class DefaultAdvanagerRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AdvanagerRepository {

    override suspend fun refreshDevices() {
        try {
            updateDevicesFromRemoteDataSource()
        } catch (ex: Exception) {
            Log.e("DefaultAdvanagerRepository", ex.toString())
        }
    }

    override suspend fun getDevices(askFromWSDiscovery: Boolean): Result<List<Device>> {
        if (askFromWSDiscovery) {
            try {
                updateDevicesFromRemoteDataSource()
            } catch (ex: Exception) {
                return Result.Error(ex.localizedMessage!!)
            }
        }
        return localDataSource.getDevices()
    }

    override suspend fun getDevice(deviceId: String): Result<Device> {
        return localDataSource.getDevice(deviceId)
    }

    private suspend fun updateDevicesFromRemoteDataSource() = withContext(ioDispatcher) {
        try {
            when (val result = remoteDataSource.getDeviceIPList()) {
                is Result.Success -> {
                    val deviceList: List<Device> =
                        result.data.map { ip ->
                            Device.convertRemoteIPToLocalDevice(ip)
                        }
                    localDataSource.insertDevices(deviceList)
                }
                else -> {
                    throw Exception("no result")
                }
            }
        } catch (e: HttpException) {
            throw e
        } catch (e: IOException) {
            throw e
        }
    }

    override fun observeDevices(): LiveData<Result<List<Device>>> {
        return localDataSource.observeDevices()
    }

    override suspend fun getDeviceInitialStatus(IP: String): Response<DeviceInitialResponse> {
        return remoteDataSource.getDeviceInitialStatus(IP)
    }

    override suspend fun postUserRegister(registerInfo: RegisterInfo): Response<RegisterUserResponse> {
        return remoteDataSource.postUserRegister(registerInfo)
    }


    override suspend fun postUserLogin(accountInfo: AccountInfo): Response<LoginUserResponse> {
        return remoteDataSource.postUserLogin(accountInfo)
    }

    override suspend fun postUserLogout(token: Token): Response<Token> {
        return remoteDataSource.postUserLogout(token)
    }
}