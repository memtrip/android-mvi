package com.consistence.pinyin

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

abstract class MxViewActivity<VI : MxViewIntent, RA : MxRenderAction, VS : MxViewState, VL : MxViewLayout> : AppCompatActivity() {

    private val d = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else ->
            super.onOptionsItemSelected(item)
    }

    abstract fun intents(): Observable<VI>

    abstract fun inject()

    abstract fun layout(): VL

    abstract fun model(): MxViewModel<VI, RA, VS>

    abstract fun render(): MxViewRenderer<VL, VS>

    protected inline fun <reified T : ViewModel> getViewModel(viewModelFactory: ViewModelProvider.Factory): T =
            ViewModelProviders.of(this, viewModelFactory)[T::class.java]
}