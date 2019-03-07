package com.consistence.pinyin.domain.study.db

import com.consistence.pinyin.domain.SchedulerProvider
import io.reactivex.Single
import javax.inject.Inject

class GetStudy @Inject internal constructor(
    private val studyDao: StudyDao,
    private val schedulerProvider: SchedulerProvider
) {

    fun search(): Single<List<StudyEntity>> {
        return Single.fromCallable { studyDao.studyOrderByDesc() }
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.thread())
    }
}