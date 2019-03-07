package com.consistence.pinyin.app.study

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class StudyActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributesStudyActivity(): StudyActivity
}