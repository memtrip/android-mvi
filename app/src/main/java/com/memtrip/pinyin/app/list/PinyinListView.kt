package com.memtrip.pinyin.app.list

import android.app.Application
import com.memtrip.pinyin.PresenterView
import com.memtrip.pinyin.api.ApiModule
import com.memtrip.pinyin.api.DatabaseModule
import com.memtrip.pinyin.api.NetworkModule
import com.memtrip.pinyin.api.PinyinEntity
import com.memtrip.pinyin.app.EntryActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

interface PinyinListView : PresenterView {
    fun populate(pinyin: List<PinyinEntity>)
    fun error()
}

@Singleton
@Component(modules = [ NetworkModule::class, DatabaseModule::class ])
interface PinyinListComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): PinyinListComponent
    }


    fun inject(pinyinListFragment: PinyinListFragment)
}