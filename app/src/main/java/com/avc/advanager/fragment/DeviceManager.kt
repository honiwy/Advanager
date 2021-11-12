package com.avc.advanager.fragment

import android.content.Context
import com.avc.advanager.AdvanagerApplication

object DeviceManager {
    private const val DEVICE_DATA = "device_data"
    private const val DEVICE_IP = "device_ip"
    private const val DEVICE_ACCOUNT = "device_account"
    private const val DEVICE_PASSWORD = "device_password"
    private const val DEVICE_TOKEN = "device_token"

    var deviceIp: String? = null
        get() = AdvanagerApplication.appContext
            .getSharedPreferences(DEVICE_DATA, Context.MODE_PRIVATE)
            .getString(DEVICE_IP, "")
        set(value) {
            field = when (value) {
                null -> {
                    AdvanagerApplication.appContext
                        .getSharedPreferences(DEVICE_DATA, Context.MODE_PRIVATE).edit()
                        .remove(DEVICE_IP)
                        .apply()
                    null
                }
                else -> {
                    AdvanagerApplication.appContext
                        .getSharedPreferences(DEVICE_DATA, Context.MODE_PRIVATE).edit()
                        .putString(DEVICE_IP, value)
                        .apply()
                    value
                }
            }
        }

    var deviceAccount: String? = null
        get() = AdvanagerApplication.appContext
            .getSharedPreferences(DEVICE_DATA, Context.MODE_PRIVATE)
            .getString(DEVICE_ACCOUNT, "")
        set(value) {
            field = when (value) {
                null -> {
                    AdvanagerApplication.appContext
                        .getSharedPreferences(DEVICE_DATA, Context.MODE_PRIVATE).edit()
                        .remove(DEVICE_ACCOUNT)
                        .apply()
                    null
                }
                else -> {
                    AdvanagerApplication.appContext
                        .getSharedPreferences(DEVICE_DATA, Context.MODE_PRIVATE).edit()
                        .putString(DEVICE_ACCOUNT, value)
                        .apply()
                    value
                }
            }
        }

    var devicePassword: String? = null
        get() = AdvanagerApplication.appContext
            .getSharedPreferences(DEVICE_DATA, Context.MODE_PRIVATE)
            .getString(DEVICE_PASSWORD, "")
        set(value) {
            field = when (value) {
                null -> {
                    AdvanagerApplication.appContext
                        .getSharedPreferences(DEVICE_DATA, Context.MODE_PRIVATE).edit()
                        .remove(DEVICE_PASSWORD)
                        .apply()
                    null
                }
                else -> {
                    AdvanagerApplication.appContext
                        .getSharedPreferences(DEVICE_DATA, Context.MODE_PRIVATE).edit()
                        .putString(DEVICE_PASSWORD, value)
                        .apply()
                    value
                }
            }
        }


    var deviceToken: String? = null
        get() = AdvanagerApplication.appContext
            .getSharedPreferences(DEVICE_DATA, Context.MODE_PRIVATE)
            .getString(DEVICE_TOKEN, "")
        set(value) {
            field = when (value) {
                null -> {
                    AdvanagerApplication.appContext
                        .getSharedPreferences(DEVICE_DATA, Context.MODE_PRIVATE).edit()
                        .remove(DEVICE_TOKEN)
                        .apply()
                    null
                }
                else -> {
                    AdvanagerApplication.appContext
                        .getSharedPreferences(DEVICE_DATA, Context.MODE_PRIVATE).edit()
                        .putString(DEVICE_TOKEN, value)
                        .apply()
                    value
                }
            }
        }
}