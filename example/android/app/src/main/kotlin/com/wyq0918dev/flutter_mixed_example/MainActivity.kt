package com.wyq0918dev.flutter_mixed_example

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.wyq0918dev.flutter_mixed.FlutterMixed

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(FlutterMixed.getFlutterView(activity = this))
    }
}
