package com.avc.advanager.source

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import com.avc.advanager.data.Result

class DefaultAdvanagerRepository(
    private val remoteDataSource: AdvanagerDataSource,
    private val localDataSource: AdvanagerDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AdvanagerRepository {

    override suspend fun getDeviceIPList(): Result<List<String>> {
        return remoteDataSource.getDeviceIPList()
    }

}