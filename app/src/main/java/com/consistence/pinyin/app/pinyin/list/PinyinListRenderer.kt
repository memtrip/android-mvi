package com.consistence.pinyin.app.pinyin.list

import com.consistence.pinyin.domain.pinyin.Pinyin
import com.memtrip.mxandroid.MxRenderAction
import com.memtrip.mxandroid.MxViewLayout
import com.memtrip.mxandroid.MxViewRenderer
import javax.inject.Inject

sealed class PinyinListRenderAction : MxRenderAction {
    data class Populate(val pinyinList: List<Pinyin>) : PinyinListRenderAction()
    object OnError : PinyinListRenderAction()
    data class PlayAudio(val audioSrc: String) : PinyinListRenderAction()
    data class SelectItem(val pinyin: Pinyin) : PinyinListRenderAction()
    object Idle : PinyinListRenderAction()
}

interface PinyinListLayout : MxViewLayout {
    fun populate(pinyin: List<Pinyin>)
    fun pinyinItemSelected(pinyin: Pinyin)
    fun showError()
    fun playAudio(audioSrc: String)
}

class PinyinListRenderer @Inject internal constructor() : MxViewRenderer<PinyinListLayout, PinyinListViewState> {
    override fun layout(layout: PinyinListLayout, state: PinyinListViewState) = when (state.view) {
        is PinyinListViewState.View.Populate -> {
            layout.populate(state.view.pinyin)
        }
        PinyinListViewState.View.OnError -> {
            layout.showError()
        }
        is PinyinListViewState.View.PlayAudio -> {
            layout.playAudio(state.view.audioSrc)
        }
        is PinyinListViewState.View.SelectItem -> {
            layout.pinyinItemSelected(state.view.pinyin)
        }
        PinyinListViewState.View.Idle -> {
        }
    }
}