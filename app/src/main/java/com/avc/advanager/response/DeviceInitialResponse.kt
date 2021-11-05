package com.avc.advanager.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DeviceInitialResponse(
    @Json(name = "initial_status")
    var initial_status: Int
) : Parcelable {
    companion object {
        const val INITIAL_YET = 0
        const val INITIAL_ALREADY = 1
    }
}
