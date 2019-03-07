package com.consistence.pinyin.app.study

import com.consistence.pinyin.domain.study.Study
import com.memtrip.mxandroid.MxViewState

data class CreateStudyViewState(val view: View) : MxViewState {
    sealed class View : MxViewState {
        object Idle : View()
        data class Populate(val study: List<Study>) : View()
        object NoResults : View()
        object Error : View()
    }
}