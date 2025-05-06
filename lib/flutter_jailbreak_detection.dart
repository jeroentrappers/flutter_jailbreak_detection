import 'dart:async';
import 'dart:convert';

import 'package:flutter/services.dart';

class FlutterJailbreakDetection {
  static const MethodChannel _channel =
      const MethodChannel('flutter_jailbreak_detection');

  static Future<bool> get jailbroken async {
    bool? jailbroken = await _channel.invokeMethod<bool>('jailbroken');
    return jailbroken ?? true;
  }

  static Future<JailbreakDetails> get jailbrokenDetails async {
    String? details = await _channel.invokeMethod<String>('jailbrokenDetails');
    return JailbreakDetails(jsonDecode(details ?? "{}"));
  }

  static Future<bool> get developerMode async {
    bool? developerMode = await _channel.invokeMethod<bool>('developerMode');
    return developerMode ?? true;
  }
}

class JailbreakDetails {
  bool get isJailbroken => _details['status'] ?? false;
  String get failMessage => _details['message'] ?? '';

  final Map<String, dynamic> _details;

  JailbreakDetails(this._details);

  @override
  String toString() => _details.toString();
}
