package com.wyq0918dev.flutter_mixed_example

import android.app.Application
import com.google.android.material.color.DynamicColors

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this@MainApp)
    }
}