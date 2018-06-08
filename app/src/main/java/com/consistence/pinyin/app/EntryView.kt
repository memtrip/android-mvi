package com.consistence.pinyin.app

import com.consistence.pinyin.ViewIntent
import com.consistence.pinyin.ViewLayout
import com.consistence.pinyin.ViewRender
import com.consistence.pinyin.ViewState
import dagger.Module
import dagger.android.ContributesAndroidInjector

sealed class EntryIntent : ViewIntent {
    object OnProgress : EntryIntent()
    object Init : EntryIntent()
    object Retry : EntryIntent()
}

sealed class EntryState : ViewState {
    object OnProgress : EntryState()
    object OnError: EntryState()
    object OnPinyinLoaded : EntryState()
}

interface EntryLayout : ViewLayout {
    fun showProgress()
    fun hideProgress()
    fun navigateToPinyin()
    fun error()
}

class EntryRender(private val entryLayout: EntryLayout) : ViewRender<EntryState> {
    override fun state(state: EntryState) = when(state) {
        EntryState.OnProgress -> {
            entryLayout.showProgress()
        }
        EntryState.OnError -> {
            entryLayout.hideProgress()
            entryLayout.error()
        }
        EntryState.OnPinyinLoaded -> {
            entryLayout.hideProgress()
            entryLayout.navigateToPinyin()
        }
    }
}

@Module
abstract class EntryActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributesEntryActivity() : EntryActivity
}