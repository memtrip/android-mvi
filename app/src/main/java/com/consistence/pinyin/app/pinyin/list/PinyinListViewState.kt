package com.consistence.pinyin.app.pinyin.list

import com.consistence.pinyin.domain.pinyin.Pinyin
import com.memtrip.mxandroid.MxViewState

data class PinyinListViewState(val view: View) : MxViewState {

    sealed class View {
        object Idle : View()
        data class Populate(val pinyin: List<Pinyin>) : View()
        object OnError : View()
        data class PlayAudio(val audioSrc: String, val eventId: Int = MxViewState.id()) : View()
        data class SelectItem(val pinyin: Pinyin) : View()
    }
}