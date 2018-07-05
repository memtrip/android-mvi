package com.consistence.pinyin.app

import com.consistence.pinyin.MxViewIntent

sealed class PinyinIntent : MxViewIntent {
    object Init : PinyinIntent()
    data class TabSelected(val page: Page) : PinyinIntent()
}
