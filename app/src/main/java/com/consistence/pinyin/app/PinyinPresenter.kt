package com.consistence.pinyin.app

import com.consistence.pinyin.legacy.Event
import com.consistence.pinyin.legacy.Presenter
import com.consistence.pinyin.R
import com.consistence.pinyin.legacy.TabSelectedEvent
import io.reactivex.functions.Consumer

import javax.inject.Inject

class PinyinPresenter @Inject internal constructor() : Presenter<PinyinView>() {

    fun updateSearchHint(position: Int) {
        when (position) {
            Page.PHONETIC.ordinal -> view.updateSearchHint(
                    view.context().getString(R.string.pinyin_activity_search_pinyin_hint))
            Page.ENGLISH.ordinal -> view.updateSearchHint(
                    view.context().getString(R.string.pinyin_activity_search_english_hint))
            Page.CHARACTER.ordinal -> view.updateSearchHint(
                    view.context().getString(R.string.pinyin_activity_search_character_hint))
        }
    }

    override fun event(): Consumer<Event> {
        return Consumer {
            when (it) {
                is TabSelectedEvent ->
                    updateSearchHint(it.position)
            }
        }
    }
}