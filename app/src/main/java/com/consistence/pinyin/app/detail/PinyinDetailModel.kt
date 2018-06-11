package com.consistence.pinyin.app.detail

import android.app.Application
import com.consistence.pinyin.Model

import io.reactivex.Observable
import javax.inject.Inject

class PinyinDetailModel @Inject internal constructor(
        private val pinyinParcel: PinyinParcel, application: Application)
    : Model<PinyinDetailIntent, PinyinDetailState>(application) {

    override fun reducer(intent: PinyinDetailIntent): Observable<PinyinDetailState> = when(intent) {
        is PinyinDetailIntent.Init -> populate(pinyinParcel)
        PinyinDetailIntent.PlayAudio -> playAudio(pinyinParcel.audioSrc!!)
    }

    private fun populate(pinyinParcel: PinyinParcel): Observable<PinyinDetailState> =
            o(PinyinDetailState.Populate(
                    pinyinParcel.phoneticScriptText,
                    pinyinParcel.englishTranslationText,
                    pinyinParcel.chineseCharacters,
                    pinyinParcel.audioSrc
            ))

    private fun playAudio(audioSrc: String): Observable<PinyinDetailState> =
            o(PinyinDetailState.PlayAudio(audioSrc))
}