package com.consistence.pinyin.legacy

import android.content.Context
import android.support.v4.app.Fragment

abstract class PresenterFragment<V : PresenterView>(interact: Interact = RxInteract())
    : Fragment(), PresenterView, Interact by interact {

    private var init = false

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        inject()
        presenter().attach(view())
    }

    override fun onStart() {
        super.onStart()

        if (!init) {
            init = true
            presenter().first()
        }

        presenter().start()
    }

    override fun onResume() {
        super.onResume()
        presenter().resume()
    }

    override fun onPause() {
        super.onPause()
        presenter().pause()
    }

    override fun onStop() {
        super.onStop()
        presenter().stop()
    }

    override fun sendEvent(event: Event) {
        presenter().event().accept(event)
    }

    override fun showProgress() {
        TODO("not implemented")
    }

    override fun hideProgress() {
        TODO("not implemented")
    }

    override fun close() {

    }

    override fun context() = activity as Context

    abstract fun inject()

    abstract fun presenter(): Presenter<V>

    abstract fun view(): V
}