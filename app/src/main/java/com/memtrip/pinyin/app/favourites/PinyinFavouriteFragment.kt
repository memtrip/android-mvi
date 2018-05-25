package com.memtrip.pinyin.app.favourites

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.memtrip.pinyin.Presenter
import com.memtrip.pinyin.PresenterFragment
import com.memtrip.pinyin.R
import javax.inject.Inject

class PinyinFavouriteFragment : PresenterFragment<PinyinFavouriteView>(), PinyinFavouriteView {

    @Inject lateinit var presenter: PinyinFavouritePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.pinyin_favourite_fragment, container, false);
        return view
    }

    override fun inject() {
        DaggerPinyinFavouriteComponent
                .builder()
                .application(activity!!.application)
                .build()
                .inject(this)
    }

    override fun presenter(): Presenter<PinyinFavouriteView> = presenter

    override fun view(): PinyinFavouriteView  = this

    companion object {
        fun newInstance() : PinyinFavouriteFragment = PinyinFavouriteFragment()
    }
}