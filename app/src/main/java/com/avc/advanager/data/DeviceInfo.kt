package com.avc.advanager.data

import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DeviceInfo(
    @Json(name = "ip")
    var ip: String = "",

    override var account: String,

    override var password: String
) : AccountInfo(account, password)