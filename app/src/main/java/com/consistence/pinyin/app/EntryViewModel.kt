package com.consistence.pinyin.app

import android.app.Application
import com.consistence.pinyin.domain.pinyin.db.CountPinyin
import com.consistence.pinyin.domain.pinyin.FetchAndSavePinyin
import com.memtrip.mxandroid.MxViewModel
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class EntryViewModel @Inject internal constructor(
    private val fetchAndSavePinyin: FetchAndSavePinyin,
    private val countPinyin: CountPinyin,
    application: Application
) : MxViewModel<EntryIntent, EntryRenderAction, EntryViewState>(
    EntryViewState(EntryViewState.View.OnProgress),
    application
) {

    private fun getPinyin(): Observable<EntryRenderAction> {
        return countPinyin.count()
                .flatMap<EntryRenderAction> { count ->
                    if (count > 0) {
                        Single.just(EntryRenderAction.OnPinyinLoaded)
                    } else {
                        fetchAndSavePinyin
                            .save()
                            .map { EntryRenderAction.OnPinyinLoaded }
                    }
                }
            .onErrorReturnItem(EntryRenderAction.OnError)
                .toObservable()
                .startWith(EntryRenderAction.OnProgress)
    }

    override fun dispatcher(intent: EntryIntent): Observable<EntryRenderAction> = when (intent) {
        EntryIntent.Init, EntryIntent.Retry -> getPinyin()
    }

    override fun reducer(previousState: EntryViewState, renderAction: EntryRenderAction): EntryViewState = when (renderAction) {
        EntryRenderAction.OnProgress -> previousState.copy(view = EntryViewState.View.OnProgress)
        EntryRenderAction.OnError -> previousState.copy(view = EntryViewState.View.OnError)
        EntryRenderAction.OnPinyinLoaded -> previousState.copy(EntryViewState.View.OnPinyinLoaded)
    }

    override fun filterIntents(intents: Observable<EntryIntent>): Observable<EntryIntent> = Observable.merge(
            intents.ofType(EntryIntent.Init.javaClass).take(1),
            intents.filter {
                !EntryIntent.Init.javaClass.isInstance(it)
            }
    )
}