package com.consistence.pinyin.app

import com.consistence.pinyin.ViewIntent
import com.consistence.pinyin.ViewLayout
import com.consistence.pinyin.ViewRender
import com.consistence.pinyin.ViewState
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Inject

sealed class EntryIntent : ViewIntent {
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

class EntryRender @Inject internal constructor(): ViewRender<EntryLayout, EntryState> {
    override fun state(layout: EntryLayout, state: EntryState) = when(state) {
        EntryState.OnProgress -> {
            layout.showProgress()
        }
        EntryState.OnError -> {
            layout.hideProgress()
            layout.error()
        }
        EntryState.OnPinyinLoaded -> {
            layout.hideProgress()
            layout.navigateToPinyin()
        }
    }
}

@Module
abstract class EntryActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributesEntryActivity() : EntryActivity
}