package com.consistence.pinyin.app.list

import android.app.Application
import com.consistence.pinyin.domain.pinyin.Pinyin
import com.memtrip.mxandroid.MxViewModel
import io.reactivex.Observable
import io.reactivex.Single

abstract class PinyinListViewModel(
    application: Application
) : MxViewModel<PinyinListIntent, PinyinListRenderAction, PinyinListViewState>(
    PinyinListViewState(view = PinyinListViewState.View.Idle),
    application
) {

    abstract fun search(terms: String = defaultSearch): Single<List<Pinyin>>

    abstract val defaultSearch: String

    private fun searchQuery(terms: String = defaultSearch): Observable<PinyinListRenderAction> {
        return search(if (terms.isEmpty()) defaultSearch else terms)
                .map<PinyinListRenderAction> { PinyinListRenderAction.Populate(it) }
                .onErrorReturnItem(PinyinListRenderAction.OnError)
                .toObservable()
    }

    override fun dispatcher(intent: PinyinListIntent): Observable<PinyinListRenderAction> = when (intent) {
        is PinyinListIntent.Init -> searchQuery(intent.terms)
        is PinyinListIntent.Search -> searchQuery(intent.terms)
        is PinyinListIntent.PlayAudio -> Observable.just(PinyinListRenderAction.PlayAudio(intent.audioSrc))
        is PinyinListIntent.SelectItem -> Observable.just(PinyinListRenderAction.SelectItem(intent.pinyin))
        PinyinListIntent.Idle -> Observable.just(PinyinListRenderAction.Idle)
    }

    override fun reducer(previousState: PinyinListViewState, renderAction: PinyinListRenderAction) = when (renderAction) {
        is PinyinListRenderAction.Populate -> previousState.copy(view = PinyinListViewState.View.Populate(renderAction.pinyinList))
        PinyinListRenderAction.OnError -> previousState.copy(view = PinyinListViewState.View.OnError)
        is PinyinListRenderAction.PlayAudio -> previousState.copy(view = PinyinListViewState.View.PlayAudio(renderAction.audioSrc))
        is PinyinListRenderAction.SelectItem -> previousState.copy(view = PinyinListViewState.View.SelectItem(renderAction.pinyin))
        PinyinListRenderAction.Idle -> previousState.copy(view = PinyinListViewState.View.Idle)
    }

    override fun filterIntents(intents: Observable<PinyinListIntent>): Observable<PinyinListIntent> = Observable.merge(
            intents.ofType(PinyinListIntent.Init::class.java).take(1),
            intents.filter {
                !PinyinListIntent.Init::class.java.isInstance(it)
            }
    )
}