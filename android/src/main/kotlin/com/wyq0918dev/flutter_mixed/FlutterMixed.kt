package com.wyq0918dev.flutter_mixed

import android.app.Application
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor


class FlutterMixed private constructor() {

    private fun initializeEngine(application: Application) {
        FlutterEngine(application).let { engine ->
            val entry = DartExecutor.DartEntrypoint.createDefault()
            engine.dartExecutor.executeDartEntrypoint(entry)
            FlutterEngineCache.getInstance().put(ENGINE_ID, engine)
        }
    }

    private fun init(application: Application) {
        if (FlutterMixedUtils.checkEngineInitialize()) {
            initializeEngine(application = application)
        }
    }


    private fun load(
        activity: FragmentActivity,
        aware: MixedAware,
        providedContainerLayout: FrameLayout?,
    ): FrameLayout {
        val adapterAll = FlutterMixedAdapter(fragmentActivity = activity)

        activity.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                super.onCreate(owner)
                aware.onAttachSignals(adapterAll)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                aware.onDetachSignals()
            }
        })

        val fv = ViewPager2(activity).apply {
            isUserInputEnabled = false
            adapter = adapterAll
        }

        val fl = providedContainerLayout ?: FrameLayout(activity)

        return fl.apply {
            addView(fv)
        }
    }

    private interface Export {
        fun initFlutter(application: Application)
        fun loadFlutter(
            activity: FragmentActivity,
            aware: MixedAware,
            providedContainerLayout: FrameLayout? = null,
        ): FrameLayout
    }

    companion object : Export {
        private val mixed: FlutterMixed = FlutterMixed()

        /** 引擎ID */
        internal const val ENGINE_ID: String = "flutter_mixed_engine"

        override fun initFlutter(
            application: Application,
        ) = mixed.init(
            application = application,
        )

        override fun loadFlutter(
            activity: FragmentActivity,
            aware: MixedAware,
            providedContainerLayout: FrameLayout?,
        ): FrameLayout = mixed.load(
            activity = activity,
            aware = aware,
            providedContainerLayout = providedContainerLayout,
        )
    }
}