package com.consistence.pinyin.app.list

import com.consistence.pinyin.MxViewState
import com.consistence.pinyin.api.PinyinEntity

data class PinyinListViewState(val view: View) : MxViewState {

    sealed class View {
        object Idle : View()
        data class Populate(val pinyin: List<PinyinEntity>) : View()
        object OnError : View()
        data class PlayAudio(val audioSrc: String, val eventId: Int = MxViewState.id()) : View()
        data class SelectItem(val pinyin: PinyinEntity) : View()
    }
}