package com.memtrip.pinyin.app

import com.memtrip.pinyin.Event
import com.memtrip.pinyin.Presenter
import com.memtrip.pinyin.R
import com.memtrip.pinyin.api.GetPinyin
import io.reactivex.functions.Consumer
import javax.inject.Inject

class EntryPresenter @Inject internal  constructor(val getPinyin: GetPinyin): Presenter<EntryView>() {

    override fun start() {
        super.start()

        getPinyin()
    }

    override fun event(): Consumer<Event> = Consumer {
        when (it.id) {
            R.id.kit_error_retry_button ->
                getPinyin()
        }
    }

    private fun getPinyin() {

        view.showProgress()

        getPinyin.values(Consumer {
            view.navigateToPinyin()
        }, Consumer {
            view.error()
        })
    }
}