package com.memtrip.pinyin

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

abstract class Presenter<V : PresenterView> {

    val d = CompositeDisposable()

    lateinit var view: V

    fun attach(view: V) {
        this.view = view
    }

    open fun first() {

    }

    open fun start() {

    }

    open fun resume() {

    }

    open fun pause() {

    }

    open fun stop() {
        d.dispose()
    }

    open fun event() : Consumer<Event> =
        throw IllegalStateException("To use event() you must implement it in the Presenter")

    fun i(disposable: Disposable) = d.add(disposable)
}