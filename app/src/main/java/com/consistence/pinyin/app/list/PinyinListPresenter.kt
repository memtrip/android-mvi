package com.consistence.pinyin.app.list

import android.app.Application
import com.consistence.pinyin.*
import com.consistence.pinyin.api.PinyinEntity
import com.consistence.pinyin.app.detail.PinyinDetailState

import com.consistence.pinyin.audio.PlayPinyAudioInPresenter
import com.consistence.pinyin.audio.PlayPinyinAudio
import io.reactivex.Observable
import io.reactivex.Single

abstract class PinyinListModel(application: Application)
    : Model<PinyinListIntent, PinyinListState>(application) {

    val pinyinAudio: PlayPinyinAudio = PlayPinyAudioInPresenter()

    abstract fun search(terms: String = defaultSearch): Single<List<PinyinEntity>>

    abstract val defaultSearch: String

    private fun searchQuery(terms: String = defaultSearch): Observable<PinyinListState> {
        return search(terms)
                .map<PinyinListState> { PinyinListState.Populate(it) }
                .toObservable()
    }

    override fun processor(intent: PinyinListIntent): Observable<PinyinListState> = when(intent) {
        PinyinListIntent.Init -> searchQuery()
        is PinyinListIntent.Search -> searchQuery(intent.terms)
        is PinyinListIntent.SelectItem -> o(PinyinListState.NavigateToDetails(intent.pinyin))
        is PinyinListIntent.PlayAudio -> o(PinyinListState.PlayAudio(intent.audioSrc))
    }

//    override fun first() {
//        super.first()
//        search()
//    }
//
//    override fun start() {
//        super.start()
//
//        pinyinAudio.attach(view.context())
//
//        //searchWith((view.context() as PinyinView).currentSearchQuery)
//    }
//
//    override fun stop() {
//        super.stop()
//
//        pinyinAudio.detach(view.context())
//    }
//
//    private fun searchWith(terms: String) {
//        if (terms.isEmpty()) {
//            search()
//        } else {
//            search(terms)
//        }
//    }
//
//    override fun event(): Consumer<Event> = Consumer {
//        when (it) {
//            is SearchEvent ->
//                searchWith(it.terms.toString())
//        }
//    }

//    internal fun adapterEvent(): Consumer<AdapterEvent<PinyinEntity>> {
//        return Consumer {
//            when (it) {
//                is Interaction -> {
//                    if (it.id == R.id.pinyin_list_audio_button) {
//                        it.value.audioSrc?.let {
//                            pinyinAudio.playPinyinAudio(it, view.context())
//                        }
//                    } else {
//                        view.navigateToPinyinDetails(it.value)
//                    }
//                }
//            }
//        }
//    }
}