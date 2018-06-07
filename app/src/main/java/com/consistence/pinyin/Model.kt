package com.consistence.pinyin

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

import io.reactivex.subjects.PublishSubject

abstract class Model<I : ViewIntent, S : ViewState> : ViewModel() {

    val intents: PublishSubject<I> = PublishSubject.create<I>()

    private val disposable = CompositeDisposable()

    internal fun d(disposable: Disposable) {
        this.disposable.add(disposable)
    }

    internal fun states(): Observable<S> = intents.flatMap({ processor(it) })

    internal fun publish(intent: I) {
        intents.onNext(intent)
    }

    abstract fun processor(intent: I): Observable<S>
}