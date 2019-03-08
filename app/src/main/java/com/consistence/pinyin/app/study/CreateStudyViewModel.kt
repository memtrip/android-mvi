package com.consistence.pinyin.app.study

import android.app.Application
import com.consistence.pinyin.R
import com.consistence.pinyin.domain.pinyin.Pinyin
import com.consistence.pinyin.domain.study.Study
import com.consistence.pinyin.domain.study.db.SaveStudy
import com.memtrip.mxandroid.MxViewModel
import io.reactivex.Observable
import javax.inject.Inject

class CreateStudyViewModel @Inject internal constructor(
    private val saveStudy: SaveStudy,
    application: Application
) : MxViewModel<CreateStudyIntent, CreateStudyRenderAction, CreateStudyViewState>(
    CreateStudyViewState(CreateStudyViewState.View.Idle),
    application
) {

    override fun dispatcher(intent: CreateStudyIntent): Observable<CreateStudyRenderAction> = when (intent) {
        CreateStudyIntent.Init ->
            Observable.just(CreateStudyRenderAction.Initial)
        is CreateStudyIntent.EnterEnglishTranslation ->
            validateEnglishTranslation(intent.englishTranslation)
        is CreateStudyIntent.EnterChinesePhrase ->
            validateChinesePhrase(intent.chinesePhrase)
        is CreateStudyIntent.AddPinyin ->
            Observable.just(CreateStudyRenderAction.AddPinyin(intent.pinyin))
        CreateStudyIntent.RemovePinyin ->
            Observable.just(CreateStudyRenderAction.RemovePinyin)
        is CreateStudyIntent.Confirm -> saveStudy(intent.englishTranslation, intent.pinyin)
        CreateStudyIntent.GoBack ->
            Observable.just(CreateStudyRenderAction.GoBack)
        CreateStudyIntent.LoseChangesAndExit ->
            Observable.just(CreateStudyRenderAction.LoseChangesAndExit)
    }

    override fun reducer(previousState: CreateStudyViewState, renderAction: CreateStudyRenderAction): CreateStudyViewState = when (renderAction) {
        CreateStudyRenderAction.Initial -> {
            previousState.copy(
                view = CreateStudyViewState.View.EnglishTranslationForm,
                step = CreateStudyViewState.Step.ENGLISH_TRANSLATION
            )
        }
        is CreateStudyRenderAction.EnterEnglishTranslation ->
            previousState.copy(
                view = CreateStudyViewState.View.ChinesePhraseForm(),
                step = CreateStudyViewState.Step.CHINESE_PHRASE,
                englishTranslation = renderAction.englishTranslation
            )
        CreateStudyRenderAction.DoneEnteringChinesePhrase ->
            previousState.copy(
                view = CreateStudyViewState.View.ConfirmPhrase,
                step = CreateStudyViewState.Step.CONFIRM
            )
        is CreateStudyRenderAction.AddPinyin -> {
            previousState.copy(
                pinyin = previousState.pinyin.apply {
                    add(renderAction.pinyin)
                },
                view = CreateStudyViewState.View.ChinesePhraseForm()
            )
        }
        is CreateStudyRenderAction.RemovePinyin -> {
            previousState.copy(
                pinyin = previousState.pinyin.apply {
                    if (previousState.pinyin.size > 0) {
                        removeAt(previousState.pinyin.size - 1)
                    }
                },
                view = CreateStudyViewState.View.ChinesePhraseForm()
            )
        }
        is CreateStudyRenderAction.ConfirmPhrase ->
            previousState.copy(
                view = CreateStudyViewState.View.Exit
            )
        CreateStudyRenderAction.GoBack -> when (previousState.step) {
            CreateStudyViewState.Step.INITIAL,
            CreateStudyViewState.Step.ENGLISH_TRANSLATION -> {
                if (previousState.englishTranslation.isEmpty() && previousState.pinyin.isEmpty()) {
                    previousState.copy(view = CreateStudyViewState.View.Exit)
                } else {
                    previousState.copy(view = CreateStudyViewState.View.LoseChangesConfirmation)
                }
            }
            CreateStudyViewState.Step.CHINESE_PHRASE -> {
                previousState.copy(
                    view = CreateStudyViewState.View.EnglishTranslationForm,
                    step = CreateStudyViewState.Step.ENGLISH_TRANSLATION
                )
            }
            CreateStudyViewState.Step.CONFIRM -> {
                previousState.copy(
                    view = CreateStudyViewState.View.ChinesePhraseForm(),
                    step = CreateStudyViewState.Step.CHINESE_PHRASE
                )
            }
        }
        CreateStudyRenderAction.LoseChangesAndExit -> previousState.copy(
            view = CreateStudyViewState.View.Exit
        )
        CreateStudyRenderAction.Success -> previousState.copy(
            view = CreateStudyViewState.View.Success
        )
        is CreateStudyRenderAction.ValidationError -> previousState.copy(
            view = CreateStudyViewState.View.ValidationError(renderAction.message)
        )
    }

    override fun filterIntents(intents: Observable<CreateStudyIntent>): Observable<CreateStudyIntent> = Observable.merge(
        intents.ofType(CreateStudyIntent.Init.javaClass).take(1),
        intents.filter {
            !CreateStudyIntent.Init.javaClass.isInstance(it)
        }
    )

    private fun validateEnglishTranslation(englishTranslation: String): Observable<CreateStudyRenderAction> {
       return Observable.just(when {
           englishTranslation.isEmpty() -> CreateStudyRenderAction.ValidationError(
               context().getString(R.string.study_create_english_translation_validation_empty))
           englishTranslation.length > 30 -> CreateStudyRenderAction.ValidationError(
               context().getString(R.string.study_create_english_translation_validation_more_than))
           else -> CreateStudyRenderAction.EnterEnglishTranslation(englishTranslation)
       })
    }

    private fun validateChinesePhrase(pinyin: List<Pinyin>): Observable<CreateStudyRenderAction> {
        return Observable.just(when {
            pinyin.isEmpty() -> CreateStudyRenderAction.ValidationError(
                context().getString(R.string.study_create_chinese_phrase_validation_empty))
            pinyin.joinToString { it.chineseCharacters }.length > 30 -> CreateStudyRenderAction.ValidationError(
                context().getString(R.string.study_create_chinese_phrase_validation_more_than))
            else -> CreateStudyRenderAction.DoneEnteringChinesePhrase
        })
    }

    private fun saveStudy(englishTranslation: String, pinyin: List<Pinyin>): Observable<CreateStudyRenderAction> {
        return saveStudy.insert(Study(englishTranslation, pinyin)).map<CreateStudyRenderAction> {
            CreateStudyRenderAction.Success
        }.toObservable().onErrorReturn {
            CreateStudyRenderAction.ValidationError(context().getString(R.string.study_create_generic_error))
        }
    }
}