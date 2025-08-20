package com.wyq0918dev.flutter_mixed

import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.FlutterFragmentActivity

class FlutterMixedActivity : FlutterFragmentActivity() {

    override fun createFlutterFragment(): FlutterFragment {
        return FlutterMixedFragment.buildMixed()
    }
}