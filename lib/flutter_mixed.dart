import 'package:flutter/widgets.dart';

class FlutterMixed {
  const FlutterMixed._();

  static void registerWith() {}

  static TransitionBuilder get builder {
    return (_, child) => Container(child: child);
  }
}
