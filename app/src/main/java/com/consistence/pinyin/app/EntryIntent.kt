package com.consistence.pinyin.app

import com.consistence.pinyin.MxViewIntent

sealed class EntryIntent : MxViewIntent {
    object Init : EntryIntent()
    object Retry : EntryIntent()
}