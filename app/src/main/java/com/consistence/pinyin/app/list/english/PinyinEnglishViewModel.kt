package com.consistence.pinyin.app.list.english

import android.app.Application
import com.consistence.pinyin.domain.pinyin.db.EnglishSearch
import com.consistence.pinyin.app.list.PinyinListViewModel
import javax.inject.Inject

class PinyinEnglishViewModel @Inject internal constructor(
    private val englishSearch: EnglishSearch,
    application: Application
) : PinyinListViewModel(application) {

    override val defaultSearch = "pinyin"

    override fun search(terms: String) = englishSearch.search(terms)
}