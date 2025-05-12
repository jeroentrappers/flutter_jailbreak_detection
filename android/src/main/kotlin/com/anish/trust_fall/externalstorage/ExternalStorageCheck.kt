package com.anish.trust_fall.externalstorage

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build

object ExternalStorageCheck {
    /**
     * Checks if the application is installed on the SD card.
     *
     * @return `true` if the application is installed on the sd card
     */
    @SuppressLint("ObsoleteSdkInt", "SdCardPath")
    fun isOnExternalStorage(context: Context?): Boolean {
        if (context == null) return false

        // check for API level 8 and higher
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ECLAIR_MR1) {
            val pm = context.packageManager
            try {
                val pi = pm.getPackageInfo(context.packageName, 0)
                val ai = pi.applicationInfo
                return ai?.flags?.and(ApplicationInfo.FLAG_EXTERNAL_STORAGE) == ApplicationInfo.FLAG_EXTERNAL_STORAGE
            } catch (e: PackageManager.NameNotFoundException) {
                // ignore
            }
        }

        // check for API level 7 - check files dir
        try {
            val filesDir = context.filesDir.absolutePath
            if (filesDir.startsWith("/data/")) {
                return false
            } else if (filesDir.contains("/mnt/") || filesDir.contains("/sdcard/")) {
                return true
            }
        } catch (e: Throwable) {
            // ignore
        }
        return false
    }
}