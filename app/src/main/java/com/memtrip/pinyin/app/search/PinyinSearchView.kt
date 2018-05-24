package com.memtrip.pinyin.app.search

import android.app.Application
import com.memtrip.pinyin.PresenterView
import com.memtrip.pinyin.api.DatabaseModule
import com.memtrip.pinyin.api.NetworkModule
import com.memtrip.pinyin.api.PinyinEntity

import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

interface PinyinSearchView : PresenterView {
    fun populate(pinyin: List<PinyinEntity>)
    fun error()
}

@Singleton
@Component(modules = [ NetworkModule::class, DatabaseModule::class ])
interface PinyinSearchComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): PinyinSearchComponent
    }

    fun inject(pinyinSearchActivity: PinyinSearchActivity)
}