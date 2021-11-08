package com.avc.advanager.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginInfo(
    @Json(name = "account")
    var account: String = "",

    @Json(name = "password")
    var password: String = ""
) : Parcelable {
}
