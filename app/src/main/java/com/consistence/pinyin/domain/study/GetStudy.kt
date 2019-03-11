package com.consistence.pinyin.domain.study

import com.consistence.pinyin.domain.SchedulerProvider
import com.consistence.pinyin.domain.pinyin.Pinyin
import com.consistence.pinyin.domain.pinyin.db.GetPinyin
import com.consistence.pinyin.domain.study.db.StudyDao
import com.consistence.pinyin.domain.study.db.StudyEntity
import com.consistence.pinyin.domain.study.db.listOfUid
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
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
            .flatMap { Observable.fromIterable(it) }
            .concatMap { studyEntity ->
                Observable.zip(
                    Observable.just(studyEntity),
                    getPinyin.byListOfUid(studyEntity.chineseSentence.listOfUid()).toObservable(),
                    BiFunction<StudyEntity, List<Pinyin>, Study> { _, pinyin ->
                        Study(
                            studyEntity.englishTranslation,
                            pinyin,
                            studyEntity.uid
                        )
                    })
            }
            .toList()
    }
}