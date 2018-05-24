package com.memtrip.pinyin

interface PresenterView {
    fun sendEvent(event: Event)
    fun showProgress()
    fun hideProgress()
}