package com.avc.advanager.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ErrorResponse(
    @Json(name = "error_code")
    var errorCode: Int
) : Parcelable {
    companion object {
        const val REGISTER_FAIL = 9036 // ACCOUNT OR PASSWORD UNQUALIFIED
        const val ACCOUNT_DUPLICATE = 9001
    }
}
