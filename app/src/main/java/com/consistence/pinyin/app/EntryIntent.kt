package com.consistence.pinyin.app

import com.memtrip.mxandroid.MxViewIntent

sealed class EntryIntent : MxViewIntent {
    object Init : EntryIntent()
    object Retry : EntryIntent()
}