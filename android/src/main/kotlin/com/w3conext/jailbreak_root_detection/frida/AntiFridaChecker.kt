package com.w3conext.jailbreak_root_detection.frida

import android.content.Context
import android.util.Log
import com.w3conext.jailbreak_root_detection.rooted.SuperUserUtility
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class AntiFridaChecker(
    private val context: Context?
) {

    fun isDetected(): Boolean {
        return tryRoot()
                || checkModuleDetected()
                || checkPortDetected()
                || checkServerProcessDetected()
                || checkSignatureDetected()
    }

    fun tryRoot(): Boolean {
        val isRooted = SuperUserUtility.tryRoot(context?.packageCodePath ?: "")

        Log.i(TAG, "Rooted: $isRooted")

        return isRooted
    }

    fun checkModuleDetected(): Boolean {
        val result = SuperUserUtility.execRootCmd("pmap ${android.os.Process.myPid()}")
        val moduleDetected = AntiFridaBlocklist.checkContain(result)

        val detected = AntiFridaUtility.checkFridaByProcMaps() || moduleDetected

        Log.i(TAG, "Check module detected: $detected")

        return detected
    }

    // frida default port 27042
    fun checkPortDetected(): Boolean {
        val detected = AntiFridaUtility.checkFridaByPort(27042)

        Log.i(TAG, "Check port detected: $detected")

        return detected
    }

    fun checkServerProcessDetected(): Boolean {
        val result = SuperUserUtility.execRootCmd("ps -ef")
        val detected = result.contains("frida-server")

        Log.i(TAG, "Check frida-server process detected: $detected")

        return detected
    }

    fun checkSignatureDetected(): Boolean {
        val detected = AntiFridaUtility.scanModulesForSignatureDetected()

        Log.i(TAG, "Check signature detected: $detected")

        return detected
    }

    companion object {
        private const val TAG = "AntiFridaChecker"


        fun checkFrida(): Boolean {
            var isFridaRunning = false

            // check for Frida-related files or directories in the file system
            val fridaGadget = File("/data/local/tmp/frida-gadget")
            if (fridaGadget.exists()) {
                Log.d(TAG, "Frida-gadget found in /data/local/tmp")
                isFridaRunning = true
            }

            // check for Frida-related processes
            try {
                val process = Runtime.getRuntime().exec("ps")
                val bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
                var line: String? = bufferedReader.readLine()
                while (line != null) {
                    if (line.contains("frida-server")) {
                        Log.d(TAG, "Frida-server process found")
                        isFridaRunning = true
                        break
                    }
                    line = bufferedReader.readLine()
                }
            } catch (e: Exception) {
                Log.d(TAG, "Error checking for Frida-related processes")
            }

            // check for Frida-related libraries
            try {
                val process = Runtime.getRuntime().exec("lsof")
                val bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
                var line: String? = bufferedReader.readLine()
                while (line != null) {
                    if (line.contains("libfrida-gadget.so")) {
                        Log.d(TAG, "Frida-gadget library found")
                        isFridaRunning = true
                        break
                    }
                    line = bufferedReader.readLine()
                }
            } catch (e: Exception) {
                Log.d(TAG, "Error checking for Frida-related libraries")
            }

            Log.i(TAG, "Frida running: $isFridaRunning")

            return isFridaRunning
        }
    }
}