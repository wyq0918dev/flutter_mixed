package com.wyq0918dev.flutter_mixed

import android.os.Bundle
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.RenderMode
import io.flutter.embedding.android.TransparencyMode

internal class FlutterMixedFragment private constructor() : FlutterFragment() {

    companion object {

        internal fun build(): FlutterMixedFragment {
            try {
                val mFragment: Class<FlutterMixedFragment> = FlutterMixedFragment::class.java
                return mFragment.getDeclaredConstructor().newInstance().let { mixed ->
                    Bundle().apply {
                        putString(ARG_CACHED_ENGINE_ID, FlutterMixed.ENGINE_ID)
                        putBoolean(ARG_DESTROY_ENGINE_WITH_FRAGMENT, false)
                        putString(ARG_FLUTTERVIEW_RENDER_MODE, RenderMode.surface.name)
                        putString(ARG_FLUTTERVIEW_TRANSPARENCY_MODE, TransparencyMode.opaque.name)
                        putBoolean(ARG_SHOULD_ATTACH_ENGINE_TO_ACTIVITY, true)
                        mixed.setArguments(this@apply)
                    }
                    return@let mixed
                }
            } catch (e: Exception) {
                error(message = e.toString())
            }
        }
    }
}