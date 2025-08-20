package com.wyq0918dev.flutter_mixed

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class FlutterMixed private constructor() {

    companion object {

        const val ENGINE_ID: String = "flutter_mixed_engine"





        fun getFlutterView(activity: FragmentActivity): View {
            FlutterEngine(activity).let { engine ->
                val entry = DartExecutor.DartEntrypoint.createDefault()
                engine.dartExecutor.executeDartEntrypoint(entry)
                FlutterEngineCache.getInstance().put(ENGINE_ID, engine)
            }
            return ViewPager2(activity).apply {
                isUserInputEnabled = false
                adapter = object : FragmentStateAdapter(activity) {
                    override fun getItemCount(): Int = 1
                    override fun createFragment(position: Int): Fragment {
                        return FlutterMixedFragment.build()
                    }
                }
            }
        }
    }
}