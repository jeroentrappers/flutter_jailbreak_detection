package appmire.be.flutter_jailbreak_detection

import android.content.Context
import android.provider.Settings

import com.scottyab.rootbeer.RootBeer

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

class FlutterJailbreakDetectionPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel: MethodChannel

  private lateinit var context: Context

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_jailbreak_detection")
    context = flutterPluginBinding.applicationContext
    channel.setMethodCallHandler(this)
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  override fun onMethodCall(call: MethodCall, result: Result): Unit {
    when (call.method) {
      "jailbroken" -> {
        val rootBeer = RootBeer(context)
        result.success(rootBeer.isRooted)
      }
      "developerMode" -> {
        result.success(isDevMode())
      }
      else -> {
        result.notImplemented()
      }
    }
  }

  private fun isDevMode(): Boolean {
    return Settings.Global.getInt(
      context.contentResolver,
      Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
      0
    ) != 0
  }
}
