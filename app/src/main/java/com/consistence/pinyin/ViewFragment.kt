package com.consistence.pinyin

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle

import android.support.v4.app.Fragment

import io.reactivex.disposables.CompositeDisposable

abstract class ViewFragment<I : ViewIntent, S : ViewState, L : ViewLayout> : Fragment() {

    private val d = CompositeDisposable()
    private var init = false

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        inject()
        d.add(model().states().subscribe({ render().state(layout(), it) }))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!init) {
            init = true
            initIntent()?.let { model().publish(it) }
        } else {
            if (savedInstanceState != null) {
                restoreStateIntent(savedInstanceState)?.let { model().publish(it) }
            }
        }
    }

    protected inline fun <reified T : ViewModel> getViewModel(viewModelFactory: ViewModelProvider.Factory): T =
            ViewModelProviders.of(this, viewModelFactory)[T::class.java]

    abstract fun inject()

    abstract fun layout(): L

    abstract fun model(): Model<I, S>

    abstract fun render() : ViewRender<L, S>

    open fun initIntent() : I? = ViewIntent.NONE

    open fun restoreStateIntent(savedInstanceState: Bundle) : I? = ViewIntent.NONE
}