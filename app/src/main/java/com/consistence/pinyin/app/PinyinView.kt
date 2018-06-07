package com.consistence.pinyin.app

import android.app.Application
import com.consistence.pinyin.ViewIntent
import com.consistence.pinyin.ViewLayout
import com.consistence.pinyin.ViewRender
import com.consistence.pinyin.ViewState

import dagger.Component
import javax.inject.Singleton
import dagger.BindsInstance

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

@Singleton
@Component
interface PinyinComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): PinyinComponent
    }

    fun inject(pinyinActivity: PinyinActivity)
}