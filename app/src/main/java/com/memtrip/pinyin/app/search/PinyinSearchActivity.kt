package com.memtrip.pinyin.app.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView

import butterknife.BindView
import butterknife.ButterKnife
import com.memtrip.pinyin.Presenter
import com.memtrip.pinyin.PresenterActivity

import com.memtrip.pinyin.R
import com.memtrip.pinyin.api.PinyinEntity

import com.memtrip.pinyin.app.list.PinyinListAdapter
import kotlinx.android.synthetic.main.pinyin_search_activity.*
import javax.inject.Inject
import android.support.v4.view.MenuItemCompat.setOnActionExpandListener
import android.support.v4.view.MenuItemCompat.getActionView
import android.support.v4.view.MenuItemCompat.expandActionView
import android.R.menu
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.memtrip.pinyin.SearchEvent


class PinyinSearchActivity : PresenterActivity<PinyinSearchView>(), PinyinSearchView {

    @Inject
    lateinit var presenter: PinyinSearchPresenter

    @BindView(R.id.pinyin_search_activity_recyclerview)
    lateinit var recyclerView: RecyclerView

    private lateinit var adapter: PinyinListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pinyin_search_activity)
        ButterKnife.bind(this)

        adapter = PinyinListAdapter(this, presenter.adapterEvent())
        recyclerView.adapter = adapter

        setSupportActionBar(pinyin_search_activity_toolbar)
        supportActionBar!!.setTitle("")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val myActionMenuItem = menu.findItem(R.id.search_menu_search)
        myActionMenuItem.expandActionView()
        val searchView = myActionMenuItem.getActionView() as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(terms: String): Boolean {
                sendEvent(SearchEvent(R.id.pinyin_search_activity_toolbar, terms))
                return false
            }
        })

        myActionMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {
                return false
            }

            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                finish()
                return false
            }
        })

        return true
    }

    override fun inject() {
        DaggerPinyinSearchComponent
                .builder()
                .application(application)
                .build()
                .inject(this)
    }

    override fun presenter(): Presenter<PinyinSearchView> = presenter

    override fun view(): PinyinSearchView = this

    override fun populate(pinyin: List<PinyinEntity>) {
        adapter.clear()
        adapter.populate(pinyin)
    }

    override fun error() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun newIntent(context: Context): Intent =
                Intent(context, PinyinSearchActivity::class.java)
    }
}