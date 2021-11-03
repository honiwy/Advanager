package com.avc.advanager.util

import android.content.Context
import android.net.wifi.WifiManager
import com.avc.advanager.AdvanagerApplication


object Util {
    fun isWifiEnabled(): Boolean {
        val wifiManager =
            AdvanagerApplication.appContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifiManager.isWifiEnabled
    }

    fun getString(resourceId: Int): String {
        return AdvanagerApplication.appContext.getString(resourceId)
    }
}