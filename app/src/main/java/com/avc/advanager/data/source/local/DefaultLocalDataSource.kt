package com.avc.advanager.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.avc.advanager.data.Device
import com.avc.advanager.data.Result
import javax.inject.Inject


class DefaultLocalDataSource @Inject constructor(private val dao: AdvanagerDao) :
    LocalDataSource {

    override suspend fun getDevices(): Result<List<Device>> {
        return Result.Success(dao.getDevices())
    }

    override suspend fun getDevice(ip: String): Result<Device> {
        val device = dao.getDeviceByIp(ip)
        device?.let {
            return Result.Success(it)
        }
        return Result.Error("Device not found!")
    }

    override suspend fun insertDevices(devices:List<Device>){
        return dao.insertDevices(devices)
    }

    override fun observeDevices(): LiveData<Result<List<Device>>> {
        return dao.observeDevices().map { Result.Success(it) }
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