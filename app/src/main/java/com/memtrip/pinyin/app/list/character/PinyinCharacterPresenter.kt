package com.memtrip.pinyin.app.list.character

import com.memtrip.pinyin.*
import com.memtrip.pinyin.api.CharacterSearch
import com.memtrip.pinyin.api.PinyinEntity
import com.memtrip.pinyin.app.list.PinyinListPresenter
import io.reactivex.functions.Consumer
import javax.inject.Inject

class PinyinCharacterPresenter @Inject internal constructor(
        val characterSearch: CharacterSearch) : PinyinListPresenter<PinyinCharacterView>() {

    override val defaultSearch = "拼音"

    override fun first() {
        super.first()
        search()
    }

    override fun search(terms: String) {
        i(characterSearch.search(terms, Consumer {
            view.populate(it)
        }, Consumer {
            view.error()
        }))
    }
}