package com.memtrip.pinyin

import android.content.Context

interface PresenterView {

    fun sendEvent(event: Event)

    fun showProgress()
    fun hideProgress()

    fun close()

    fun context() : Context
}