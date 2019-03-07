package com.consistence.pinyin.app.list.phonetic

import android.app.Application
import com.consistence.pinyin.database.pinyin.PhoneticSearch
import com.consistence.pinyin.database.pinyin.PinyinEntity
import com.consistence.pinyin.app.list.PinyinListViewModel
import io.reactivex.Single
import javax.inject.Inject

class PinyinPhoneticViewModel @Inject internal constructor(
        private val phoneticSearch: PhoneticSearch,
        application: Application
) : PinyinListViewModel(application) {

    override val defaultSearch = "pinyin"

    override fun search(terms: String): Single<List<PinyinEntity>> = phoneticSearch.search(terms)
}