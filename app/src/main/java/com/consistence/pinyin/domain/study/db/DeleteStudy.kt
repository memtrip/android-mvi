package com.consistence.pinyin.domain.study.db

import com.consistence.pinyin.domain.SchedulerProvider
import com.consistence.pinyin.domain.study.Study
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class DeleteStudy @Inject internal constructor(
    private val studyDao: StudyDao,
    private val schedulerProvider: SchedulerProvider
) {

    fun remove(study: Study): Single<Boolean> {
        return Completable
            .fromAction { studyDao.deleteStudy(study.uid) }
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.thread())
            .toSingle { true }
    }
}