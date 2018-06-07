package com.consistence.pinyin

import android.content.Context
import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable

abstract class ViewFragment<I : ViewIntent, S : ViewState, M : Model<I, S>, R : ViewRender<S>> : Fragment() {

    private val d = CompositeDisposable()
    private var init = false

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        inject()
        d.add(model().states().subscribe(render()::state))
    }

    override fun onStart() {
        super.onStart()

        if (!init) {
            init = true
            initIntent()?.let { model().publish(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        init = false
    }

    abstract fun inject()

    abstract fun model(): M

    abstract fun render() : R

    open fun initIntent() : I? = ViewIntent.NONE
}