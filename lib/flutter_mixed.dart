import 'src/flutter_mixed_platform_interface.dart';

class FlutterMixed {
  const FlutterMixed._();

  static void registerWith() {}

  static Future<String?> getPlatformVersion() {
    return FlutterMixedPlatform.instance.getPlatformVersion();
  }
}
