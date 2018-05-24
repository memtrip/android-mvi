package com.memtrip.pinyin

import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.ObservableSource

abstract class InteractActivity : AppCompatActivity() {

    fun onClickEvent(view: View) : Observable<Event> {
        return RxView.clicks(view).flatMap({
            ObservableSource<Event> { observer -> observer.onNext(ClickEvent(view.id)) }
        })
    }
}