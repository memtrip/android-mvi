package com.memtrip.pinyin.app.list.character

import android.app.Application
import com.memtrip.pinyin.PresenterView
import com.memtrip.pinyin.api.DatabaseModule
import com.memtrip.pinyin.api.NetworkModule
import com.memtrip.pinyin.api.PinyinEntity
import com.memtrip.pinyin.app.list.PinyinListView
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

interface PinyinCharacterView : PinyinListView

@Singleton
@Component(modules = [ NetworkModule::class, DatabaseModule::class ])
interface PinyinCharacterComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): PinyinCharacterComponent
    }

    fun inject(pinyinCharacterFragment: PinyinCharacterFragment)
}