package com.avc.advanager.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.avc.advanager.data.Device

@Dao
interface AdvanagerDao {
    @Query("SELECT * FROM device_login_table")
    fun observeDevices(): LiveData<List<Device>>

    @Query("SELECT * FROM device_login_table")
    suspend fun getDevices(): List<Device>

    @Query("SELECT * FROM device_login_table WHERE ip = :deviceIp")
    suspend fun getDeviceByIp(deviceIp: String): Device?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDevice(device: Device)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDevices(device: List<Device>)

    @Transaction
    suspend fun insertNumberOfDevices(device: Device) {
        insertDevice(device)
    }
}