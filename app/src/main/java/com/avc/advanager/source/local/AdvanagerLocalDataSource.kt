package com.avc.advanager.source.local

import android.content.Context
import com.avc.advanager.source.AdvanagerDataSource
import com.avc.advanager.data.Result


class AdvanagerLocalDataSource(val context: Context) : AdvanagerDataSource {
    override suspend fun getDeviceIPList(): Result<List<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}