package com.wyq0918dev.flutter_mixed_example

import android.app.Application
import com.google.android.material.color.DynamicColors
import com.wyq0918dev.flutter_mixed.FlutterMixed

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // 初始化 flutter_mixed
        FlutterMixed.initFlutter(this@MainApplication)
        // 启用动态取色
        DynamicColors.applyToActivitiesIfAvailable(this@MainApplication)
    }
}