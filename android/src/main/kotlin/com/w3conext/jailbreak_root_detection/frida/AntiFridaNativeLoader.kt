package com.w3conext.jailbreak_root_detection.frida

import androidx.annotation.Keep

@Keep
object AntiFridaNativeLoader {

    external fun checkFridaByPort(port: Int): Boolean
    external fun checkBeingDebugged(useCustomizedSyscall: Boolean = false): Boolean
    external fun nativeReadProcMaps(useCustomizedSyscall: Boolean = false): String?
    external fun scanModulesForSignature(
        signature: String,
        useCustomizedSysCalls: Boolean = false
    ): Boolean

    init {
        try {
            System.loadLibrary("antifrida")
        } catch (e: Exception) {
            print(e)
        }
    }

}