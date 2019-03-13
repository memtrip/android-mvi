package com.consistence.pinyin.domain.study

import com.consistence.pinyin.domain.SchedulerProvider
import com.consistence.pinyin.domain.pinyin.db.GetPinyin
import com.consistence.pinyin.domain.study.db.StudyDao
import com.consistence.pinyin.domain.study.db.withPinyin
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class GetStudy @Inject internal constructor(
    private val studyDao: StudyDao,
    private val getPinyin: GetPinyin,
    private val schedulerProvider: SchedulerProvider
) {

    fun get(): Single<List<Study>> {
        return Observable.fromCallable { studyDao.studyOrderByDesc() }
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.thread())
            .withPinyin(getPinyin)
    }
}