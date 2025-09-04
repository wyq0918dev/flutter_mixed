package com.wyq0918dev.flutter_mixed

import android.app.Application
import android.os.Build
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.RenderMode
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.embedding.engine.plugins.lifecycle.FlutterLifecycleAdapter
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result


class FlutterMixedPlugin : FlutterPlugin, MethodChannel.MethodCallHandler, ActivityAware {

    /** 方法通道 */
    private lateinit var mChannel: MethodChannel

    /** Flutter片段 */
    private lateinit var mFlutterFragment: FlutterFragment

    /** HostActivity */
    private lateinit var mHostActivity: FragmentActivity
    private lateinit var mLifecycle: Lifecycle

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        mChannel = MethodChannel(flutterPluginBinding.binaryMessenger, CHANNEL_NAME)
        mChannel.setMethodCallHandler(this)
    }

    override fun onMethodCall(
        call: MethodCall,
        result: Result,
    ) {
        when (call.method) {
            "getPlatformVersion" -> result.success("Android ${Build.VERSION.RELEASE}")
            else -> result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        mChannel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        mLifecycle = FlutterLifecycleAdapter.getActivityLifecycle(binding)
        mLifecycle.addObserver(mObserver)
    }

    override fun onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity()
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        onAttachedToActivity(binding = binding)
    }

    override fun onDetachedFromActivity() {
        mLifecycle.removeObserver(mObserver)
    }

    /**
     * 导出的接口
     */
    private interface IExport {

        /**
         * 初始化Flutter引擎
         *
         * @param application 应用程序上下文
         * @param block 传入引擎对引擎执行其他操作
         */
        fun initFlutter(
            application: Application,
            block: (FlutterEngine) -> Unit,
        )

        /**
         * 加载Flutter视图
         *
         * @param activity 活动上下文
         * @param renderMode 渲染模式
         * @param block 加载完成回调, 分别传入FlutterFragment和View,
         * 直接将View添加到布局中即可显示Flutter, 使用传入的FlutterFragment
         * 完成对Flutter的信号适配, 详细参考Flutter文档.
         */
        fun <T> loadFlutter(
            activity: FragmentActivity,
            renderMode: RenderMode = RenderMode.surface,
            block: (FlutterFragment, View) -> T?,
        ): T?
    }

    /** 导出接口实现 */
    private val mExport: IExport = object : IExport {

        /**
         * 初始化Flutter引擎
         *
         * @param application 应用程序上下文
         */
        override fun initFlutter(
            application: Application,
            block: (FlutterEngine) -> Unit,
        ) {
            if (!checkEngineInitialize()) {
                val engine = initializeEngine(application = application)
                block.invoke(engine)
            }
        }

        /**
         * 加载Flutter视图
         *
         * @param activity 活动上下文
         * @param block 加载完成回调
         */
        override fun <T> loadFlutter(
            activity: FragmentActivity,
            renderMode: RenderMode,
            block: (FlutterFragment, View) -> T?,
        ): T? {
            mHostActivity = activity
            if (checkEngineInitialize()) {
                FlutterFragment.withCachedEngine(ENGINE_ID).let {
                    mFlutterFragment = it.renderMode(renderMode).build()
                }
            } else {
                error(
                    message = "FlutterMixed 未初始化",
                )
            }
            return block.invoke(mFlutterFragment, mFlutterContainer)
        }
    }

    private val mObserver: DefaultLifecycleObserver = object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner = owner)
        }

        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner = owner)
        }

        override fun onResume(owner: LifecycleOwner) {
            super.onResume(owner = owner)
        }

        override fun onPause(owner: LifecycleOwner) {
            super.onPause(owner = owner)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner = owner)
        }
    }

    /**
     * 初始化Flutter引擎
     *
     */
    private fun initializeEngine(application: Application): FlutterEngine {
        return FlutterEngine(application).let { engine ->
            val entry = DartExecutor.DartEntrypoint.createDefault()
            engine.dartExecutor.executeDartEntrypoint(entry)
            FlutterEngineCache.getInstance().put(ENGINE_ID, engine)
            return@let engine
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

    /**
     * 检查引擎是否已初始化
     *
     * @return true 已初始化 false 未初始化
     */
    private fun checkEngineInitialize(): Boolean {
        return FlutterEngineCache.getInstance().get(ENGINE_ID) != null
    }

    /** 伴生对象 */
    companion object : IExport by FlutterMixedPlugin().mExport {

        /** 引擎ID */
        private const val ENGINE_ID: String = "flutter_mixed_engine"

        /** 通道名称 */
        private const val CHANNEL_NAME: String = "flutter_mixed"
    }
}