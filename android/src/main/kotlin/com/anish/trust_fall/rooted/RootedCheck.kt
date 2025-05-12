package com.anish.trust_fall.rooted

import android.content.Context
import android.os.Build
import com.scottyab.rootbeer.RootBeer

object RootedCheck {
    private const val ONEPLUS = "oneplus"
    private const val MOTO = "moto"
    private const val XIAOMI = "Xiaomi"

    /**
     * Checks if the device is rooted.
     *
     * @return `true` if the device is rooted, `false` otherwise.
     */
    fun isJailBroken(context: Context?): Boolean {
        val check: CheckApiVersion = if (Build.VERSION.SDK_INT >= 23) {
            GreaterThan23()
        } else {
            LessThan23()
        }
        return check.checkRooted() || rootBeerCheck(context)
    }

    @Suppress("DEPRECATION")
    private fun rootBeerCheck(context: Context?): Boolean {
        val rootBeer = RootBeer(context)
        val rv: Boolean =
            if (Build.BRAND.contains(ONEPLUS) || Build.BRAND.contains(MOTO) || Build.BRAND.contains(
                    XIAOMI
                )
            ) {
                rootBeer.isRootedWithoutBusyBoxCheck
            } else {
                rootBeer.isRooted
            }
        return rv
    }
}