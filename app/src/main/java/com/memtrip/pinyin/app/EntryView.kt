package com.memtrip.pinyin.app

import android.app.Application
import com.memtrip.pinyin.PresenterView
import com.memtrip.pinyin.api.ApiModule
import com.memtrip.pinyin.api.DatabaseModule
import com.memtrip.pinyin.api.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

interface EntryView : PresenterView {
    fun navigateToPinyin()
    fun error()
}

@Singleton
@Component(modules = [ NetworkModule::class, DatabaseModule::class, ApiModule::class ])
interface EntryComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): EntryComponent
    }


    fun inject(entryActivity: EntryActivity)
}