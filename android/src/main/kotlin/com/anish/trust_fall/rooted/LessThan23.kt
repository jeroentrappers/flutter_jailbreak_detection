package com.anish.trust_fall.rooted

import java.io.File

class LessThan23 : CheckApiVersion {

    override fun checkRooted(): Boolean {
        return canExecuteCommand("/system/xbin/which su") || isSuperuserPresent
    }

    companion object {
        // executes a command on the system
        private fun canExecuteCommand(command: String): Boolean {
            val executeResult: Boolean = try {
                val process = Runtime.getRuntime().exec(command)
                process.waitFor() == 0
            } catch (e: Exception) {
                false
            }
            return executeResult
        }

        // Check if /system/app/Superuser.apk is present
        private val isSuperuserPresent: Boolean
            get() {
                // Check if /system/app/Superuser.apk is present
                val paths = arrayOf(
                    "/system/app/Superuser.apk",
                    "/sbin/su",
                    "/system/bin/su",
                    "/system/xbin/su",
                    "/data/local/xbin/su",
                    "/data/local/bin/su",
                    "/system/sd/xbin/su",
                    "/system/bin/failsafe/su",
                    "/data/local/su"
                )
                for (path in paths) {
                    if (File(path).exists()) {
                        return true
                    }
                }
                return false
            }
    }
}