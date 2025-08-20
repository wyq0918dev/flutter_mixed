package com.wyq0918dev.flutter_mixed

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

internal class FlutterMixedAdapter(
    fragmentActivity: FragmentActivity,
) : FragmentStateAdapter(
    fragmentActivity,
) {
    override fun getItemCount(): Int = 1
    override fun createFragment(position: Int): Fragment {
        return FlutterMixedFragment.buildMixed()
    }
}