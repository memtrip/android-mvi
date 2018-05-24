package com.memtrip.pinyin.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.memtrip.pinyin.Presenter
import com.memtrip.pinyin.PresenterActivity
import com.memtrip.pinyin.R
import kotlinx.android.synthetic.main.pinyin_activity.*
import javax.inject.Inject

class PinyinActivity : PresenterActivity<PinyinView>(), PinyinView {

    @Inject lateinit var presenter: PinyinPresenter

    internal lateinit var fragmentAdapter: PinyinFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pinyin_activity)

        fragmentAdapter = PinyinFragmentAdapter(this, supportFragmentManager)
        pinyin_activity_viewpager.adapter = fragmentAdapter
        pinyin_activity_viewpager.offscreenPageLimit = 2
        pinyin_activity_tablayout.setupWithViewPager(pinyin_activity_viewpager)
    }

    override fun inject() {
        DaggerPinyinComponent.create().inject(this)
    }

    override fun presenter(): Presenter<PinyinView> = presenter

    override fun view(): PinyinView = this

    companion object {
        fun newIntent(context: Context): Intent =
                Intent(context, PinyinActivity::class.java)
    }
}