package com.consistence.pinyin.database.pinyin

import com.consistence.pinyin.api.SchedulerProvider
import io.reactivex.Single
import javax.inject.Inject

class CharacterSearch @Inject internal constructor(
    private val pinyinDao: PinyinDao,
    private val schedulerProvider: SchedulerProvider
) {

    fun search(terms: String): Single<List<PinyinEntity>> {
        return Single.fromCallable { pinyinDao.characterSearch("$terms%") }
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.thread())
    }
}