package com.consistence.pinyin.app.study

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CreateStudyActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributesCreateStudyActivity(): CreateStudyActivity
}