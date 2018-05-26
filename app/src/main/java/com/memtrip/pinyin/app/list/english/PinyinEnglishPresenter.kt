package com.memtrip.pinyin.app.list.english

import com.memtrip.pinyin.api.EnglishSearch

import com.memtrip.pinyin.app.list.PinyinListPresenter
import io.reactivex.functions.Consumer
import javax.inject.Inject

class PinyinEnglishPresenter @Inject internal constructor(
        val englishSearch: EnglishSearch) : PinyinListPresenter<PinyinEnglishView>() {

    override val defaultSearch = "pinyin"

    override fun first() {
        super.first()
        search()
    }

    override fun search(terms: String) {
        i(englishSearch.search(terms, Consumer {
            view.populate(it)
        }, Consumer {
            view.error()
        }))
    }
}