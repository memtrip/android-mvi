package com.consistence.pinyin.app.list.phonetic

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.consistence.pinyin.R
import com.consistence.pinyin.api.PinyinEntity
import com.consistence.pinyin.app.detail.PinyinDetailActivity
import com.consistence.pinyin.app.list.PinyinListFragment
import com.consistence.pinyin.app.list.PinyinListIntent
import com.consistence.pinyin.kit.Interaction
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class PinyinPhoneticFragment : PinyinListFragment() {

    @Inject lateinit var model: PinyinPhoneticModel

    @BindView(R.id.pinyin_phonetic_fragment_recyclerview)
    lateinit var recyclerView: RecyclerView

    private lateinit var adapter: PinyinPhoneticAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.pinyin_phonetic_fragment, container, false)
        ButterKnife.bind(this, view)

        val adapterInteraction: PublishSubject<Interaction<PinyinEntity>> = PublishSubject.create()
        adapter = PinyinPhoneticAdapter(context!!, adapterInteraction)
        recyclerView.adapter = adapter

        adapterInteraction.map({
            when (it.id) {
                R.id.pinyin_list_audio_button ->
                    PinyinListIntent.PlayAudio(it.data.audioSrc!!)
                else ->
                    PinyinListIntent.SelectItem(it.data)
            }
        }).subscribe(model.incomingIntents)

        return view
    }

    override fun inject() {
        DaggerPinyinPhoneticComponent
                .builder()
                .application(activity!!.application)
                .build()
                .inject(this)
    }

    override fun model() = model

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

    companion object { fun newInstance() = PinyinPhoneticFragment() }
}