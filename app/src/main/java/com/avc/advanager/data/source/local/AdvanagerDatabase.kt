package com.avc.advanager.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.avc.advanager.data.Device

@Database(
    entities = [Device::class],
    version = 1,
    exportSchema = false
)
abstract class AdvanagerDatabase : RoomDatabase() {
    abstract fun advanagerDao(): AdvanagerDao

}