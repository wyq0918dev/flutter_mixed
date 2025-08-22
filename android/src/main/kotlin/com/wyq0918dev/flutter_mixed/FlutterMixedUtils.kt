package com.wyq0918dev.flutter_mixed

import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import com.wyq0918dev.flutter_mixed.FlutterMixed.Companion.ENGINE_ID
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.plugins.FlutterPlugin

internal object FlutterMixedUtils {

    /**
     * 检查引擎是否已初始化
     *
     * @return true 未初始化 false 已初始化
     */
    internal fun checkEngineInitialize(): Boolean {
        return FlutterEngineCache.getInstance().get(ENGINE_ID) == null
    }

    internal fun findFlutterView(view: View?): FlutterView? {
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

    internal fun getPlugin(engine: FlutterEngine?): FlutterMixedPlugin? {
        val name = "com.wyq0918dev.flutter_mixed.FlutterMixedPlugin"
        if (engine != null) {
            try {
                val pluginClass: Class<out FlutterPlugin?> = Class.forName(name) as Class<out FlutterPlugin?>
                return engine.plugins.get(pluginClass) as FlutterMixedPlugin?
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
        return null
    }
}