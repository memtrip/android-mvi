package com.consistence.pinyin

interface MxViewRenderer<VL : MxViewLayout, VS : MxViewState> {
    fun layout(layout: VL, state: VS)
}