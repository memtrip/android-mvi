package com.consistence.pinyin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

abstract class ViewActivity<I : ViewIntent, S : ViewState, M : Model<I, S>, R : ViewRender<S>> : AppCompatActivity() {

    private val d = CompositeDisposable()
    private var init = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    abstract fun inject()

    abstract fun model(): M

    abstract fun render() : R

    open fun initIntent() : I? = ViewIntent.NONE
}