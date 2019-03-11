package com.consistence.pinyin.app.study

import com.consistence.pinyin.domain.study.Study
import com.memtrip.mxandroid.MxRenderAction
import com.memtrip.mxandroid.MxViewLayout
import com.memtrip.mxandroid.MxViewRenderer
import javax.inject.Inject

sealed class StudyRenderAction : MxRenderAction {
    object Idle : StudyRenderAction()
    data class Populate(val study: List<Study>) : StudyRenderAction()
    object NoResults : StudyRenderAction()
    object Error : StudyRenderAction()
    data class NavigateToStudy(val study: Study) : StudyRenderAction()
    data class TrainPhrase(val study: Study) : StudyRenderAction()
}

interface StudyLayout : MxViewLayout {
    fun noResults()
    fun populate(study: List<Study>)
    fun navigateToStudy(study: Study)
    fun navigateToTrainPhrase(study: Study)
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
        is StudyViewState.View.NavigateToStudy -> {
            layout.navigateToStudy(state.view.study)
        }
        is StudyViewState.View.NavigateToTrainPhrase -> {
            layout.navigateToTrainPhrase(state.view.study)
        }
    }
}