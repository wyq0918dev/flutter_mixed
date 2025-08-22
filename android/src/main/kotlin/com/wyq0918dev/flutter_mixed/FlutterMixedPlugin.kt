package com.wyq0918dev.flutter_mixed

import android.app.Application
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

class FlutterMixedPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {

    private lateinit var mChannel: MethodChannel
    private lateinit var mFlutterFragment: FlutterFragment
    private lateinit var mHostActivity: FragmentActivity

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        mChannel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_mixed")
        mChannel.setMethodCallHandler(this)
    }

    override fun onMethodCall(
        call: MethodCall, result: Result
    ) {
        when (call.method) {
            "getPlatformVersion" -> result.success("Android ${android.os.Build.VERSION.RELEASE}")
            else -> result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        mChannel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {

    }

    override fun onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity()
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        onAttachedToActivity(binding = binding)
    }

    override fun onDetachedFromActivity() {

    }

    private interface Export {
        fun initFlutter(application: Application)
        fun loadFlutter(
            activity: FragmentActivity,
            block: (FlutterFragment, View) -> Unit,
        )
    }


    private fun initializeEngine(application: Application) {
        FlutterEngine(application).let { engine ->
            val entry = DartExecutor.DartEntrypoint.createDefault()
            engine.dartExecutor.executeDartEntrypoint(entry)
            FlutterEngineCache.getInstance().put(ENGINE_ID, engine)
        }
    }

    private fun initFlutter(application: Application) {
        if (checkEngineInitialize()) {
            initializeEngine(application = application)
        }
    }

    private val mFlutterAdapter: FragmentStateAdapter by lazy {
        return@lazy object : FragmentStateAdapter(mHostActivity) {
            override fun getItemCount(): Int = 1
            override fun createFragment(position: Int): Fragment = mFlutterFragment
        }
    }

    private val mFlutterContainer: ViewPager2 by lazy {
        return@lazy ViewPager2(mHostActivity).apply {
            isUserInputEnabled = false
            adapter = mFlutterAdapter
        }
    }

    private fun loadFlutter(
        activity: FragmentActivity,
        block: (FlutterFragment, View) -> Unit,
    ) {
        mHostActivity = activity
        mFlutterFragment = buildFlutter()




        block.invoke(mFlutterFragment, mFlutterContainer)
    }


    /**
     * 检查引擎是否已初始化
     *
     * @return true 未初始化 false 已初始化
     */
    private fun checkEngineInitialize(): Boolean {
        return FlutterEngineCache.getInstance().get(ENGINE_ID) == null
    }


    private fun getPlugin(engine: FlutterEngine?): FlutterMixedPlugin? {
        val name = "com.wyq0918dev.flutter_mixed.FlutterMixedPlugin"
        if (engine != null) {
            try {
                val pluginClass: Class<out FlutterPlugin?> =
                    Class.forName(name) as Class<out FlutterPlugin?>
                return engine.plugins.get(pluginClass) as FlutterMixedPlugin?
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
        return null
    }



    private fun findFlutterView(view: View?): FlutterView? {
        when (view) {
            is FlutterView -> return view
            is ViewGroup -> for (index in 0 until view.size) {
                return findFlutterView(
                    view = view.getChildAt(index),
                )
            }
        }
        return null
    }

    private fun buildFlutter(): FlutterFragment {
        if (checkEngineInitialize()) {
            return FlutterFragment.withCachedEngine(ENGINE_ID).build()
        } else {
            error("")
        }
    }


    companion object : Export {
        private val plugin: FlutterMixedPlugin = FlutterMixedPlugin()

        /** 引擎ID */
        private const val ENGINE_ID: String = "flutter_mixed_engine"

        override fun initFlutter(
            application: Application,
        ) = plugin.initFlutter(
            application = application,
        )

        override fun loadFlutter(
            activity: FragmentActivity,
            block: (FlutterFragment, View) -> Unit,
        ) = plugin.loadFlutter(
            activity = activity,
            block = block,
        )
    }
}