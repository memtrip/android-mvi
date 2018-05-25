package com.memtrip.pinyin.app.favourites

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.memtrip.pinyin.Presenter
import com.memtrip.pinyin.PresenterFragment
import com.memtrip.pinyin.R
import com.memtrip.pinyin.api.PinyinEntity
import com.memtrip.pinyin.app.detail.PinyinDetailActivity
import com.memtrip.pinyin.app.list.PinyinPhoneticAdapter
import javax.inject.Inject

class PinyinCharacterFragment : PresenterFragment<PinyinCharacterView>(), PinyinCharacterView {

    @Inject lateinit var presenter: PinyinCharacterPresenter

    @BindView(R.id.pinyin_character_fragment_recyclerview)
    lateinit var recyclerView: RecyclerView

    private lateinit var adapter: PinyinCharacterAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.pinyin_character_fragment, container, false)
        ButterKnife.bind(this, view)
        adapter = PinyinCharacterAdapter(context!!, presenter.adapterEvent())
        recyclerView.adapter = adapter
        return view
    }

    override fun inject() {
        DaggerPinyinCharacterComponent
                .builder()
                .application(activity!!.application)
                .build()
                .inject(this)
    }

    override fun presenter(): Presenter<PinyinCharacterView> = presenter

    override fun view(): PinyinCharacterView  = this

    companion object {
        fun newInstance() : PinyinCharacterFragment = PinyinCharacterFragment()
    }

    override fun populate(pinyin: List<PinyinEntity>) {
        adapter.clear()
        adapter.populate(pinyin)
    }

    override fun navigateToPinyinDetails(pinyinEntity: PinyinEntity) {
        startActivity(PinyinDetailActivity.newIntent(context!!, pinyinEntity))
    }

    override fun error() {

    }
}