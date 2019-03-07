package com.consistence.pinyin.app.study

import com.memtrip.mxandroid.MxViewIntent

sealed class CreateStudyIntent : MxViewIntent {
    object Init : CreateStudyIntent()
    object Retry : CreateStudyIntent()
}