package com.consistence.pinyin

interface ViewRender<S : ViewState> {
    fun state(state: S)
}