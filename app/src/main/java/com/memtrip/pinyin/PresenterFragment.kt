package com.memtrip.pinyin

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class PresenterFragment<V : PresenterView>(interact: Interact = RxInteract())
    : Fragment(), PresenterView, Interact by interact {

    private lateinit var presenterView: PresenterView;

    private var init = false

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        inject()
        presenter().attach(view())
        presenterView = context as PresenterView
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
        presenterView.close()
    }

    override fun context() = activity as Context

    abstract fun inject()

    abstract fun presenter(): Presenter<V>

    abstract fun view(): V
}