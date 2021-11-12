package com.avc.advanager.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Token(
    var token: String
) : Parcelable