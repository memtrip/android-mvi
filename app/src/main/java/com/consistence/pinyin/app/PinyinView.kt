package com.consistence.pinyin.app

import com.consistence.pinyin.legacy.PresenterView
import dagger.Component
import javax.inject.Singleton

interface PinyinView : PresenterView {
    var currentSearchQuery : String

    fun updateSearchHint(hint: String)
}

@Singleton
@Component
interface PinyinComponent {
    fun inject(pinyinActivity: PinyinActivity)
}