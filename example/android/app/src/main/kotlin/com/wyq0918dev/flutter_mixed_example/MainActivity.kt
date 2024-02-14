package com.wyq0918dev.flutter_mixed_example

import android.os.Bundle
import com.wyq0918dev.flutter_mixed.FlutterMixedPlugin

class MainActivity: FlutterMixedPlugin() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(flutter)
    }
}
