package com.wyq0918dev.flutter_mixed_example

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.wyq0918dev.flutter_mixed.FlutterMixed
import com.wyq0918dev.flutter_mixed.MixedAware
import com.wyq0918dev.flutter_mixed.MixedSignals


class MainActivity : AppCompatActivity(), MixedAware {

    private var mMixedSignals: MixedSignals? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 启用边倒边
        enableEdgeToEdge()
        // 承载Flutter的控件
        setContentView(FlutterMixed.getFlutter(activity = this))
    }

    override fun onAttachedSignals(signals: MixedSignals) {
        mMixedSignals = signals
    }

    override fun onDetachedSignals() {
        mMixedSignals = null
    }

    override fun onPostResume() {
        super.onPostResume()
        mMixedSignals?.onPostResume()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        mMixedSignals?.onNewIntent(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mMixedSignals?.onBackPressed()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mMixedSignals?.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        mMixedSignals?.onActivityResult(
            requestCode,
            resultCode,
            data
        )
    }

    override fun onUserLeaveHint() {
        mMixedSignals?.onUserLeaveHint()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        mMixedSignals?.onTrimMemory(level)
    }
}
