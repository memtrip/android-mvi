package com.consistence.pinyin.database.pinyin

import com.consistence.pinyin.api.FetchPinyin
import com.consistence.pinyin.database.pinyin.PinyinEntity
import com.consistence.pinyin.database.pinyin.SavePinyin
import io.reactivex.Single

import javax.inject.Inject

class FetchAndSavePinyin @Inject internal constructor(
    private val fetchPinyin: FetchPinyin,
    private val savePinyin: SavePinyin
) {

    fun save(): Single<List<PinyinEntity>> {
        return fetchPinyin.values()
            .flatMap { (savePinyin.insert(it.pinyin)) }
    }
}