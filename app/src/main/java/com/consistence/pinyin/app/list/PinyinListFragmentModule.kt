package com.consistence.pinyin.app.list

import com.consistence.pinyin.app.list.character.PinyinCharacterFragment
import com.consistence.pinyin.app.list.english.PinyinEnglishFragment
import com.consistence.pinyin.app.list.phonetic.PinyinPhoneticFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PinyinPhoneticFragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributesPinyinPhoneticFragment(): PinyinPhoneticFragment
}

@Module
abstract class PinyinEnglishFragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributesPinyinEnglishFragment(): PinyinEnglishFragment
}

@Module
abstract class PinyinCharacterFragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributesPinyinCharacterFragment(): PinyinCharacterFragment
}