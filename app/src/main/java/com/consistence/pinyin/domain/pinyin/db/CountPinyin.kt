package com.consistence.pinyin.domain.pinyin.db

import com.consistence.pinyin.domain.SchedulerProvider
import io.reactivex.Single
import javax.inject.Inject

class CountPinyin @Inject internal constructor(
    private val pinyinDao: PinyinDao,
    private val schedulerProvider: SchedulerProvider
) {

    fun count(): Single<Int> {
        return Single.fromCallable { pinyinDao.count() }
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.thread())
    }
}