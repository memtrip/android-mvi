package com.consistence.pinyin.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.SearchView
import com.consistence.pinyin.R
import com.consistence.pinyin.ViewActivity
import com.consistence.pinyin.ViewModelFactory
import com.consistence.pinyin.app.list.PinyinListIntent
import com.consistence.pinyin.kit.gone
import com.consistence.pinyin.kit.visible
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.pinyin_activity.*
import javax.inject.Inject

class PinyinActivity(override var currentSearchQuery: String = "")
    : ViewActivity<PinyinIntent, PinyinState, PinyinModel, PinyinRender>(), PinyinLayout {

    @Inject lateinit var viewModelFactory: ViewModelFactory<PinyinModel>

    private lateinit var fragmentAdapter: PinyinFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pinyin_activity)

        fragmentAdapter = PinyinFragmentAdapter(
                R.id.pinyin_activity_fragment_container,
                pinyin_activity_tablayout,
                supportFragmentManager,
                this)

        pinyin_activity_tablayout.addOnTabSelectedListener(object : OnTabSelectedListenerAdapter() {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                super.onTabSelected(tab)
                model().intents.onNext(PinyinIntent.TabSelected(Page.values().get(tab!!.position)))
            }
        })

        pinyin_activity_searchview.setOnQueryTextFocusChangeListener { _ , hasFocus ->
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

        pinyin_activity_searchview_label.setOnClickListener({
            pinyin_activity_searchview.isIconified = false
        })
    }

    private fun sendSearchEvent(terms:String = "") {
        fragmentAdapter.sendIntent(PinyinListIntent.Search(terms))
    }

    override fun inject() {
        AndroidInjection.inject(this)
    }

    override fun model():PinyinModel = getViewModel(viewModelFactory)

    override fun render() = lazy { PinyinRender(this) }.value

    override fun updateSearchHint(hint: String) {
        pinyin_activity_searchview.queryHint = hint
        pinyin_activity_searchview_label.text = hint
    }

    companion object { fun newIntent(context: Context) = Intent(context, PinyinActivity::class.java) }
}