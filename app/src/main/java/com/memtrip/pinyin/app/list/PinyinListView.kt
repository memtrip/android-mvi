package com.memtrip.pinyin.app.list

import com.memtrip.pinyin.PresenterView
import com.memtrip.pinyin.api.PinyinEntity

interface PinyinListView : PresenterView {
    fun populate(pinyin: List<PinyinEntity>)
    fun navigateToPinyinDetails(pinyinEntity: PinyinEntity)
    fun error()
}