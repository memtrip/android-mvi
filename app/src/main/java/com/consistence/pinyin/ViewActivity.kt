package com.consistence.pinyin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else ->
                return super.onOptionsItemSelected(item)
        }
    }

    abstract fun inject()

    abstract fun model(): M

    abstract fun render() : R

    open fun initIntent() : I? = ViewIntent.NONE
}