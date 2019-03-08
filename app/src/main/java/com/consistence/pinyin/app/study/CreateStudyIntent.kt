package com.consistence.pinyin.app.study

import com.consistence.pinyin.domain.pinyin.Pinyin
import com.memtrip.mxandroid.MxViewIntent

sealed class CreateStudyIntent : MxViewIntent {
    object Init : CreateStudyIntent()
    data class EnterEnglishTranslation(val englishTranslation: String) : CreateStudyIntent()
    data class EnterChinesePhrase(val chinesePhrase: List<Pinyin>) : CreateStudyIntent()
    data class AddPinyin(val pinyin: Pinyin) : CreateStudyIntent()
    object RemovePinyin : CreateStudyIntent()
    object Confirm: CreateStudyIntent()
    object GoBack : CreateStudyIntent()
    object LoseChangesAndExit : CreateStudyIntent()
}