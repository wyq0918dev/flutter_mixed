package com.wyq0918dev.flutter_mixed

interface MixedAware {
    fun onAttachSignals(signals: MixedSignals)
    fun onDetachSignals()
}