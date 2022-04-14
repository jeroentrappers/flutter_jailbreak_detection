import Flutter
import UIKit
import DTTJailbreakDetection

public class SwiftFlutterJailbreakDetectionPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "flutter_jailbreak_detection", binaryMessenger: registrar.messenger())
    let instance = SwiftFlutterJailbreakDetectionPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        switch call.method {
        case "jailbroken":
            
            var isiOSAppOnMac = false
            if #available(iOS 14.0, *) {
                isiOSAppOnMac = ProcessInfo.processInfo.isiOSAppOnMac
            }
            let isJailBroken = DTTJailbreakDetection.isJailbroken() && !isiOSAppOnMac
        
            result(isJailBroken)
            break
        case "developerMode":
            result(false)
            break
        default:
            result(FlutterMethodNotImplemented)
        }
  }
}
