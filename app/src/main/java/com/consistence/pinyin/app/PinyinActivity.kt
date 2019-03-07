package com.consistence.pinyin.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import com.consistence.pinyin.R
import com.consistence.pinyin.ViewModelFactory
import com.consistence.pinyin.app.list.PinyinListIntent
import com.consistence.pinyin.kit.RxTabLayout2
import com.consistence.pinyin.kit.gone
import com.consistence.pinyin.kit.visible
import com.memtrip.mxandroid.MxViewActivity
import dagger.android.AndroidInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.pinyin_activity.*
import javax.inject.Inject

class PinyinActivity(
    override var currentSearchQuery: String = ""
) : MxViewActivity<PinyinIntent, PinyinRenderAction, PinyinViewState, PinyinLayout>(), PinyinLayout {

    @Inject lateinit var viewModelFactory: ViewModelFactory<PinyinViewModel>
    @Inject lateinit var render: PinyinRenderer

    private lateinit var fragmentAdapter: PinyinFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pinyin_activity)

        fragmentAdapter = PinyinFragmentAdapter(
            R.id.pinyin_activity_fragment_container,
            pinyin_activity_tablayout,
            supportFragmentManager,
            this)

        pinyin_activity_searchview.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                pinyin_activity_searchview_label.gone()
            }
        }

        pinyin_activity_searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p: String): Boolean { return false }

            override fun onQueryTextChange(terms: String): Boolean {
                currentSearchQuery = terms
                sendSearchEvent(terms)
                return true
            }
        })

        pinyin_activity_searchview.setOnCloseListener {
            sendSearchEvent()
            pinyin_activity_searchview_label.visible()
            false
        }

        pinyin_activity_searchview_label.setOnClickListener {
            pinyin_activity_searchview.isIconified = false
        }

        pinyin_activity_study_button.setOnClickListener {

        }
    }

    override fun intents(): Observable<PinyinIntent> = Observable.merge(
        Observable.just(PinyinIntent.Init),
        RxTabLayout2
            .selectionEvents(pinyin_activity_tablayout)
            .map { PinyinIntent.TabSelected(Page.values()[it.tab().position]) }
    )

    private fun sendSearchEvent(terms: String = "") {
        fragmentAdapter.sendIntent(PinyinListIntent.Search(terms))
    }

    override fun inject() {
        AndroidInjection.inject(this)
    }

    override fun layout(): PinyinLayout = this

    override fun model(): PinyinViewModel = getViewModel(viewModelFactory)

    override fun render(): PinyinRenderer = render

    override fun updateSearchHint(hint: String) {
        pinyin_activity_searchview.queryHint = hint
        pinyin_activity_searchview_label.text = hint
    }

    companion object { fun newIntent(context: Context) = Intent(context, PinyinActivity::class.java) }
}