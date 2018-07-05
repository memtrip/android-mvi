package com.consistence.pinyin

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Context
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

abstract class MxViewModel<VI : MxViewIntent, RA : MxRenderAction, VS : MxViewState>(
    initialState: VS,
    application: Application
) : AndroidViewModel(application) {

    private val intentsSubject: PublishSubject<VI> = PublishSubject.create()
    private val statesObservable = intentsSubject
            .compose { intents -> intents.publish { filterIntents(intents) } }
            .flatMap {
                logIntent(it)
                dispatcher(it)
            }
            .scan(initialState, { previousState: VS, renderAction: RA ->
                logRenderAction(renderAction)
                reducer(previousState, renderAction)
            })
            .map {
                logViewState(it)
                it
            }
            .distinctUntilChanged()
            .replay(1)
            .autoConnect(0)

    fun publish(intent: VI) {
        intentsSubject.onNext(intent)
    }

    internal fun processIntents(intents: Observable<VI>) {
        intents.subscribe(intentsSubject)
    }

    internal fun states(): Observable<VS> = statesObservable

    abstract fun reducer(previousState: VS, renderAction: RA): VS

    abstract fun dispatcher(intent: VI): Observable<RA>

    open fun filterIntents(intents: Observable<VI>): Observable<VI> = intents

    protected fun <T> observable(item: T): Observable<T> = Observable.just(item)

    protected fun context(): Context = getApplication()

    private fun logIntent(intent: VI) {
        Timber.d("```")
        Timber.d(">>> INTENT: $intent")
        Timber.d("```")
    }

    private fun logRenderAction(renderAction: RA) {
        Timber.d("```")
        Timber.d(">>> RENDER ACTION: $renderAction")
        Timber.d("```")
    }

    private fun logViewState(viewState: VS) {
        Timber.d("```")
        Timber.d("<<< VIEW STATE: $viewState")
        Timber.d("```")
    }
}