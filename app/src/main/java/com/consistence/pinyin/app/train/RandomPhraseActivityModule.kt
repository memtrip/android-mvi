package com.consistence.pinyin.app.train

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RandomPhraseActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributesRandomPhraseActivity(): RandomPhraseActivity
}