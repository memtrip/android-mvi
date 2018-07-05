package com.consistence.pinyin.app.detail

import com.consistence.pinyin.MxViewIntent

sealed class PinyinDetailIntent : MxViewIntent {
    object Idle : PinyinDetailIntent()
    object PlayAudio : PinyinDetailIntent()
}
