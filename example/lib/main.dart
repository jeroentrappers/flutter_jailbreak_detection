import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_jailbreak_detection/flutter_jailbreak_detection.dart';

void main() => runApp(new MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => new _MyAppState();
}

class _MyAppState extends State<MyApp> {
  bool? _jailbroken;
  bool? _developerMode;

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    bool jailbroken;
    bool developerMode;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      jailbroken = await FlutterJailbreakDetection.jailbroken;
      developerMode = await FlutterJailbreakDetection.developerMode;
    } on PlatformException {
      jailbroken = true;
      developerMode = true;
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _jailbroken = jailbroken;
      _developerMode = developerMode;
    });
  }

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Jailbroken plugin example app'),
        ),
        body:  Center(
          child: Column( mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[Text('Jailbroken: ${_jailbroken == null ? "Unknown" : _jailbroken! ? "YES" : "NO"}'),
            Text('Developer mode: ${_developerMode == null ? "Unknown" : _developerMode! ? "YES" : "NO"}')
            ],
          ),
        ),
      ),
    );
  }
}
