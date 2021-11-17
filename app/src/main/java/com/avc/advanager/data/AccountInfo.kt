package com.avc.advanager.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
open class AccountInfo(
    open var account: String = "",

    open var password: String = ""
) : Parcelable
