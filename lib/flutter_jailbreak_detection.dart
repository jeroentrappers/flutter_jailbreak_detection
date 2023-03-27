import 'dart:async';

import 'package:flutter/services.dart';

class FlutterJailbreakDetection {
  static const MethodChannel _channel =
      const MethodChannel('flutter_jailbreak_detection');

  static Future<bool> jailbroken({bool setLogging = true}) async {
    bool? jailbroken = await _channel.invokeMethod<bool>(
        'jailbroken', <String, dynamic>{"setLogging": setLogging});

    return jailbroken ?? true;
  }

  static Future<bool> get developerMode async {
    bool? developerMode = await _channel.invokeMethod<bool>('developerMode');
    return developerMode ?? true;
  }
}
