package appmire.be.flutterjailbreakdetection

import android.app.Activity
import android.provider.Settings
import com.scottyab.rootbeer.RootBeer
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

class FlutterJailbreakDetectionPlugin(private val context: Activity) : MethodCallHandler {
    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar): Unit {
            val channel = MethodChannel(registrar.messenger(), "flutter_jailbreak_detection")
            channel.setMethodCallHandler(FlutterJailbreakDetectionPlugin(registrar.activity()))
        }
    }

    @android.annotation.TargetApi(17)
    fun isDevMode(): Boolean {
        return if (Integer.valueOf(android.os.Build.VERSION.SDK) == 16) {
            Settings.Secure.getInt(context.getContentResolver(),
                    Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0
        } else if (Integer.valueOf(android.os.Build.VERSION.SDK) >= 17) {
            Settings.Secure.getInt(context.getContentResolver(),
                    Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0
        } else
            false
    }

    override fun onMethodCall(call: MethodCall, result: Result): Unit {
        if (call.method.equals("jailbroken")) {
            val rootBeer = RootBeer(context)
            result.success(rootBeer.isRooted)
        }else if (call.method.equals("developerMode")){
            result.success(isDevMode())
        } else {
            result.notImplemented()
        }
    }
}
