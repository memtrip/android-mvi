package com.consistence.pinyin.legacy

import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.ObservableSource

interface Interact {
    fun onClickEvent(view: View) : Observable<Event>
}

class RxInteract : Interact {
    override fun onClickEvent(view: View) : Observable<Event> {
        return RxView.clicks(view).flatMap({
            ObservableSource<Event> {
                it.onNext(ClickEvent(view.id))
            }
        })
    }
}