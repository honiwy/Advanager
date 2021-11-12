package com.avc.advanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "device_login_table", primaryKeys = ["id","ip"])
@Parcelize
data class Device(
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "ip")
    var ip: String = "",

    @ColumnInfo(name = "account")
    override var account: String,

    @ColumnInfo(name = "password")
    override var password: String
) : AccountInfo(account, password){

    companion object {

        fun convertRemoteIPToLocalDeviceInfo(ip: String): Device {
            return Device(
                ip = ip,
                account =  "",
                password = ""
            )
        }
    }
}