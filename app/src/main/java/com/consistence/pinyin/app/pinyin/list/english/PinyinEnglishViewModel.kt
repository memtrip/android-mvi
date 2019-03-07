package com.consistence.pinyin.app.pinyin.list.english

import android.app.Application
import com.consistence.pinyin.app.pinyin.list.PinyinListViewModel
import com.consistence.pinyin.domain.pinyin.db.EnglishSearch
import javax.inject.Inject

class PinyinEnglishViewModel @Inject internal constructor(
    private val englishSearch: EnglishSearch,
    application: Application
) : PinyinListViewModel(application) {

    override val defaultSearch = "pinyin"

    override fun search(terms: String) = englishSearch.search(terms)
}