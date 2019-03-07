package com.consistence.pinyin.app.pinyin.detail

import android.app.Application
import com.memtrip.mxandroid.MxViewModel
import io.reactivex.Observable
import javax.inject.Inject

class PinyinDetailViewModel @Inject internal constructor(
    private val pinyinParcel: PinyinParcel,
    application: Application
) : MxViewModel<PinyinDetailIntent, PinyinDetailRenderAction, PinyinDetailViewState>(
    PinyinDetailViewState(
        pinyinParcel.phoneticScriptText,
        pinyinParcel.englishTranslationText,
        pinyinParcel.chineseCharacters,
        pinyinParcel.audioSrc
    ),
    application
) {

    override fun dispatcher(intent: PinyinDetailIntent): Observable<PinyinDetailRenderAction> = when (intent) {
        is PinyinDetailIntent.Idle -> Observable.just(PinyinDetailRenderAction.Idle)
        PinyinDetailIntent.PlayAudio -> Observable.just(PinyinDetailRenderAction.PlayAudio(pinyinParcel.audioSrc!!))
    }

    override fun reducer(previousState: PinyinDetailViewState, renderAction: PinyinDetailRenderAction): PinyinDetailViewState = when (renderAction) {
        PinyinDetailRenderAction.Idle -> previousState.copy(action = PinyinDetailViewState.Action.None)
        is PinyinDetailRenderAction.PlayAudio -> previousState.copy(action = PinyinDetailViewState.Action.PlayAudio(renderAction.audioSrc))
    }
}