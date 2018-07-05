package com.consistence.pinyin.app.list.character

import android.app.Application
import com.consistence.pinyin.api.CharacterSearch

import com.consistence.pinyin.app.list.PinyinListViewModel

import javax.inject.Inject

class PinyinCharacterViewModel @Inject internal constructor(
    private val characterSearch: CharacterSearch,
    application: Application
) : PinyinListViewModel(application) {

    override val defaultSearch = "拼音"

    override fun search(terms: String) = characterSearch.search(terms)
}