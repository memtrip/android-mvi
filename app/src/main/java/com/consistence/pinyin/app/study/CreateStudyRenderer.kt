package com.consistence.pinyin.app.study

import com.consistence.pinyin.domain.pinyin.Pinyin
import com.consistence.pinyin.domain.study.Study
import com.memtrip.mxandroid.MxRenderAction
import com.memtrip.mxandroid.MxViewLayout
import com.memtrip.mxandroid.MxViewRenderer
import javax.inject.Inject

sealed class CreateStudyRenderAction : MxRenderAction {
    data class EnterEnglishTranslation(
        val englishTranslation: String = ""
    ) : CreateStudyRenderAction()
    data class EnterChinesePhrase(
        val pinyin: List<Pinyin> = listOf()
    ) : CreateStudyRenderAction()
    object ConfirmPhrase: CreateStudyRenderAction()
    object GoBack : CreateStudyRenderAction()
    object LoseChangesAndExit : CreateStudyRenderAction()
}

interface CreateStudyLayout : MxViewLayout {
    fun enterEnglishTranslation(englishTranslation: String = "")
    fun enterChinesePhrase(phrase: List<Pinyin>)
    fun confirmPhrase(englishTranslation: String, pinyin: List<Pinyin>)
    fun exit()
    fun loseChanges()
}

class CreateStudyRenderer @Inject internal constructor() : MxViewRenderer<CreateStudyLayout, CreateStudyViewState> {
    override fun layout(layout: CreateStudyLayout, state: CreateStudyViewState) = when (state.view) {
        CreateStudyViewState.View.Idle -> {
        }
        is CreateStudyViewState.View.EnterEnglishTranslation -> {
            layout.enterEnglishTranslation(state.view.englishTranslation)
        }
        is CreateStudyViewState.View.EnterChinesePhrase -> {
            layout.enterChinesePhrase(state.view.pinyin)
        }
        is CreateStudyViewState.View.ConfirmPhrase -> {
            layout.confirmPhrase(state.englishTranslation, state.pinyin)
        }
        CreateStudyViewState.View.Exit -> {
            layout.exit()
        }
        CreateStudyViewState.View.LoseChanges -> {
            layout.loseChanges()
        }
    }
}