package com.consistence.pinyin.app

import android.os.Bundle
import com.consistence.pinyin.R
import com.consistence.pinyin.ViewActivity
import com.consistence.pinyin.ViewModelFactory
import com.consistence.pinyin.kit.gone
import com.consistence.pinyin.kit.visible

import com.jakewharton.rxbinding2.view.RxView

import kotlinx.android.synthetic.main.entry_activity.*
import kotlinx.android.synthetic.main.kit_error_retry.view.*

import javax.inject.Inject

class EntryActivity : ViewActivity<EntryIntent, EntryState, EntryModel, EntryRender>(), EntryLayout {

    @Inject lateinit var viewModelFactory: ViewModelFactory<EntryModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entry_activity)

        RxView.clicks(entry_activity_error.kit_error_retry_button)
                .map({ EntryIntent.Init })
                .subscribe(model().intents)
    }

    override fun showProgress() {
        entry_activity_progress.visible()
        entry_activity_error.gone()
    }

    override fun hideProgress() {
        entry_activity_progress.gone()
    }

    override fun navigateToPinyin() {
        startActivity(PinyinActivity.newIntent(this))
        finish()
    }

    override fun error() {
        entry_activity_progress.gone()
        entry_activity_error.visible()
    }

    override fun inject() {
        DaggerEntryComponent
                .builder()
                .application(application)
                .build()
                .inject(this)
    }

    override fun render() = lazy { EntryRender(this) }.value

    override fun model(): EntryModel = getViewModel(viewModelFactory)

    override fun initIntent(): EntryIntent? = EntryIntent.Init
}