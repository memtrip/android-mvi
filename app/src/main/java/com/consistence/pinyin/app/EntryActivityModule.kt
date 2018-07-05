package com.consistence.pinyin.app

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class EntryActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributesEntryActivity(): EntryActivity
}