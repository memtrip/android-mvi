package com.consistence.pinyin

interface ViewRender<L : ViewLayout, S : ViewState> {
    fun state(layout: L, state: S)
}