package com.consistence.pinyin.app

import android.os.Bundle
import com.consistence.pinyin.Presenter
import com.consistence.pinyin.PresenterActivity
import com.consistence.pinyin.R
import com.consistence.pinyin.kit.gone
import com.consistence.pinyin.kit.visible
import kotlinx.android.synthetic.main.entry_activity.*
import kotlinx.android.synthetic.main.kit_error_retry.view.*
import javax.inject.Inject

class EntryActivity : PresenterActivity<EntryView>(), EntryView {

    @Inject lateinit var presenter: EntryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entry_activity)

        onClickEvent(entry_activity_error.kit_error_retry_button)
                .subscribe(presenter().event())
    }

    override fun presenter(): Presenter<EntryView> = presenter

    override fun view(): EntryView = this

    override fun inject() {
        DaggerEntryComponent
                .builder()
                .application(application)
                .build()
                .inject(this)
    }

    override fun navigateToPinyin() {
        startActivity(PinyinActivity.newIntent(this))
    }

    override fun showProgress() {
        entry_activity_progress.visible()
        entry_activity_error.gone()
    }

    override fun hideProgress() {
        entry_activity_progress.gone()
    }

    override fun error() {
        entry_activity_progress.gone()
        entry_activity_error.visible()
    }
}