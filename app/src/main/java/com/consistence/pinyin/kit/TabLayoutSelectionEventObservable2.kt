package com.consistence.pinyin.kit

import android.os.Looper
import com.google.android.material.tabs.TabLayout
import com.jakewharton.rxbinding2.support.design.widget.TabLayoutSelectionEvent
import com.jakewharton.rxbinding2.support.design.widget.TabLayoutSelectionReselectedEvent
import com.jakewharton.rxbinding2.support.design.widget.TabLayoutSelectionSelectedEvent
import com.jakewharton.rxbinding2.support.design.widget.TabLayoutSelectionUnselectedEvent
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import io.reactivex.disposables.Disposables

internal class TabLayoutSelectionEventObservable2(val view: TabLayout) : Observable<TabLayoutSelectionEvent>() {

    override fun subscribeActual(observer: Observer<in TabLayoutSelectionEvent>) {
        if (checkMainThread(observer)) {
            val listener = Listener(this.view, observer)
            observer.onSubscribe(listener)
            this.view.addOnTabSelectedListener(listener)
            val index = this.view.selectedTabPosition
            if (index != -1) {
                observer.onNext(TabLayoutSelectionSelectedEvent.create(this.view, this.view.getTabAt(index)!!))
            }
        }
    }

    internal inner class Listener(private val tabLayout: TabLayout, private val observer: Observer<in TabLayoutSelectionEvent>) : MainThreadDisposable(), TabLayout.OnTabSelectedListener {

        override fun onTabSelected(tab: TabLayout.Tab) {
            if (!this.isDisposed) {
                this.observer.onNext(TabLayoutSelectionSelectedEvent.create(view, tab))
            }

        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
            if (!this.isDisposed) {
                this.observer.onNext(TabLayoutSelectionUnselectedEvent.create(view, tab))
            }

        }

        override fun onTabReselected(tab: TabLayout.Tab) {
            if (!this.isDisposed) {
                this.observer.onNext(TabLayoutSelectionReselectedEvent.create(view, tab))
            }

        }

        override fun onDispose() {
            this.tabLayout.removeOnTabSelectedListener(this)
        }
    }

    fun checkMainThread(observer: Observer<*>): Boolean {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            observer.onSubscribe(Disposables.empty())
            observer.onError(IllegalStateException(
                "Expected to be called on the main thread but was " + Thread.currentThread().name))
            return false
        }
        return true
    }

}
