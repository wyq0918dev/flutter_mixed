#include "include/flutter_mixed/flutter_mixed_plugin_c_api.h"

#include <flutter/plugin_registrar_windows.h>

#include "flutter_mixed_plugin.h"

void FlutterMixedPluginCApiRegisterWithRegistrar(
    FlutterDesktopPluginRegistrarRef registrar) {
  flutter_mixed::FlutterMixedPlugin::RegisterWithRegistrar(
      flutter::PluginRegistrarManager::GetInstance()
          ->GetRegistrar<flutter::PluginRegistrarWindows>(registrar));
}
