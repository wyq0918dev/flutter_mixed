import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_mixed/flutter_mixed.dart';
import 'package:freefeos/freefeos.dart';
import 'package:multi_builder/multi_builder.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  Future<void> initPlatformState() async {
    String platformVersion;
    try {
      platformVersion =
          await FlutterMixed.getPlatformVersion() ?? 'Unknown platform version';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }
    if (!mounted) return;
    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      builder: [FreeFEOS.builder, FlutterMixed.builder].toBuilder,
      home: Scaffold(
        appBar: AppBar(title: const Text('Mixed'), centerTitle: true),
        body: Center(child: Text('Running on: $_platformVersion\n')),
      ),
    );
  }
}
