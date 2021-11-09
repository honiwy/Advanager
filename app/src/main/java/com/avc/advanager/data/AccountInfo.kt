package com.avc.advanager.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
open class AccountInfo(
    @Json(name = "account")
    open var account: String = "",

    @Json(name = "password")
    open var password: String = ""
) : Parcelable
