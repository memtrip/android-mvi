package com.consistence.pinyin.app

import com.memtrip.mxandroid.MxRenderAction
import com.memtrip.mxandroid.MxViewLayout
import com.memtrip.mxandroid.MxViewRenderer
import javax.inject.Inject

sealed class EntryRenderAction : MxRenderAction {
    object OnProgress : EntryRenderAction()
    object OnError : EntryRenderAction()
    object OnPinyinLoaded : EntryRenderAction()
}

interface EntryLayout : MxViewLayout {
    fun showProgress()
    fun navigateToPinyin()
    fun showError()
}

class EntryRenderer @Inject internal constructor() : MxViewRenderer<EntryLayout, EntryViewState> {
    override fun layout(layout: EntryLayout, state: EntryViewState) = when (state.view) {
        EntryViewState.View.OnProgress -> {
            layout.showProgress()
        }
        EntryViewState.View.OnPinyinLoaded -> {
            layout.navigateToPinyin()
        }
        EntryViewState.View.OnError -> {
            layout.showError()
        }
    }
}