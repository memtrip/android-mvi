package com.consistence.pinyin.app

import android.app.Application
import com.consistence.pinyin.PresenterView
import com.consistence.pinyin.api.ApiModule
import com.consistence.pinyin.api.DatabaseModule
import com.consistence.pinyin.api.NetworkModule
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