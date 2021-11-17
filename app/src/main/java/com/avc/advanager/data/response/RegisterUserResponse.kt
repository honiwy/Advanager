package com.avc.advanager.data.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
data class RegisterUserResponse(
    @Json(name = "permission_type")
    var permissionType: Int,
    @Json(name = "account")
    var account: String
) : Parcelable
