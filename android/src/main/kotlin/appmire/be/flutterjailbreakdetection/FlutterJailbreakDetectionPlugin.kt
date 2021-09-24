package appmire.be.flutterjailbreakdetection

import android.content.Context
import android.provider.Settings
import com.scottyab.rootbeer.RootBeer
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.PluginRegistry.Registrar
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.plugin.common.BinaryMessenger

fun registerPlugin(messenger: BinaryMessenger, context: Context): Unit {
    val channel = MethodChannel(messenger, "flutter_jailbreak_detection")
    val plugin = FlutterJailbreakDetectionPlugin()
    plugin.context = context
    channel.setMethodCallHandler(plugin)
}

class FlutterJailbreakDetectionPlugin : FlutterPlugin, MethodCallHandler {
    lateinit var context: Context

    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar): Unit {
            registerPlugin(registrar.messenger(), registrar.context())
        }
    }

    override fun onAttachedToEngine(binding: FlutterPluginBinding) {
        registerPlugin(binding.getBinaryMessenger(), binding.getApplicationContext())
    }

    override fun onDetachedFromEngine(binding: FlutterPluginBinding) {}

    @android.annotation.TargetApi(17)
    fun isDevMode(): Boolean {
        return when {
            Integer.valueOf(android.os.Build.VERSION.SDK) == 16 -> {
                Settings.Secure.getInt(context.contentResolver,
                    Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0
            }
            Integer.valueOf(android.os.Build.VERSION.SDK) >= 17 -> {
                Settings.Secure.getInt(context.contentResolver,
                    Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0
            }
            else -> false
        }
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
