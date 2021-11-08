package com.avc.advanager.source.local

import android.content.Context
import com.avc.advanager.data.LoginInfo
import com.avc.advanager.data.RegisterInfo
import com.avc.advanager.source.AdvanagerDataSource
import com.avc.advanager.data.Result
import com.avc.advanager.response.DeviceInitialResponse
import com.avc.advanager.response.LoginUserResponse
import com.avc.advanager.response.RegisterUserResponse


class AdvanagerLocalDataSource(val context: Context) : AdvanagerDataSource {
    override suspend fun getDeviceIPList(): Result<List<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getDeviceInitialStatus(IP:String): Result<DeviceInitialResponse>{
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun postUserRegister(registerInfo: RegisterInfo): Result<RegisterUserResponse>{
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun postUserLogin(loginInfo: LoginInfo): Result<LoginUserResponse>{
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}