package com.consistence.pinyin.app.study

import com.consistence.pinyin.domain.study.Study
import com.memtrip.mxandroid.MxRenderAction
import com.memtrip.mxandroid.MxViewLayout
import com.memtrip.mxandroid.MxViewRenderer
import javax.inject.Inject

sealed class StudyRenderAction : MxRenderAction {
    data class Populate(val study: List<Study>) : StudyRenderAction()
    object NoResults : StudyRenderAction()
    object Error : StudyRenderAction()
}

interface StudyLayout : MxViewLayout {
    fun noResults()
    fun populate(study: List<Study>)
}

class StudyRenderer @Inject internal constructor() : MxViewRenderer<StudyLayout, StudyViewState> {
    override fun layout(layout: StudyLayout, state: StudyViewState) = when (state.view) {
        StudyViewState.View.Idle -> {
        }
        is StudyViewState.View.Populate -> {
            layout.populate(state.view.study)
        }
        StudyViewState.View.NoResults -> {
            layout.noResults()
        }
        StudyViewState.View.Error -> {
        }
    }
}