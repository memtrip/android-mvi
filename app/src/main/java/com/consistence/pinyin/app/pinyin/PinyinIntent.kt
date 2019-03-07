package com.consistence.pinyin.app.pinyin

import com.memtrip.mxandroid.MxViewIntent

sealed class PinyinIntent : MxViewIntent {
    object Init : PinyinIntent()
    data class TabSelected(val page: Page) : PinyinIntent()
}
