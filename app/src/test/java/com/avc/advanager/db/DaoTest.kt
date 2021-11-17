package com.avc.advanager.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.avc.advanager.data.Device
import com.avc.advanager.data.source.local.AdvanagerDatabase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DaoTest : BaseDaoTest<AdvanagerDatabase>(AdvanagerDatabase::class.java) {

    private val deviceList = listOf(
        Device(1, "192.168.1.101", "Mickey", "Christmas2021-"),
        Device(2, "192.168.1.123", "Depp", "NewYear2022!")
    )

    @Test
    @Throws(InterruptedException::class)
    fun insertDevicesAndRead(): Unit = runBlocking {
        db.advanagerDao().insertDevices(deviceList)

        // THEN
        MatcherAssert.assertThat(
            db.advanagerDao().getDevices()[0].password,
            CoreMatchers.equalTo("Christmas2021-")
        )
        MatcherAssert.assertThat(
            db.advanagerDao().getDevices()[1].ip,
            CoreMatchers.equalTo("192.168.1.123")
        )
    }

    @Test
    @Throws(InterruptedException::class)
    fun getDeviceByIp(): Unit = runBlocking {
        db.advanagerDao().insertDevices(deviceList)

        val device = db.advanagerDao().getDeviceByIp("192.168.1.123")
        device?.let {
            MatcherAssert.assertThat(device.password, CoreMatchers.equalTo("NewYear2022!"))
            MatcherAssert.assertThat(device.account,CoreMatchers.equalTo("Depp"))
        }

    }
}