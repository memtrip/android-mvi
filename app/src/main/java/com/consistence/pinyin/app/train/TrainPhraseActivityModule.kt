package com.consistence.pinyin.app.train

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TrainPhraseActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributesTrainPhraseActivity(): TrainPhraseActivity
}