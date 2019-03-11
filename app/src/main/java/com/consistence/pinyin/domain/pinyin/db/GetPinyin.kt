package com.consistence.pinyin.domain.pinyin.db

import com.consistence.pinyin.domain.SchedulerProvider
import com.consistence.pinyin.domain.pinyin.Pinyin
import io.reactivex.Single
import javax.inject.Inject

class GetPinyin @Inject internal constructor(
    private val pinyinDao: PinyinDao,
    private val schedulerProvider: SchedulerProvider
) {

    fun byListOfUid(listOfUid: List<Int>): Single<List<Pinyin>> {
        return Single.fromCallable { pinyinDao.getByUid(listOfUid) }
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.thread())
            .map { entities ->
                entities.map {
                    Pinyin(
                        it.uid,
                        it.sourceUrl,
                        it.phoneticScriptText,
                        it.romanLetterText,
                        it.audioSrc,
                        it.englishTranslationText,
                        it.chineseCharacters,
                        it.characterImageSrc
                    )
                }
            }
    }
}