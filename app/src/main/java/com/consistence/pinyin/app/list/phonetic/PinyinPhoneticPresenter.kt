package com.consistence.pinyin.app.list.phonetic

import com.consistence.pinyin.api.PhoneticSearch
import com.consistence.pinyin.app.PinyinView
import com.consistence.pinyin.app.list.PinyinListPresenter

import io.reactivex.functions.Consumer
import javax.inject.Inject

class PinyinPhoneticPresenter @Inject internal constructor(
        val phoneticSearch: PhoneticSearch) : PinyinListPresenter<PinyinPhoneticView>() {

    override val defaultSearch = "pinyin"

    override fun search(terms: String) {
        i(phoneticSearch.search(terms, Consumer {
            view.populate(it)
        }, Consumer {
            view.error()
        }))
    }
}