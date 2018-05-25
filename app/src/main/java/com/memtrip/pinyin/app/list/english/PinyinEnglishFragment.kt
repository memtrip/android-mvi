package com.memtrip.pinyin.app.list.english

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
import javax.inject.Inject

class PinyinEnglishFragment : PresenterFragment<PinyinEnglishView>(), PinyinEnglishView {

    @Inject lateinit var presenter: PinyinEnglishPresenter

    @BindView(R.id.pinyin_english_fragment_recyclerview)
    lateinit var recyclerView: RecyclerView

    private lateinit var adapter: PinyinEnglishAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.pinyin_english_fragment, container, false)
        ButterKnife.bind(this, view)
        adapter = PinyinEnglishAdapter(context!!, presenter.adapterEvent())
        recyclerView.adapter = adapter
        return view
    }

    override fun inject() {
        DaggerPinyinEnglishComponent
                .builder()
                .application(activity!!.application)
                .build()
                .inject(this)
    }

    override fun presenter(): Presenter<PinyinEnglishView> = presenter

    override fun view(): PinyinEnglishView  = this

    companion object {
        fun newInstance() : PinyinEnglishFragment = PinyinEnglishFragment()
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