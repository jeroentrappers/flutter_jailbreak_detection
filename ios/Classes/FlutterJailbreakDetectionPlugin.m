#import "FlutterJailbreakDetectionPlugin.h"
#import <flutter_jailbreak_detection/flutter_jailbreak_detection-Swift.h>

@implementation FlutterJailbreakDetectionPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterJailbreakDetectionPlugin registerWithRegistrar:registrar];
}
@end
