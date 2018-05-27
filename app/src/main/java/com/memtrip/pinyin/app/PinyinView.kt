package com.memtrip.pinyin.app

import com.memtrip.pinyin.PresenterView
import dagger.Component
import javax.inject.Singleton

interface PinyinView : PresenterView {
    var currentSearchQuery : String
}

@Singleton
@Component
interface PinyinComponent {
    fun inject(pinyinActivity: PinyinActivity)
}