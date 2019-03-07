package com.consistence.pinyin.domain.study.db

import com.consistence.pinyin.domain.SchedulerProvider
import io.reactivex.Single
import javax.inject.Inject

class GetStudyOrderedByDesc @Inject internal constructor(
    private val studyDao: StudyDao,
    private val schedulerProvider: SchedulerProvider
) {

    fun get(): Single<List<StudyEntity>> {
        return Single.fromCallable { studyDao.studyOrderByDesc() }
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.thread())
    }
}