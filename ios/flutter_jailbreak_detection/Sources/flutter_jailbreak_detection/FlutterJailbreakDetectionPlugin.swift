import Flutter
import UIKit
import IOSSecuritySuite

public class FlutterJailbreakDetectionPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "flutter_jailbreak_detection", binaryMessenger: registrar.messenger())
    let instance = FlutterJailbreakDetectionPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    switch call.method {
    case "jailbroken":
      result(IOSSecuritySuite.amIJailbroken())
      break
    case "developerMode":
      result(IOSSecuritySuite.amIRunInEmulator())
      break
    default:
      result(FlutterMethodNotImplemented)
    }
  }
}
