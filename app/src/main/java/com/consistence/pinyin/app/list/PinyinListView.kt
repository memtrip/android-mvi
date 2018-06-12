package com.consistence.pinyin.app.list

import com.consistence.pinyin.ViewIntent
import com.consistence.pinyin.ViewLayout
import com.consistence.pinyin.ViewRender
import com.consistence.pinyin.ViewState
import com.consistence.pinyin.api.PinyinEntity
import com.consistence.pinyin.app.list.character.PinyinCharacterFragment
import com.consistence.pinyin.app.list.english.PinyinEnglishFragment
import com.consistence.pinyin.app.list.phonetic.PinyinPhoneticFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Inject

sealed class PinyinListIntent : ViewIntent {
    data class Search(val terms: String) : PinyinListIntent()
    data class SelectItem(val pinyin: PinyinEntity) : PinyinListIntent()
    data class PlayAudio(val audioSrc: String) : PinyinListIntent()
}

sealed class PinyinListState : ViewState {
    data class Populate(val pinyinList: List<PinyinEntity>) : PinyinListState()
    data class NavigateToDetails(val pinyin: PinyinEntity) : PinyinListState()
    data class PlayAudio(val audioSrc: String) : PinyinListState()
    object OnError : PinyinListState()
}

interface PinyinListLayout : ViewLayout {
    fun populate(pinyin: List<PinyinEntity>)
    fun navigateToPinyinDetails(pinyinEntity: PinyinEntity)
    fun error()
    fun playAudio(audioSrc: String)
}

class PinyinListRender @Inject internal constructor() : ViewRender<PinyinListLayout, PinyinListState> {
    override fun state(layout: PinyinListLayout, state: PinyinListState) = when(state) {
        is PinyinListState.Populate ->
            layout.populate(state.pinyinList)
        is PinyinListState.NavigateToDetails ->
            layout.navigateToPinyinDetails(state.pinyin)
        PinyinListState.OnError ->
            layout.error()
        is PinyinListState.PlayAudio ->
            layout.playAudio(state.audioSrc)
    }
}

@Module
abstract class PinyinPhoneticFragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributesPinyinPhoneticFragment() : PinyinPhoneticFragment
}

@Module
abstract class PinyinEnglishFragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributesPinyinEnglishFragment() : PinyinEnglishFragment
}

@Module
abstract class PinyinCharacterFragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributesPinyinCharacterFragment() : PinyinCharacterFragment
}