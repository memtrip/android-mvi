package com.consistence.pinyin

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context

import android.support.v4.app.Fragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.disposables.CompositeDisposable

abstract class MxViewFragment<VI : MxViewIntent, RA : MxRenderAction, VS : MxViewState, VL : MxViewLayout> : Fragment() {

    private val d = CompositeDisposable()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        inject()
    }

    override fun onStart() {
        super.onStart()

        d.add(

                model()
                        .states()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ state -> render().layout(layout(), state) })
        )

        model().processIntents(intents())
    }

    override fun onStop() {
        super.onStop()
        d.clear()
    }

    abstract fun intents(): Observable<VI>

    abstract fun inject()

    abstract fun layout(): VL

    abstract fun model(): MxViewModel<VI, RA, VS>

    abstract fun render(): MxViewRenderer<VL, VS>

    protected inline fun <reified T : ViewModel> getViewModel(viewModelFactory: ViewModelProvider.Factory): T =
            ViewModelProviders.of(this, viewModelFactory)[T::class.java]
}