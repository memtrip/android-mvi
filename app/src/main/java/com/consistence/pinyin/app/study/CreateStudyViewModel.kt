package com.consistence.pinyin.app.study

import android.app.Application
import com.consistence.pinyin.R
import com.consistence.pinyin.domain.pinyin.Pinyin
import com.consistence.pinyin.domain.study.Study
import com.consistence.pinyin.domain.study.db.DeleteStudy
import com.consistence.pinyin.domain.study.db.SaveStudy
import com.consistence.pinyin.domain.study.db.UpdateStudy
import com.memtrip.mxandroid.MxViewModel
import io.reactivex.Observable
import javax.inject.Inject

class CreateStudyViewModel @Inject internal constructor(
    private val saveStudy: SaveStudy,
    private val updateStudy: UpdateStudy,
    private val deleteStudy: DeleteStudy,
    application: Application
) : MxViewModel<CreateStudyIntent, CreateStudyRenderAction, CreateStudyViewState>(
    CreateStudyViewState(CreateStudyViewState.View.Idle),
    application
) {

    override fun dispatcher(intent: CreateStudyIntent): Observable<CreateStudyRenderAction> = when (intent) {
        CreateStudyIntent.Init ->
            Observable.just(CreateStudyRenderAction.Init)
        is CreateStudyIntent.InitWithData ->
            Observable.just(CreateStudyRenderAction.InitWithData(intent.study))
        CreateStudyIntent.Idle -> Observable.just(CreateStudyRenderAction.Idle)
        is CreateStudyIntent.EnterEnglishTranslation ->
            validateEnglishTranslation(intent.englishTranslation)
        CreateStudyIntent.DeleteStudy ->
            Observable.just(CreateStudyRenderAction.DeleteStudy)
        is CreateStudyIntent.ConfirmDeleteStudy ->
            deleteStudy(intent.study)
        is CreateStudyIntent.EnterChinesePhrase ->
            validateChinesePhrase(intent.chinesePhrase)
        is CreateStudyIntent.AddPinyin ->
            Observable.just(CreateStudyRenderAction.AddPinyin(intent.pinyin))
        CreateStudyIntent.RemovePinyin ->
            Observable.just(CreateStudyRenderAction.RemovePinyin)
        is CreateStudyIntent.Confirm -> persistStudy(intent.study, intent.updateMode)
        CreateStudyIntent.GoBack ->
            Observable.just(CreateStudyRenderAction.GoBack)
        CreateStudyIntent.LoseChangesAndExit ->
            Observable.just(CreateStudyRenderAction.LoseChangesAndExit)
    }

    override fun reducer(previousState: CreateStudyViewState, renderAction: CreateStudyRenderAction): CreateStudyViewState = when (renderAction) {
        CreateStudyRenderAction.Init -> {
            previousState.copy(
                view = CreateStudyViewState.View.EnglishTranslationForm,
                step = CreateStudyViewState.Step.ENGLISH_TRANSLATION
            )
        }
        is CreateStudyRenderAction.InitWithData -> {
            previousState.copy(
                view = CreateStudyViewState.View.EnglishTranslationForm,
                step = CreateStudyViewState.Step.ENGLISH_TRANSLATION,
                englishTranslation = renderAction.study.englishTranslation,
                pinyin = previousState.pinyin.apply {
                    addAll(renderAction.study.pinyin)
                },
                originalStudy = renderAction.study
            )
        }
        CreateStudyRenderAction.Idle -> previousState.copy(
            view = CreateStudyViewState.View.Idle
        )
        is CreateStudyRenderAction.EnterEnglishTranslation ->
            previousState.copy(
                view = CreateStudyViewState.View.ChinesePhraseForm(),
                step = CreateStudyViewState.Step.CHINESE_PHRASE,
                englishTranslation = renderAction.englishTranslation
            )
        CreateStudyRenderAction.DeleteStudy -> previousState.copy(
            view = CreateStudyViewState.View.DeleteStudyConfirmation
        )
        is CreateStudyRenderAction.StudyDeleted -> previousState.copy(
            view = CreateStudyViewState.View.Exit
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
                if (previousState.englishTranslation.isEmpty() && previousState.pinyin.isEmpty() ||
                    studyHasNotChanged(previousState.originalStudy, previousState.englishTranslation, previousState.pinyin)
                ) {
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

    private fun studyHasNotChanged(
        originalStudy: Study?,
        currentEnglishPhrase: String,
        currentPinyin: List<Pinyin>
    ): Boolean {
        return originalStudy != null &&
            originalStudy.englishTranslation == currentEnglishPhrase &&
            originalStudy.pinyin == currentPinyin
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
            englishTranslation.length > 80 -> CreateStudyRenderAction.ValidationError(
                context().getString(R.string.study_create_english_translation_validation_more_than))
            else -> CreateStudyRenderAction.EnterEnglishTranslation(englishTranslation)
        })
    }

    private fun validateChinesePhrase(pinyin: List<Pinyin>): Observable<CreateStudyRenderAction> {
        return Observable.just(when {
            pinyin.isEmpty() -> CreateStudyRenderAction.ValidationError(
                context().getString(R.string.study_create_chinese_phrase_validation_empty))
            pinyin.joinToString { it.chineseCharacters }.length > 80 -> CreateStudyRenderAction.ValidationError(
                context().getString(R.string.study_create_chinese_phrase_validation_more_than))
            else -> CreateStudyRenderAction.DoneEnteringChinesePhrase
        })
    }

    private fun persistStudy(study: Study, updateMode: Boolean): Observable<CreateStudyRenderAction> {
        return if (updateMode) {
            updateStudy.update(study).map<CreateStudyRenderAction> {
                CreateStudyRenderAction.Success
            }.toObservable().onErrorReturn {
                CreateStudyRenderAction.ValidationError(context().getString(R.string.study_create_generic_error))
            }
        } else {
            saveStudy.insert(study).map<CreateStudyRenderAction> {
                CreateStudyRenderAction.Success
            }.toObservable().onErrorReturn {
                CreateStudyRenderAction.ValidationError(context().getString(R.string.study_create_generic_error))
            }
        }
    }

    private fun deleteStudy(study: Study): Observable<CreateStudyRenderAction> {
        return deleteStudy.remove(study).map<CreateStudyRenderAction> {
            CreateStudyRenderAction.StudyDeleted
        }.toObservable().onErrorReturn {
            CreateStudyRenderAction.ValidationError(context().getString(R.string.study_delete_generic_error))
        }
    }
}