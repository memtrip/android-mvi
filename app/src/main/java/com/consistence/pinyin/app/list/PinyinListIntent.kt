package com.consistence.pinyin.app.list

import com.consistence.pinyin.MxViewIntent
import com.consistence.pinyin.api.PinyinEntity

sealed class PinyinListIntent : MxViewIntent {
    data class Init(val terms: String) : PinyinListIntent()
    data class Search(val terms: String) : PinyinListIntent()
    data class PlayAudio(val audioSrc: String) : PinyinListIntent()
    data class SelectItem(val pinyin: PinyinEntity) : PinyinListIntent()
    object Idle : PinyinListIntent()
}