package com.consistence.pinyin.app

import android.app.Application
import com.consistence.pinyin.ViewIntent
import com.consistence.pinyin.ViewLayout
import com.consistence.pinyin.ViewRender
import com.consistence.pinyin.ViewState
import com.consistence.pinyin.api.ApiModule
import com.consistence.pinyin.api.DatabaseModule
import com.consistence.pinyin.api.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

sealed class EntryIntent : ViewIntent {
    object OnProgress : EntryIntent()
    object Init : EntryIntent()
    object Retry : EntryIntent()
}

sealed class EntryState : ViewState {
    object OnProgress : EntryState()
    object OnError: EntryState()
    object OnPinyinLoaded : EntryState()
}

interface EntryLayout : ViewLayout {
    fun showProgress()
    fun hideProgress()
    fun navigateToPinyin()
    fun error()
}

class EntryRender(private val entryLayout: EntryLayout) : ViewRender<EntryState> {
    override fun state(state: EntryState) = when(state) {
        EntryState.OnProgress -> {
            entryLayout.showProgress()
        }
        EntryState.OnError -> {
            entryLayout.hideProgress()
            entryLayout.error()
        }
        EntryState.OnPinyinLoaded -> {
            entryLayout.hideProgress()
            entryLayout.navigateToPinyin()
        }
    }
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