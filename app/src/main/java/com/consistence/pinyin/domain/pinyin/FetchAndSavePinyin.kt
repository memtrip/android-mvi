package com.consistence.pinyin.domain.pinyin

import com.consistence.pinyin.domain.pinyin.api.FetchPinyin
import com.consistence.pinyin.domain.pinyin.db.PinyinEntity
import com.consistence.pinyin.domain.pinyin.db.SavePinyin
import io.reactivex.Single

import javax.inject.Inject

class FetchAndSavePinyin @Inject internal constructor(
    private val fetchPinyin: FetchPinyin,
    private val savePinyin: SavePinyin
) {

    fun save(): Single<List<PinyinEntity>> {
        return fetchPinyin.values().flatMap { pinyinWrapper ->
            savePinyin.insert(pinyinWrapper.pinyin)
        }
    }
}