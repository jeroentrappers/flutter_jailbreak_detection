import Flutter
import UIKit
import IOSSecuritySuite

public class SwiftFlutterJailbreakDetectionPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "flutter_jailbreak_detection", binaryMessenger: registrar.messenger())
    let instance = SwiftFlutterJailbreakDetectionPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        switch call.method {
        case "jailbroken":
            if IOSSecuritySuite.amIJailbroken() || IOSSecuritySuite.amIReverseEngineered() {
                result(true)
            } else {
                result(false)
            }
            break
        case "developerMode":
            if IOSSecuritySuite.amIDebugged() {
                result(true)
            }
            else {
                result(false)
            }            
            break
        default:
            result(FlutterMethodNotImplemented)
        }
  }
}
