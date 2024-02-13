import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_mixed_platform_interface.dart';

class MethodChannelFlutterMixed extends FlutterMixedPlatform {

  @visibleForTesting
  final methodChannel = const MethodChannel('flutter_mixed');

  @override
  Future<String?> getPlatformVersion() async {
    final version =
        await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
