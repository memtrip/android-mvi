package com.consistence.pinyin.app.study

import android.app.Application
import com.memtrip.mxandroid.MxViewModel
import io.reactivex.Observable
import javax.inject.Inject

class CreateStudyViewModel @Inject internal constructor(
    application: Application
) : MxViewModel<CreateStudyIntent, CreateStudyRenderAction, CreateStudyViewState>(
    CreateStudyViewState(CreateStudyViewState.View.Idle),
    application
) {

    override fun dispatcher(intent: CreateStudyIntent): Observable<CreateStudyRenderAction> = when (intent) {
        CreateStudyIntent.Init ->
            Observable.just(CreateStudyRenderAction.Initial)
        is CreateStudyIntent.EnterEnglishTranslation ->
            Observable.just(CreateStudyRenderAction.EnterEnglishTranslation(intent.englishTranslation))
        is CreateStudyIntent.EnterChinesePhrase ->
            Observable.just(CreateStudyRenderAction.DoneEnteringChinesePhrase)
        is CreateStudyIntent.AddPinyin ->
            Observable.just(CreateStudyRenderAction.AddPinyin(intent.pinyin))
        CreateStudyIntent.RemovePinyin ->
            Observable.just(CreateStudyRenderAction.RemovePinyin)
        is CreateStudyIntent.Confirm ->
            Observable.just(CreateStudyRenderAction.Success)
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
                }
            )
        }
        is CreateStudyRenderAction.RemovePinyin -> {
            previousState.copy(
                pinyin = previousState.pinyin.apply {
                    if (previousState.pinyin.size > 0) {
                        removeAt(previousState.pinyin.size - 1)
                    }
                }
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
    }

    override fun filterIntents(intents: Observable<CreateStudyIntent>): Observable<CreateStudyIntent> = Observable.merge(
        intents.ofType(CreateStudyIntent.Init.javaClass).take(1),
        intents.filter {
            !CreateStudyIntent.Init.javaClass.isInstance(it)
        }
    )
}