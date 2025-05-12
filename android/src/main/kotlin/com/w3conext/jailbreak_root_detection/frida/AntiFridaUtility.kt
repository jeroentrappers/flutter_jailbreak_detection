package com.w3conext.jailbreak_root_detection.frida

import android.util.Log
import java.io.File
import java.lang.Exception

object AntiFridaUtility {

    private const val TAG = "AntiFridaUtility"

    fun checkFridaByProcMaps(): Boolean {
        val procMapJvm = readProcMaps()
        val procMapSysCall = AntiFridaNativeLoader.nativeReadProcMaps()
        val procMapCustomSysCall = AntiFridaNativeLoader.nativeReadProcMaps(true)

        return AntiFridaBlocklist.check(procMapJvm)
                || AntiFridaBlocklist.check(procMapSysCall)
                || AntiFridaBlocklist.check(procMapCustomSysCall)
    }

    fun checkFridaByPort(port: Int) = AntiFridaNativeLoader.checkFridaByPort(port)

    fun scanModulesForSignatureDetected(): Boolean {
        val blockList = listOf("frida:rpc", "LIBFRIDA")
        var detected = 0
        blockList.forEach {
            if (AntiFridaNativeLoader.scanModulesForSignature(it, true)) detected++
            if (AntiFridaNativeLoader.scanModulesForSignature(it, false)) detected++
        }
        return detected > 0
    }

    fun checkBeingDebugged(useCustomizedSyscall: Boolean): Boolean =
        AntiFridaNativeLoader.checkBeingDebugged(useCustomizedSyscall)

    private fun readProcMaps(): String? {
        try {
            val mapsFile = File("/proc/self/maps")
            return mapsFile.readText()
        } catch (e: Exception) {
            Log.e(TAG, e.stackTraceToString())
        }
        return null
    }

}