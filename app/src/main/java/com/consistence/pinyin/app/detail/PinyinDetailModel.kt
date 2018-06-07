package com.consistence.pinyin.app.detail

import android.app.Application
import com.consistence.pinyin.Model

import io.reactivex.Observable
import javax.inject.Inject

class PinyinDetailModel @Inject internal constructor(val pinyinParcel: PinyinParcel, application: Application)
    : Model<PinyinDetailIntent, PinyinDetailState>(application) {

    override fun processor(intent: PinyinDetailIntent): Observable<PinyinDetailState> = when(intent) {
        is PinyinDetailIntent.Init -> populate(pinyinParcel)
        PinyinDetailIntent.PlayAudio -> action(pinyinParcel.audioSrc!!)
    }

    private fun populate(pinyinParcel: PinyinParcel): Observable<PinyinDetailState> =
            Observable.just(PinyinDetailState.Populate(pinyinParcel))

    private fun action(audioSrc: String): Observable<PinyinDetailState> =
            Observable.just(PinyinDetailState.PlayAudio(audioSrc))
}