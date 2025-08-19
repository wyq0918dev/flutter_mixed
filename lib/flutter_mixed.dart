import 'flutter_mixed_platform_interface.dart';

class FlutterMixed {
  Future<String?> getPlatformVersion() {
    return FlutterMixedPlatform.instance.getPlatformVersion();
  }
}
