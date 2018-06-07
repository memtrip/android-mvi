package com.consistence.pinyin.app.list.character

import android.app.Application
import com.consistence.pinyin.api.CharacterSearch

import com.consistence.pinyin.app.list.PinyinListModel

import javax.inject.Inject

class PinyinCharacterModel @Inject internal constructor(
        val characterSearch: CharacterSearch,
        application: Application) : PinyinListModel(application) {

    override val defaultSearch = "拼音"

    override fun search(terms: String) = characterSearch.search(terms)
}