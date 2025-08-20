package com.wyq0918dev.flutter_mixed

import android.app.Application
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class FlutterMixed private constructor() {

    companion object {

        const val ENGINE_ID: String = "flutter_mixed_engine"

        fun initFlutterMixed(application: Application, mixed: MixedApplication) {
            FlutterEngine(application).let { engine ->
                val entry = DartExecutor.DartEntrypoint.createDefault()
                engine.dartExecutor.executeDartEntrypoint(entry)
                FlutterEngineCache.getInstance().put(ENGINE_ID, engine)
            }
        }

        fun getFlutterView(activity: FragmentActivity): View {
            return ViewPager2(activity).apply {
                isUserInputEnabled = false
                adapter = FlutterMixedAdapter(fragmentActivity = activity)
            }
        }
    }
}