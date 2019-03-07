package com.consistence.pinyin.app.train

import com.consistence.pinyin.domain.study.Study
import com.memtrip.mxandroid.MxViewState

data class RandomPhraseViewState(
    val view: View,
    val study: List<Study> = listOf(),
    val currentPosition: Int = 0,
    val results: List<Pair<Study, Boolean>> = listOf()
) : MxViewState {
    sealed class View : MxViewState {
        object Idle : View()
        data class Next(val study: Study) : View()
        object Finished : View()
    }
}