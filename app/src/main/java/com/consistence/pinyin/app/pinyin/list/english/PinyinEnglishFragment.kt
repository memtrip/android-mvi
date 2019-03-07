package com.consistence.pinyin.app.pinyin.list.english

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.consistence.pinyin.R
import com.consistence.pinyin.ViewModelFactory
import com.consistence.pinyin.app.pinyin.list.PinyinListFragment
import com.consistence.pinyin.app.pinyin.list.PinyinListIntent
import com.consistence.pinyin.app.pinyin.list.PinyinListLayout
import com.consistence.pinyin.domain.pinyin.Pinyin
import com.consistence.pinyin.kit.Interaction
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.pinyin_english_fragment.view.*
import javax.inject.Inject

class PinyinEnglishFragment : PinyinListFragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory<PinyinEnglishViewModel>

    private lateinit var adapter: PinyinEnglishAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.pinyin_english_fragment, container, false)

        val adapterInteraction: PublishSubject<Interaction<Pinyin>> = PublishSubject.create()
        adapter = PinyinEnglishAdapter(context!!, delegate.fullListStyle, adapterInteraction)
        view.pinyin_english_fragment_recyclerview.adapter = adapter

        return view
    }

    override fun intents(): Observable<PinyinListIntent> = Observable.merge(
        super.intents(),
        adapter.interaction.map {
            when (it.id) {
                R.id.pinyin_list_audio_button -> PinyinListIntent.PlayAudio(it.data.audioSrc!!)
                else -> PinyinListIntent.SelectItem(it.data)
            }
        }
    )

    override fun inject() {
        AndroidSupportInjection.inject(this)
    }

    override fun layout(): PinyinListLayout = this

    override fun model(): PinyinEnglishViewModel = getViewModel(viewModelFactory)

    override fun populate(pinyin: List<Pinyin>) {
        adapter.clear()
        adapter.populate(pinyin)
    }

    override fun showError() {
    }

    companion object { fun newInstance() = PinyinEnglishFragment() }
}