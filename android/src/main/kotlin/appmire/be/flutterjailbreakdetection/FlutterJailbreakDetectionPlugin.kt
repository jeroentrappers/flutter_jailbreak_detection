package appmire.be.flutterjailbreakdetection

import android.content.Context
import android.provider.Settings
import com.scottyab.rootbeer.RootBeer

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import android.os.Handler
import android.os.Looper
import java.lang.Thread

class FlutterJailbreakDetectionPlugin : FlutterPlugin, MethodCallHandler {
    private lateinit var context: Context
    private lateinit var channel: MethodChannel


    override fun onAttachedToEngine(binding: FlutterPluginBinding) {
        channel = MethodChannel(binding.binaryMessenger, "flutter_jailbreak_detection")
        context = binding.applicationContext
        channel.setMethodCallHandler(this)
    }


    override fun onDetachedFromEngine(binding: FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }


    private fun isDevMode(): Boolean {
        return Settings.Secure.getInt(
            context.contentResolver,
            Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
        ) != 0
    }


    override fun onMethodCall(call: MethodCall, result: Result): Unit {
        if (call.method.equals("jailbroken")) {
            Thread {
            val isRooted = try {
                RootBeer(context).isRooted
                } catch (e: Exception) {
                    false
                }    
               Handler(Looper.getMainLooper()).post {
                   result.success(isRooted)
                }
            }.start()
        } else if (call.method.equals("developerMode")) {
            result.success(isDevMode())
        } else {
            result.notImplemented()
        }
    }


}
