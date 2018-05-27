package com.consistence.pinyin.app.list.character

import com.consistence.pinyin.api.CharacterSearch
import com.consistence.pinyin.app.list.PinyinListPresenter
import io.reactivex.functions.Consumer
import javax.inject.Inject

class PinyinCharacterPresenter @Inject internal constructor(
        val characterSearch: CharacterSearch) : PinyinListPresenter<PinyinCharacterView>() {

    override val defaultSearch = "拼音"

    override fun search(terms: String) {
        i(characterSearch.search(terms, Consumer {
            view.populate(it)
        }, Consumer {
            view.error()
        }))
    }
}