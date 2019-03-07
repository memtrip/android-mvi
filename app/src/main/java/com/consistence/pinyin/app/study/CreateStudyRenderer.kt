package com.consistence.pinyin.app.study

import com.consistence.pinyin.domain.study.Study
import com.memtrip.mxandroid.MxRenderAction
import com.memtrip.mxandroid.MxViewLayout
import com.memtrip.mxandroid.MxViewRenderer
import javax.inject.Inject

sealed class CreateStudyRenderAction : MxRenderAction {
    data class Populate(val study: List<Study>) : CreateStudyRenderAction()
    object NoResults : CreateStudyRenderAction()
    object Error : CreateStudyRenderAction()
}

interface CreateStudyLayout : MxViewLayout {
}

class CreateStudyRenderer @Inject internal constructor() : MxViewRenderer<CreateStudyLayout, CreateStudyViewState> {
    override fun layout(layout: CreateStudyLayout, state: CreateStudyViewState) = when (state.view) {
        CreateStudyViewState.View.Idle -> {
        }
        is CreateStudyViewState.View.Populate -> {
        }
        CreateStudyViewState.View.NoResults -> {
        }
        CreateStudyViewState.View.Error -> {
        }
    }
}