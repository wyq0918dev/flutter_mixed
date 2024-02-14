package com.wyq0918dev.flutter_mixed

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import io.flutter.embedding.android.FlutterEngineConfigurator
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

open class MixedActivity : FragmentActivity(), FlutterPlugin, MethodCallHandler,
    FlutterEngineConfigurator {

    private var mFlutterFragment: FlutterFragment? = null
    private lateinit var mMethodChannel: MethodChannel
    private lateinit var mFragmentContainerView: FragmentContainerView

    /** 获取显示 FlutterFragment 的容器控件 */
    open val flutter: View? get() = mFragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        val flutterEngine = FlutterEngine(this@MixedActivity)
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )
        FlutterEngineCache.getInstance().put(engineId, flutterEngine)
        super.onCreate(savedInstanceState)
        mFlutterFragment = supportFragmentManager.findFragmentByTag(
            tagFlutterFragment
        ) as FlutterFragment?
        mFragmentContainerView = FragmentContainerView(
            context = this@MixedActivity
        ).apply {
            id = R.id.flutter_container
        }
        val flutterFragment = FlutterFragment.withCachedEngine(engineId)
            .destroyEngineWithFragment(false)
            .shouldAttachEngineToActivity(true)
            .build<FlutterFragment>()
        if (mFlutterFragment == null) {
            if (!flutterFragment.isAdded && supportFragmentManager.findFragmentByTag(
                    tagFlutterFragment
                ) == null
            ) {
                supportFragmentManager.commit(
                    allowStateLoss = false,
                    body = {
                        mFlutterFragment = flutterFragment
                        add(
                            mFragmentContainerView.id, flutterFragment, tagFlutterFragment
                        )
                    }
                )
            }
        }
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {

        Toast.makeText(
            this@MixedActivity, flutterEngine.plugins.has(
                this@MixedActivity.javaClass
            ).toString(), Toast.LENGTH_SHORT
        ).show()

    }

    override fun cleanUpFlutterEngine(flutterEngine: FlutterEngine) {

    }

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        mMethodChannel = MethodChannel(flutterPluginBinding.binaryMessenger, channelName)
        mMethodChannel.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "getPlatformVersion" -> result.success("Android ${android.os.Build.VERSION.RELEASE}")
            else -> result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        mMethodChannel.setMethodCallHandler(null)
    }

    override fun onPostResume() {
        super.onPostResume()
        try {
            mFlutterFragment?.onPostResume()
        } catch (e: Exception) {
            Log.e(tag, "onPostResume", e)
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onNewIntent(intent: Intent) {
        try {
            mFlutterFragment?.onNewIntent(intent)
        } catch (e: Exception) {
            Log.e(tag, "onNewIntent", e)
        }
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        try {
            mFlutterFragment?.onBackPressed()
        } catch (e: Exception) {
            Log.e(tag, "onBackPressed", e)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        try {
            mFlutterFragment?.onRequestPermissionsResult(requestCode, permissions, grantResults)
        } catch (e: Exception) {
            Log.e(tag, "onRequestPermissionsResult", e)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            mFlutterFragment?.onActivityResult(requestCode, resultCode, data)
        } catch (e: Exception) {
            Log.e(tag, "onActivityResult", e)
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onUserLeaveHint() {
        try {
            mFlutterFragment?.onUserLeaveHint()
        } catch (e: Exception) {
            Log.e(tag, "onUserLeaveHint", e)
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        try {
            mFlutterFragment?.onTrimMemory(level)
        } catch (e: Exception) {
            Log.e(tag, "onTrimMemory", e)
        }
    }

    companion object {
        const val tagFlutterFragment = "flutter_fragment"
        const val channelName: String = "flutter_mixed"
        const val engineId: String = "engine_mixed"
        const val tag: String = "FlutterMixedPlugin"
    }
}