package com.consistence.pinyin.app.list

import com.consistence.pinyin.PresenterView
import com.consistence.pinyin.api.PinyinEntity

interface PinyinListView : PresenterView {
    fun populate(pinyin: List<PinyinEntity>)
    fun navigateToPinyinDetails(pinyinEntity: PinyinEntity)
    fun error()
}