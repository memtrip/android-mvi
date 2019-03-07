package com.consistence.pinyin.app.list

import com.consistence.pinyin.domain.pinyin.Pinyin
import com.memtrip.mxandroid.MxViewIntent

sealed class PinyinListIntent : MxViewIntent {
    data class Init(val terms: String) : PinyinListIntent()
    data class Search(val terms: String) : PinyinListIntent()
    data class PlayAudio(val audioSrc: String) : PinyinListIntent()
    data class SelectItem(val pinyin: Pinyin) : PinyinListIntent()
    object Idle : PinyinListIntent()
}