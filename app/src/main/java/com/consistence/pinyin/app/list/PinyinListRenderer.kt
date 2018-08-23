package com.consistence.pinyin.app.list

import com.consistence.pinyin.api.PinyinEntity
import com.memtrip.mxandroid.MxRenderAction
import com.memtrip.mxandroid.MxViewLayout
import com.memtrip.mxandroid.MxViewRenderer
import javax.inject.Inject

sealed class PinyinListRenderAction : MxRenderAction {
    data class Populate(val pinyinList: List<PinyinEntity>) : PinyinListRenderAction()
    object OnError : PinyinListRenderAction()
    data class PlayAudio(val audioSrc: String) : PinyinListRenderAction()
    data class SelectItem(val pinyin: PinyinEntity) : PinyinListRenderAction()
    object Idle : PinyinListRenderAction()
}

interface PinyinListLayout : MxViewLayout {
    fun populate(pinyin: List<PinyinEntity>)
    fun navigateToPinyinDetails(pinyinEntity: PinyinEntity)
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
            layout.navigateToPinyinDetails(state.view.pinyin)
        }
        PinyinListViewState.View.Idle -> {
        }
    }
}