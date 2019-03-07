package com.consistence.pinyin.app.pinyin

import com.memtrip.mxandroid.MxRenderAction
import com.memtrip.mxandroid.MxViewLayout
import com.memtrip.mxandroid.MxViewRenderer
import javax.inject.Inject

sealed class PinyinRenderAction : MxRenderAction {
    data class SearchHint(val hint: String) : PinyinRenderAction()
}

interface PinyinLayout : MxViewLayout {
    var currentSearchQuery: String
    fun updateSearchHint(hint: String)
}

class PinyinRenderer @Inject internal constructor() : MxViewRenderer<PinyinLayout, PinyinViewState> {

    override fun layout(layout: PinyinLayout, state: PinyinViewState) {
        layout.updateSearchHint(state.currentSearchQuery)
    }
}