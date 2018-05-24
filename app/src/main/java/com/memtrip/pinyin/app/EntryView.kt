package com.memtrip.pinyin.app

import com.memtrip.pinyin.PresenterView
import com.memtrip.pinyin.api.NetworkModule
import com.memtrip.pinyin.api.Pinyin
import com.memtrip.pinyin.api.PinyinModule

import dagger.Component
import javax.inject.Singleton

interface EntryView : PresenterView {
    fun navigateToPinyin()
    fun error()
}

@Singleton
@Component(modules = [ NetworkModule::class, PinyinModule::class ])
interface EntryComponent {
    fun inject(entryActivity: EntryActivity)
}