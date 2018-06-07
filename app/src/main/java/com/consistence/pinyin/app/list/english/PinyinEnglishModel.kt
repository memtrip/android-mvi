package com.consistence.pinyin.app.list.english

import android.app.Application
import com.consistence.pinyin.api.EnglishSearch
import com.consistence.pinyin.app.list.PinyinListModel

import javax.inject.Inject

class PinyinEnglishModel @Inject internal constructor(
        val englishSearch: EnglishSearch,
        application: Application) : PinyinListModel(application) {

    override val defaultSearch = "pinyin"

    override fun search(terms: String) = englishSearch.search(terms)
}