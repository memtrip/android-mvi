package com.memtrip.pinyin.app.list.phonetic

import android.app.Application
import com.memtrip.pinyin.PresenterView
import com.memtrip.pinyin.api.DatabaseModule
import com.memtrip.pinyin.api.NetworkModule
import com.memtrip.pinyin.api.PinyinEntity
import com.memtrip.pinyin.app.list.PinyinListView
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

interface PinyinPhoneticView : PinyinListView

@Singleton
@Component(modules = [ NetworkModule::class, DatabaseModule::class ])
interface PinyinPhoneticComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): PinyinPhoneticComponent
    }

    fun inject(pinyinPhoneticFragment: PinyinPhoneticFragment)
}