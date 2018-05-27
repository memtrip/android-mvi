package com.memtrip.pinyin.app

import com.memtrip.pinyin.Event
import com.memtrip.pinyin.Presenter
import com.memtrip.pinyin.R
import com.memtrip.pinyin.TabSelectedEvent
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