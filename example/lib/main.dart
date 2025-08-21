import 'package:flutter/material.dart';
import 'package:flutter_mixed/flutter_mixed.dart';
import 'package:freefeos/freefeos.dart';
import 'package:multi_builder/multi_builder.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      builder: [FreeFEOS.builder, FlutterMixed.builder].toBuilder,
      home: Scaffold(
        appBar: AppBar(title: const Text('FlutterMixed example')),
        body: Center(child: Text('Hello World')),
      ),
    );
  }
}
