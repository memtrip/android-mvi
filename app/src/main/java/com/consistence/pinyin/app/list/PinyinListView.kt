package com.consistence.pinyin.app.list

import com.consistence.pinyin.ViewIntent
import com.consistence.pinyin.ViewLayout
import com.consistence.pinyin.ViewRender
import com.consistence.pinyin.ViewState
import com.consistence.pinyin.api.PinyinEntity

sealed class PinyinListIntent : ViewIntent {
    data class Search(val terms: String) : PinyinListIntent()
    data class SelectItem(val pinyin: PinyinEntity) : PinyinListIntent()
    data class PlayAudio(val audioSrc: String) : PinyinListIntent()
}

sealed class PinyinListState : ViewState {
    data class Populate(val pinyinList: List<PinyinEntity>) : PinyinListState()
    data class NavigateToDetails(val pinyin: PinyinEntity) : PinyinListState()
    data class PlayAudio(val audioSrc: String) : PinyinListState()
    object Error : PinyinListState()
}

interface PinyinListLayout : ViewLayout {
    fun populate(pinyin: List<PinyinEntity>)
    fun navigateToPinyinDetails(pinyinEntity: PinyinEntity)
    fun error()
    fun playAudio(audioSrc: String)
}

class PinyinListRender(val layout: PinyinListLayout) : ViewRender<PinyinListState> {
    override fun state(state: PinyinListState) = when(state) {
        is PinyinListState.Populate ->
            layout.populate(state.pinyinList)
        is PinyinListState.NavigateToDetails ->
            layout.navigateToPinyinDetails(state.pinyin)
        PinyinListState.Error ->
            layout.error()
        is PinyinListState.PlayAudio ->
            layout.playAudio(state.audioSrc)
    }
}