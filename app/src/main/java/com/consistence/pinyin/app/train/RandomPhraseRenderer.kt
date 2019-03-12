package com.consistence.pinyin.app.train

import com.consistence.pinyin.domain.study.Study
import com.memtrip.mxandroid.MxRenderAction
import com.memtrip.mxandroid.MxViewLayout
import com.memtrip.mxandroid.MxViewRenderer
import javax.inject.Inject

sealed class RandomPhraseRenderAction : MxRenderAction {
    data class PickRandomPhrases(val study: List<Study>) : RandomPhraseRenderAction()
    data class Result(val correct: Boolean) : RandomPhraseRenderAction()
}

interface RandomPhraseLayout : MxViewLayout {
    fun next(study: Study)
    fun finished(results: List<Pair<Study, Boolean>>)
}

class RandomPhraseRenderer @Inject internal constructor() : MxViewRenderer<RandomPhraseLayout, RandomPhraseViewState> {
    override fun layout(layout: RandomPhraseLayout, state: RandomPhraseViewState) = when (state.view) {
        RandomPhraseViewState.View.Idle -> {
        }
        is RandomPhraseViewState.View.Next -> {
            layout.next(state.view.study)
        }
        is RandomPhraseViewState.View.Finished -> {
            layout.finished(state.results)
        }
    }
}