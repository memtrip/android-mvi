package com.consistence.pinyin.app

import android.os.Bundle
import com.consistence.pinyin.MxViewActivity
import com.consistence.pinyin.R
import com.consistence.pinyin.ViewModelFactory
import com.consistence.pinyin.kit.gone
import com.consistence.pinyin.kit.visible
import com.jakewharton.rxbinding2.view.RxView
import dagger.android.AndroidInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.entry_activity.*
import kotlinx.android.synthetic.main.kit_error_retry.view.*
import javax.inject.Inject

class EntryActivity : MxViewActivity<EntryIntent, EntryRenderAction, EntryViewState, EntryLayout>(), EntryLayout {

    @Inject lateinit var viewModelFactory: ViewModelFactory<EntryViewModel>
    @Inject lateinit var render: EntryRenderer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entry_activity)
    }

    override fun intents(): Observable<EntryIntent> = Observable.merge(
            Observable.just(EntryIntent.Init),
            RxView.clicks(entry_activity_error.kit_error_retry_button)
                    .map({ EntryIntent.Retry })
    )

    override fun showProgress() {
        entry_activity_progress.visible()
        entry_activity_error.gone()
    }

    override fun navigateToPinyin() {
        entry_activity_progress.gone()
        startActivity(PinyinActivity.newIntent(this))
        finish()
    }

    override fun showError() {
        entry_activity_progress.gone()
        entry_activity_error.visible()
    }

    override fun inject() {
        AndroidInjection.inject(this)
    }

    override fun layout(): EntryLayout = this

    override fun render(): EntryRenderer = render

    override fun model(): EntryViewModel = getViewModel(viewModelFactory)
}