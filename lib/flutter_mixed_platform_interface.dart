import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'flutter_mixed_method_channel.dart';

abstract class FlutterMixedPlatform extends PlatformInterface {
  FlutterMixedPlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterMixedPlatform _instance = MethodChannelFlutterMixed();

  static FlutterMixedPlatform get instance => _instance;

  static set instance(FlutterMixedPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
