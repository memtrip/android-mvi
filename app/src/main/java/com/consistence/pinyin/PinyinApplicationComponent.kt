package com.consistence.pinyin

import android.app.Application
import com.consistence.pinyin.api.ApiModule
import com.consistence.pinyin.api.DatabaseModule
import com.consistence.pinyin.api.NetworkModule
import com.consistence.pinyin.app.EntryActivityModule
import com.consistence.pinyin.app.PinyinActivityModule
import com.consistence.pinyin.app.list.PinyinCharacterFragmentModule
import com.consistence.pinyin.app.list.PinyinEnglishFragmentModule
import com.consistence.pinyin.app.list.PinyinPhoneticFragmentModule
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
    PinyinCharacterFragmentModule::class
])
interface PinyinApplicationComponent : AndroidInjector<PinyinApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): PinyinApplicationComponent
    }
}