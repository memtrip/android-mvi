package com.consistence.pinyin.app

import android.app.Application
import com.consistence.pinyin.MxViewModel
import com.consistence.pinyin.R
import io.reactivex.Observable
import javax.inject.Inject

class PinyinViewModel @Inject internal constructor(
    application: Application
) : MxViewModel<PinyinIntent, PinyinRenderAction, PinyinViewState>(
    PinyinViewState(),
    application
) {

    override fun dispatcher(intent: PinyinIntent): Observable<PinyinRenderAction> = when (intent) {
        PinyinIntent.Init ->
            observable(PinyinRenderAction.SearchHint(
                    context().getString(R.string.pinyin_activity_search_phonetic_hint)))
        is PinyinIntent.TabSelected -> {
            observable(
                    when (intent.page) {
                        Page.PHONETIC -> PinyinRenderAction.SearchHint(
                                context().getString(R.string.pinyin_activity_search_phonetic_hint))
                        Page.ENGLISH -> PinyinRenderAction.SearchHint(
                                context().getString(R.string.pinyin_activity_search_english_hint))
                        Page.CHARACTER -> PinyinRenderAction.SearchHint(
                                context().getString(R.string.pinyin_activity_search_character_hint))
                    }
            )
        }
    }

    override fun reducer(previousState: PinyinViewState, renderAction: PinyinRenderAction) = when (renderAction) {
        is PinyinRenderAction.SearchHint -> PinyinViewState(renderAction.hint)
    }

    override fun filterIntents(intents: Observable<PinyinIntent>): Observable<PinyinIntent> = Observable.merge(
            intents.ofType(PinyinIntent.Init.javaClass).take(1),
            intents.filter {
                !PinyinIntent.Init.javaClass.isInstance(it)
            }
    )
}