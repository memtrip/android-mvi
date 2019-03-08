package com.consistence.pinyin.app.study

import com.consistence.pinyin.domain.pinyin.Pinyin
import com.memtrip.mxandroid.MxRenderAction
import com.memtrip.mxandroid.MxViewLayout
import com.memtrip.mxandroid.MxViewRenderer
import javax.inject.Inject

sealed class CreateStudyRenderAction : MxRenderAction {
    object Initial : CreateStudyRenderAction()
    data class EnterEnglishTranslation(
        val englishTranslation: String = ""
    ) : CreateStudyRenderAction()
    object  DoneEnteringChinesePhrase : CreateStudyRenderAction()
    data class AddPinyin(val pinyin: Pinyin) : CreateStudyRenderAction()
    object RemovePinyin : CreateStudyRenderAction()
    object ConfirmPhrase : CreateStudyRenderAction()
    object GoBack : CreateStudyRenderAction()
    object LoseChangesAndExit : CreateStudyRenderAction()
    object Success : CreateStudyRenderAction()
    data class ValidationError(val message: String) : CreateStudyRenderAction()
}

interface CreateStudyLayout : MxViewLayout {
    fun enterEnglishTranslation(englishTranslation: String = "")
    fun updateChinesePhrase(pinyin: List<Pinyin>)
    fun confirmPhrase(englishTranslation: String, pinyin: List<Pinyin>)
    fun exit()
    fun loseChanges()
    fun validationError(message: String)
}

class CreateStudyRenderer @Inject internal constructor() : MxViewRenderer<CreateStudyLayout, CreateStudyViewState> {
    override fun layout(layout: CreateStudyLayout, state: CreateStudyViewState) = when (state.view) {
        CreateStudyViewState.View.Idle -> {
        }
        is CreateStudyViewState.View.EnglishTranslationForm -> {
            layout.enterEnglishTranslation(state.englishTranslation)
        }
        is CreateStudyViewState.View.ChinesePhraseForm -> {
            layout.updateChinesePhrase(state.pinyin)
        }
        is CreateStudyViewState.View.ConfirmPhrase -> {
            layout.confirmPhrase(state.englishTranslation, state.pinyin)
        }
        CreateStudyViewState.View.Exit -> {
            layout.exit()
        }
        CreateStudyViewState.View.LoseChangesConfirmation -> {
            layout.loseChanges()
        }
        CreateStudyViewState.View.Success -> {
            layout.exit()
        }
        is CreateStudyViewState.View.ValidationError -> {
            layout.validationError(state.view.message)
        }
    }
}