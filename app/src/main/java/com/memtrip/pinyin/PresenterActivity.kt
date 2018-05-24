package com.memtrip.pinyin

import android.os.Bundle

abstract class PresenterActivity<V : PresenterView> : InteractActivity(), PresenterView {

    var init = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter().attach(view())
    }

    override fun onStart() {
        super.onStart()

        if (!init) {
            init = true
            presenter().first()
        } else {
            presenter().start()
        }
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

    abstract fun presenter(): Presenter<V>

    abstract fun view(): V
}