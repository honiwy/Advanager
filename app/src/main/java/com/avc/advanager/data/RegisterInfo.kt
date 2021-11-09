package com.avc.advanager.data

import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegisterInfo(
    @Json(name = "permission_type")
    var permissionType: Int,

    override var account: String,

    override var password: String
) : AccountInfo(account, password) {
    companion object {
        const val ADMINSTRATOR = 0
        const val GUEST = 1
        const val SUPER_ADMINSTRATOR = 99
    }
}
