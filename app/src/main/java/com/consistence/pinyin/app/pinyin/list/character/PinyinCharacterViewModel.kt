package com.consistence.pinyin.app.pinyin.list.character

import android.app.Application
import com.consistence.pinyin.app.pinyin.list.PinyinListViewModel
import com.consistence.pinyin.domain.pinyin.db.CharacterSearch
import javax.inject.Inject

class PinyinCharacterViewModel @Inject internal constructor(
    private val characterSearch: CharacterSearch,
    application: Application
) : PinyinListViewModel(application) {

    override val defaultSearch = "拼音"

    override fun search(terms: String) = characterSearch.search(terms)
}