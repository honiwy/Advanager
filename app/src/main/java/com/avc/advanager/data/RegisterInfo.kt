package com.avc.advanager.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegisterInfo(
    @Json(name = "permission_type")
    var permissionType: Int,

    @Json(name = "account")
    var account: String = "",

    @Json(name = "password")
    var password: String = ""
) : Parcelable {
    companion object {
        const val ADMINSTRATOR = 0
        const val GUEST = 1
        const val SUPER_ADMINSTRATOR = 99
    }
}
