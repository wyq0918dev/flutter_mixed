package com.wyq0918dev.flutter_mixed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.android.RenderMode
import io.flutter.embedding.android.TransparencyMode
import io.flutter.embedding.engine.FlutterEngine

internal class FlutterMixedFragment private constructor() : FlutterFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val flutter: View? = super.onCreateView(
            inflater,
            container,
            savedInstanceState,
        )

        return flutter

//        return FrameLayout(context).apply {
//            setBackgroundColor(Color.BLUE)
//            addView(
//                flutter,
//                FrameLayout.LayoutParams(
//                    FrameLayout.LayoutParams.MATCH_PARENT,
//                    FrameLayout.LayoutParams.MATCH_PARENT,
//                ).apply {
//                    setPadding(20)
//                },
//            )
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val flutterView: FlutterView? = FlutterMixedUtils.findFlutterView(view = view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

    }

    internal companion object {

        internal fun buildMixed(): FlutterFragment {
            try {
                val mFragment: Class<out FlutterFragment> = FlutterMixedFragment::class.java
                val args = Bundle().apply {
                    putString(ARG_CACHED_ENGINE_ID, FlutterMixed.ENGINE_ID)
                    putBoolean(ARG_DESTROY_ENGINE_WITH_FRAGMENT, false)
                    putString(ARG_FLUTTERVIEW_RENDER_MODE, RenderMode.surface.name)
                    putString(ARG_FLUTTERVIEW_TRANSPARENCY_MODE, TransparencyMode.opaque.name)
                    putBoolean(ARG_SHOULD_ATTACH_ENGINE_TO_ACTIVITY, true)
                }
                val mixed = mFragment.getDeclaredConstructor().newInstance()
                mixed.setArguments(args)
                return mixed
            } catch (e: Exception) {
                error(message = e.toString())
            }
        }
    }
}