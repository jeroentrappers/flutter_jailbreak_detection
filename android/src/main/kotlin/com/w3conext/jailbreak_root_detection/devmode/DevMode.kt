package com.w3conext.jailbreak_root_detection.devmode

import android.content.Context
import android.provider.Settings

object DevMode {
    fun isDevMode(activity: Context?): Boolean {
        val context = activity ?: return false
        return Settings.Secure.getInt(
            context.contentResolver,
            Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
        ) != 0
    }
}