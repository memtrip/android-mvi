package com.consistence.pinyin.domain.study.db

import com.consistence.pinyin.domain.SchedulerProvider
import com.consistence.pinyin.domain.pinyin.formatPinyinString
import com.consistence.pinyin.domain.pinyin.pinyinForDatabase
import com.consistence.pinyin.domain.study.Study
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class SaveStudy @Inject internal constructor(
    private val studyDao: StudyDao,
    private val schedulerProvider: SchedulerProvider
) {

    fun insert(study: Study): Single<Boolean> {

        val studyEntity = StudyEntity(
            study.englishTranslation,
            study.pinyin.pinyinForDatabase(),
            0,
            0
        )

        return Completable
            .fromAction { studyDao.insert(studyEntity) }
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.thread())
            .toSingle { true }
    }
}