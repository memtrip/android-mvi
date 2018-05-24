package com.memtrip.pinyin.app.favourites

import android.app.Application
import com.memtrip.pinyin.PresenterView
import com.memtrip.pinyin.api.DatabaseModule
import com.memtrip.pinyin.app.list.PinyinListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

interface PinyinFavouriteView : PresenterView {

}

@Singleton
@Component(modules = [ DatabaseModule::class ])
interface PinyinFavouriteComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): PinyinFavouriteComponent
    }

    fun inject(pinyinFavouriteFragment: PinyinFavouriteFragment)
}