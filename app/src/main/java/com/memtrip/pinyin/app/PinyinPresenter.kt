package com.memtrip.pinyin.app

import com.memtrip.pinyin.Event
import com.memtrip.pinyin.Presenter
import com.memtrip.pinyin.R
import io.reactivex.functions.Consumer
import javax.inject.Inject

class PinyinPresenter @Inject internal constructor() : Presenter<PinyinView>() {

    override fun event(): Consumer<Event> {
        return Consumer {
            when(it.id) {
                R.id.pinyin_activity_search_cardview ->
                    view.navigateToSearch()
            }
        }
    }
}