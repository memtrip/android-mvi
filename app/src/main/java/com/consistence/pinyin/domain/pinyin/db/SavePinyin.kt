package com.consistence.pinyin.domain.pinyin.db

import com.consistence.pinyin.domain.pinyin.api.PinyinJson
import com.consistence.pinyin.domain.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class SavePinyin @Inject internal constructor(
    private val pinyinDao: PinyinDao,
    private val schedulerProvider: SchedulerProvider
) {

    fun insert(pinyin: List<PinyinJson>): Single<List<PinyinEntity>> {

        val pinyinEntities = pinyin.map {
            PinyinEntity(
                it.sourceUrl,
                it.phoneticScriptText,
                it.romanLetterText,
                it.audioSrc,
                it.englishTranslationText,
                it.chineseCharacters,
                it.characterImageSrc)
        }

        return Completable
            .fromAction { pinyinDao.insertAll(pinyinEntities) }
            .observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.thread())
            .toSingle { pinyinEntities }
    }
}