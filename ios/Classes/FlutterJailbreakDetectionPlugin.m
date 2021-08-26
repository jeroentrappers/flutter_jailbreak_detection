#import "FlutterJailbreakDetectionPlugin.h"
#if __has_include(<flutter_jailbreak_detection/flutter_jailbreak_detection-Swift.h>)
    #import <flutter_jailbreak_detection/flutter_jailbreak_detection-Swift.h>
#else
    // Support project import fallback if the generated compatibility header
    // is not copied when this plugin is created as a library.
    // https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
    #import "flutter_jailbreak_detection-Swift.h"
#endif

@implementation FlutterJailbreakDetectionPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterJailbreakDetectionPlugin registerWithRegistrar:registrar];
}
@end
