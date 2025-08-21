package com.wyq0918dev.flutter_mixed

import android.app.Application
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor


class FlutterMixed private constructor() {


    companion object {
        private val mixed: FlutterMixed = FlutterMixed()

        internal const val ENGINE_ID: String = "flutter_mixed_engine"

        fun initFlutter(application: Application) {
            FlutterEngine(application).let { engine ->
                val entry = DartExecutor.DartEntrypoint.createDefault()
                engine.dartExecutor.executeDartEntrypoint(entry)
                FlutterEngineCache.getInstance().put(ENGINE_ID, engine)
            }
        }

        fun loadFlutter(activity: FragmentActivity, aware: MixedAware): View {
            val adapterAll = FlutterMixedAdapter(fragmentActivity = activity)

            activity.lifecycle.addObserver(object : DefaultLifecycleObserver{
                override fun onCreate(owner: LifecycleOwner) {
                    super.onCreate(owner)
                    aware.onAttachSignals(adapterAll)
                }

                override fun onDestroy(owner: LifecycleOwner) {
                    super.onDestroy(owner)
                    aware.onDetachSignals()
                }
            })

            return ViewPager2(activity).apply {
                isUserInputEnabled = false
                adapter = adapterAll
            }
        }
    }
}