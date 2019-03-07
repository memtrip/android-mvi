package com.consistence.pinyin.app.list

import com.consistence.pinyin.database.pinyin.PinyinEntity
import com.memtrip.mxandroid.MxViewIntent

sealed class PinyinListIntent : MxViewIntent {
    data class Init(val terms: String) : PinyinListIntent()
    data class Search(val terms: String) : PinyinListIntent()
    data class PlayAudio(val audioSrc: String) : PinyinListIntent()
    data class SelectItem(val pinyin: PinyinEntity) : PinyinListIntent()
    object Idle : PinyinListIntent()
}