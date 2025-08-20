package com.wyq0918dev.flutter_mixed

import android.content.Intent

interface MixedSignals {
    fun onPostResume()
    fun onNewIntent(intent: Intent)
    fun onBackPressed()
    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    )
    fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    )
    fun onUserLeaveHint()
    fun onTrimMemory(level: Int)
}