import 'package:flutter_web_plugins/flutter_web_plugins.dart';
import 'package:web/web.dart' as web;

import 'flutter_mixed_platform_interface.dart';

class FlutterMixedWeb extends FlutterMixedPlatform {
  FlutterMixedWeb();

  static void registerWith(Registrar registrar) {
    FlutterMixedPlatform.instance = FlutterMixedWeb();
  }

  @override
  Future<String?> getPlatformVersion() async {
    final version = web.window.navigator.userAgent;
    return version;
  }
}
