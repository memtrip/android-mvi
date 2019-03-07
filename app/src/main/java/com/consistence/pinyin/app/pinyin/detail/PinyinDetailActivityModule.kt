package com.consistence.pinyin.app.pinyin.detail

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class PinyinDetailActivityModule {

    @ContributesAndroidInjector(modules = [PinyinParcelModule::class])
    internal abstract fun contributesPinyinDetailActivity(): PinyinDetailActivity
}

@Module
class PinyinParcelModule {

    @Provides
    fun bindPinyinParcel(activity: PinyinDetailActivity) = PinyinParcel.out(activity.intent)
}