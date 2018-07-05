package com.consistence.pinyin.api

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