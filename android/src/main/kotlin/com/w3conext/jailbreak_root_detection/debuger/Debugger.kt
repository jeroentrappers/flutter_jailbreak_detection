package com.w3conext.jailbreak_root_detection.debuger

import android.content.Context
import android.provider.Settings


object Debugger {
    fun isDebugged(context: Context?): Boolean {
        if (context == null) return false
        val isAdb =
            Settings.Secure.getInt(context.contentResolver, Settings.Global.ADB_ENABLED, 0) == 1
        val isWaitDebugger = Settings.Secure.getInt(
            context.contentResolver,
            Settings.Global.WAIT_FOR_DEBUGGER,
            0
        ) == 1
        return isAdb || isWaitDebugger
    }
}