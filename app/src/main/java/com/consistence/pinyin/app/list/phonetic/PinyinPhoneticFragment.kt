package com.consistence.pinyin.app.list.phonetic

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.consistence.pinyin.legacy.Presenter
import com.consistence.pinyin.legacy.PresenterFragment
import com.consistence.pinyin.R
import com.consistence.pinyin.api.PinyinEntity
import com.consistence.pinyin.app.detail.PinyinDetailActivity
import javax.inject.Inject

class PinyinPhoneticFragment : PresenterFragment<PinyinPhoneticView>(), PinyinPhoneticView {

    @Inject lateinit var presenter: PinyinPhoneticPresenter

    @BindView(R.id.pinyin_phonetic_fragment_recyclerview)
    lateinit var recyclerView: RecyclerView

    private lateinit var adapter: PinyinPhoneticAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.pinyin_phonetic_fragment, container, false)
        ButterKnife.bind(this, view)
        adapter = PinyinPhoneticAdapter(context!!, presenter.adapterEvent())
        recyclerView.adapter = adapter
        return view
    }

    override fun inject() {
        DaggerPinyinPhoneticComponent
                .builder()
                .application(activity!!.application)
                .build()
                .inject(this)
    }

    override fun presenter(): Presenter<PinyinPhoneticView> = presenter

    override fun view(): PinyinPhoneticView = this

    override fun populate(pinyin: List<PinyinEntity>) {
        adapter.clear()
        adapter.populate(pinyin)
    }

    override fun error() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateToPinyinDetails(pinyinEntity: PinyinEntity) {
        startActivity(PinyinDetailActivity.newIntent(context!!, pinyinEntity))
    }

    companion object {
        fun newInstance() : PinyinPhoneticFragment = PinyinPhoneticFragment()
    }
}