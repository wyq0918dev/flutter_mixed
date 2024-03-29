import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_mixed/flutter_mixed.dart';
import 'package:flutter_mixed/flutter_mixed_platform_interface.dart';
import 'package:flutter_mixed/flutter_mixed_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterMixedPlatform
    with MockPlatformInterfaceMixin
    implements FlutterMixedPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final FlutterMixedPlatform initialPlatform = FlutterMixedPlatform.instance;

  test('$MethodChannelFlutterMixed is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterMixed>());
  });

  test('getPlatformVersion', () async {
    FlutterMixed flutterMixedPlugin = FlutterMixed();
    MockFlutterMixedPlatform fakePlatform = MockFlutterMixedPlatform();
    FlutterMixedPlatform.instance = fakePlatform;

    expect(await flutterMixedPlugin.getPlatformVersion(), '42');
  });
}
