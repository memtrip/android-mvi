package com.consistence.pinyin.app

import com.consistence.pinyin.ViewIntent
import com.consistence.pinyin.ViewLayout
import com.consistence.pinyin.ViewRender
import com.consistence.pinyin.ViewState
import dagger.Module
import dagger.android.ContributesAndroidInjector

sealed class PinyinIntent : ViewIntent {
    data class TabSelected(val page: Page) : PinyinIntent()
}

sealed class PinyinState : ViewState {
    data class SearchHint(val hint: String) : PinyinState()
}

interface PinyinLayout: ViewLayout {
    var currentSearchQuery: String
    fun updateSearchHint(hint: String)
}

class PinyinRender(private val layout: PinyinLayout) : ViewRender<PinyinState> {

    override fun state(state: PinyinState) = when(state) {
        is PinyinState.SearchHint -> {
            layout.updateSearchHint(state.hint)
        }
    }
}

@Module
abstract class PinyinActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributesPinyinActivity() : PinyinActivity
}