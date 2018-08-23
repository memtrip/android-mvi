package com.consistence.pinyin.app.detail

import com.memtrip.mxandroid.MxViewIntent

sealed class PinyinDetailIntent : MxViewIntent {
    object Idle : PinyinDetailIntent()
    object PlayAudio : PinyinDetailIntent()
}
