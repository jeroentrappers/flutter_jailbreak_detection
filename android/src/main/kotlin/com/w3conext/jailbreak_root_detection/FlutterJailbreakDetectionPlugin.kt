package com.w3conext.jailbreak_root_detection

import android.app.Activity
import com.anish.trust_fall.emulator.EmulatorCheck
import com.anish.trust_fall.externalstorage.ExternalStorageCheck
import com.anish.trust_fall.rooted.RootedCheck
import com.scottyab.rootbeer.util.QLog
import com.w3conext.jailbreak_root_detection.debuger.Debugger
import com.w3conext.jailbreak_root_detection.devmode.DevMode
import com.w3conext.jailbreak_root_detection.frida.AntiFridaChecker
import com.w3conext.jailbreak_root_detection.magisk.MagiskChecker
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/** JailbreakRootDetectionPlugin */
class FlutterJailbreakDetectionPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {

    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel

    private var activity: Activity? = null

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "jailbreak_root_detection")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "checkForIssues" -> processCheckIssues(result)
            "isJailBroken" -> processJailBroken(result)
            "isRealDevice" -> result.success(!EmulatorCheck.isEmulator)
            "isOnExternalStorage" -> result.success(
                ExternalStorageCheck.isOnExternalStorage(activity)
            )

            "isDevMode" -> result.success(DevMode.isDevMode(activity))
            "isDebugged" -> result.success(Debugger.isDebugged(activity))
            else -> result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity
    }

    override fun onDetachedFromActivityForConfigChanges() {}

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        activity = binding.activity
    }

    override fun onDetachedFromActivity() {
        activity = null
    }

    private fun processCheckIssues(result: Result) {
        val scope = CoroutineScope(Job() + Dispatchers.Default)
        scope.launch {

            QLog.LOGGING_LEVEL = QLog.NONE;

            val issues = mutableListOf<String>()
            if (RootedCheck.isJailBroken(activity)) {
                issues.add("jailbreak")
            }

            if (AntiFridaChecker.checkFrida()) {
                issues.add("fridaFound")
            }

            if (Debugger.isDebugged(activity)) {
                issues.add("debugged")
            }

            if (DevMode.isDevMode(activity)) {
                issues.add("devMode")
            }

            if (MagiskChecker.isInstalled()) {
                issues.add("magiskFound")
            }

            if (EmulatorCheck.isEmulator) {
                issues.add("notRealDevice")
            }

            if (ExternalStorageCheck.isOnExternalStorage(activity)) {
                issues.add("onExternalStorage")
            }

            result.success(issues)
        }
    }

    private fun processJailBroken(result: Result) {
        val scope = CoroutineScope(Job() + Dispatchers.Default)
        scope.launch {

            QLog.LOGGING_LEVEL = QLog.NONE;

            val isRootBeer = RootedCheck.isJailBroken(activity)
            val isFrida = AntiFridaChecker.checkFrida()
            val isMagisk = MagiskChecker.isInstalled()
            val isRooted = isRootBeer || isFrida || isMagisk

            result.success(isRooted)
        }
    }
}
