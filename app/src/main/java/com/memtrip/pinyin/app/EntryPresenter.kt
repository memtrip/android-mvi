package com.memtrip.pinyin.app

import com.memtrip.pinyin.Event
import com.memtrip.pinyin.Presenter
import com.memtrip.pinyin.R
import com.memtrip.pinyin.api.CountPinyin
import com.memtrip.pinyin.api.FetchAndSavePinyin
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import javax.inject.Inject

class EntryPresenter @Inject internal  constructor(
        val fetchAndSavePinyin: FetchAndSavePinyin,
        val countPinyin: CountPinyin): Presenter<EntryView>() {

    override fun first() {
        super.first()

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

        i(countPinyin.count(Consumer {
            if (it > 0) {
                navigateToPinyin()
            } else {
                i(fetchAndSavePinyin.save(Action {
                    navigateToPinyin()
                }, Consumer {
                    it.printStackTrace()
                    view.error()
                }))
            }
        }, Consumer {
            view.error()
        }))
    }


    private fun navigateToPinyin() {
        view.hideProgress()
        view.navigateToPinyin()
        view.close()
    }
}