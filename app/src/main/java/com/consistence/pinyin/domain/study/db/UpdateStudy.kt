package com.consistence.pinyin.domain.study.db

import com.consistence.pinyin.domain.SchedulerProvider
import com.consistence.pinyin.domain.study.Study
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class UpdateStudy @Inject internal constructor(
    private val studyDao: StudyDao,
    private val schedulerProvider: SchedulerProvider
) {

    fun update(study: Study): Single<Boolean> {

        val chineseSentence = ""

        return Completable
            .fromAction { studyDao.update(study.englishTranslation, chineseSentence, study.uid) }
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.thread())
            .toSingle { true }
    }
}