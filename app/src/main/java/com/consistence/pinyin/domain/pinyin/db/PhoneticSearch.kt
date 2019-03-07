package com.consistence.pinyin.domain.pinyin.db

import com.consistence.pinyin.domain.SchedulerProvider
import io.reactivex.Single
import javax.inject.Inject

class PhoneticSearch @Inject internal constructor(
    private val pinyinDao: PinyinDao,
    private val schedulerProvider: SchedulerProvider
) {

    fun search(terms: String): Single<List<PinyinEntity>> {
        return Single.fromCallable { pinyinDao.phoneticSearch("$terms%") }
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.thread())
    }
}