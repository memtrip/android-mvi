package com.memtrip.pinyin.app

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.os.Handler

import com.memtrip.pinyin.*
import com.memtrip.pinyin.kit.gone
import com.memtrip.pinyin.kit.visible
import javax.inject.Inject

import kotlinx.android.synthetic.main.entry_activity.*
import kotlinx.android.synthetic.main.kit_error_retry.view.*

class EntryActivity : PresenterActivity<EntryView>(), EntryView {

    @Inject lateinit var presenter: EntryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerEntryComponent.create().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entry_activity)

        onClickEvent(entry_activity_error.kit_error_retry_button)
                .subscribe(presenter().event())
    }

    override fun presenter(): Presenter<EntryView> {
        return presenter
    }

    override fun view(): EntryView {
        return this
    }

    override fun navigateToPinyin() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgress() {
        entry_activity_progress.visible()
        entry_activity_error.gone()
    }

    override fun error() {
        entry_activity_progress.gone()
        entry_activity_error.visible()
    }
}