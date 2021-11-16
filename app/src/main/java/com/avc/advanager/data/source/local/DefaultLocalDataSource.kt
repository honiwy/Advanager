package com.avc.advanager.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.avc.advanager.AdvanagerApplication.Companion.appContext
import com.avc.advanager.data.Device
import com.avc.advanager.data.Result


class DefaultLocalDataSource() :LocalDataSource {

    override suspend fun getDevices(): Result<List<Device>> {
        return Result.Success(AdvanagerDatabase.getInstance(appContext).advanagerDao().getDevices())
    }

    override suspend fun getDevice(ip: String): Result<Device> {
        val device = AdvanagerDatabase.getInstance(appContext).advanagerDao().getDeviceByIp(ip)
        device?.let {
            return Result.Success(it)
        }
        return Result.Error("Device not found!")
    }

    override suspend fun insertDevices(devices: List<Device>) {
        return AdvanagerDatabase.getInstance(appContext).advanagerDao().insertDevices(devices)
    }

    override fun observeDevices(): LiveData<Result<List<Device>>> {
        return AdvanagerDatabase.getInstance(appContext).advanagerDao().observeDevices()
            .map { Result.Success(it) }
    }

//    override suspend fun getDeviceInitialStatus(IP: String): Result<DeviceInitialResponse> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override suspend fun postUserRegister(registerInfo: RegisterInfo): Result<RegisterUserResponse> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override suspend fun postUserLogin(accountInfo: AccountInfo): Result<LoginUserResponse> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override suspend fun postUserLogout(token: Token): Result<Token> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
}