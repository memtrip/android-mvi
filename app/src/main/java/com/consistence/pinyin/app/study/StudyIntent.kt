package com.consistence.pinyin.app.study

import com.memtrip.mxandroid.MxViewIntent

sealed class StudyIntent : MxViewIntent {
    object Init : StudyIntent()
    object Refresh : StudyIntent()
    object Retry : StudyIntent()
}