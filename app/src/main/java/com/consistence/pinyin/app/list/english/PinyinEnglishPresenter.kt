package com.consistence.pinyin.app.list.english

import com.consistence.pinyin.api.EnglishSearch

import com.consistence.pinyin.app.list.PinyinListPresenter
import io.reactivex.functions.Consumer
import javax.inject.Inject

class PinyinEnglishPresenter @Inject internal constructor(
        val englishSearch: EnglishSearch) : PinyinListPresenter<PinyinEnglishView>() {

    override val defaultSearch = "pinyin"

    override fun search(terms: String) {
        i(englishSearch.search(terms, Consumer {
            view.populate(it)
        }, Consumer {
            view.error()
        }))
    }
}