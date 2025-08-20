package com.wyq0918dev.flutter_mixed

interface MixedAware {
    fun onAttachedSignals(signals: MixedSignals)
    fun onDetachedSignals()
}