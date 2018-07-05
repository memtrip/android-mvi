package com.consistence.pinyin.app

import com.consistence.pinyin.MxRenderAction
import com.consistence.pinyin.MxViewLayout
import com.consistence.pinyin.MxViewRenderer
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