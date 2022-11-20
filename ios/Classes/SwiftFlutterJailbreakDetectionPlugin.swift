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
            
            let check2 = IOSSecuritySuite.amIJailbroken()
            result(check2)
            break
        case "developerMode":
            result(IOSSecuritySuite.amIRunInEmulator())
            break
        default:
            result(FlutterMethodNotImplemented)
        }
  }
}
