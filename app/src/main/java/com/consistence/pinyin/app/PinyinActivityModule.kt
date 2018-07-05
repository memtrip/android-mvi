package com.consistence.pinyin.app

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PinyinActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributesPinyinActivity(): PinyinActivity
}