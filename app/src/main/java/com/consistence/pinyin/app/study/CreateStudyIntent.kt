package com.consistence.pinyin.app.study

import com.consistence.pinyin.domain.pinyin.Pinyin
import com.consistence.pinyin.domain.study.Study
import com.memtrip.mxandroid.MxViewIntent

sealed class CreateStudyIntent : MxViewIntent {
    object Init : CreateStudyIntent()
    data class InitWithData(val study: Study) : CreateStudyIntent()
    object Idle : CreateStudyIntent()
    data class EnterEnglishTranslation(val englishTranslation: String) : CreateStudyIntent()
    object DeleteStudy : CreateStudyIntent()
    data class ConfirmDeleteStudy(val study: Study) : CreateStudyIntent()
    data class EnterChinesePhrase(val chinesePhrase: List<Pinyin>) : CreateStudyIntent()
    data class AddPinyin(val pinyin: Pinyin) : CreateStudyIntent()
    object RemovePinyin : CreateStudyIntent()
    data class Confirm(val study: Study, val updateMode: Boolean): CreateStudyIntent()
    object GoBack : CreateStudyIntent()
    object LoseChangesAndExit : CreateStudyIntent()
}