package com.consistence.pinyin.app

import com.consistence.pinyin.MxViewState

data class EntryViewState(val view: View) : MxViewState {
    sealed class View : MxViewState {
        object OnProgress : View()
        object OnError : View()
        object OnPinyinLoaded : View()
    }
}