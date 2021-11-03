package com.avc.advanager.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DeviceInfo(
    var ip: String = "",
    var account: String = "",
    var password: String = ""
) : Parcelable