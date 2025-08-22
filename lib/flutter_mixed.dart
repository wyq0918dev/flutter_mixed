import 'package:flutter/widgets.dart';

import 'src/platform_interface.dart';

class FlutterMixed {
  const FlutterMixed._();

  static void registerWith() {}

  static Future<String?> getPlatformVersion() {
    return FlutterMixedPlatform.instance.getPlatformVersion();
  }

  static TransitionBuilder get builder {
    return (_, child) => Container(child: child);
  }
}
