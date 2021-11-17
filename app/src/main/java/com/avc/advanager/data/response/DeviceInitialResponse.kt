package com.avc.advanager.data.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DeviceInitialResponse(
    @Json(name = "initial_status")
    var initial_status: Int
) : Parcelable
