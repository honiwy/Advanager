package com.avc.advanager.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
data class LoginUserResponse(
    @Json(name = "permission_type")
    var permissionType: Int,
    @Json(name = "token")
    var token: String
) : Parcelable {

}
