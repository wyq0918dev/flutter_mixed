package com.wyq0918dev.flutter_mixed

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.flutter.embedding.android.FlutterFragment

internal class FlutterMixedAdapter(
    fragmentActivity: FragmentActivity,
) : FragmentStateAdapter(
    fragmentActivity,
), MixedSignals {

    private val mMixed: FlutterFragment = FlutterMixedFragment.buildMixed()

    override fun getItemCount(): Int {
        return 1
    }

    override fun createFragment(position: Int): Fragment {
        return mMixed
    }

    override fun onPostResume() {
        mMixed.onPostResume()
    }

    override fun onNewIntent(intent: Intent) {
        mMixed.onNewIntent(intent)
    }

    override fun onBackPressed() {
        mMixed.onBackPressed()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>, grantResults: IntArray
    ) {
        mMixed.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int, data: Intent?
    ) {
        mMixed.onActivityResult(requestCode, resultCode, data)
    }

    override fun onUserLeaveHint() {
        mMixed.onUserLeaveHint()
    }

    override fun onTrimMemory(level: Int) {
        mMixed.onTrimMemory(level)
    }
}