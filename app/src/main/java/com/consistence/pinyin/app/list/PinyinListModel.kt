package com.consistence.pinyin.app.list

import android.app.Application
import com.consistence.pinyin.Model
import com.consistence.pinyin.api.PinyinEntity
import io.reactivex.Observable
import io.reactivex.Single

abstract class PinyinListModel(application: Application)
    : Model<PinyinListIntent, PinyinListState>(application) {

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
}