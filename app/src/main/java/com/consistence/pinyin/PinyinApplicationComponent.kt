package com.consistence.pinyin

import android.app.Application
import com.consistence.pinyin.domain.ApiModule
import com.consistence.pinyin.domain.DatabaseModule
import com.consistence.pinyin.domain.NetworkModule
import com.consistence.pinyin.app.EntryActivityModule
import com.consistence.pinyin.app.pinyin.PinyinActivityModule
import com.consistence.pinyin.app.pinyin.detail.PinyinDetailActivityModule
import com.consistence.pinyin.app.pinyin.list.PinyinCharacterFragmentModule
import com.consistence.pinyin.app.pinyin.list.PinyinEnglishFragmentModule
import com.consistence.pinyin.app.pinyin.list.PinyinPhoneticFragmentModule
import dagger.BindsInstance
import dagger.Component

import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApiModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    EntryActivityModule::class,
    PinyinActivityModule::class,
    PinyinPhoneticFragmentModule::class,
    PinyinEnglishFragmentModule::class,
    PinyinCharacterFragmentModule::class,
    PinyinDetailActivityModule::class
])
interface PinyinApplicationComponent : AndroidInjector<PinyinApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): PinyinApplicationComponent
    }
}