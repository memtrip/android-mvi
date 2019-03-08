package com.consistence.pinyin.app.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.consistence.pinyin.R
import com.consistence.pinyin.ViewModelFactory
import com.consistence.pinyin.domain.pinyin.Pinyin
import com.consistence.pinyin.kit.gone
import com.consistence.pinyin.kit.visible

import com.memtrip.mxandroid.MxViewActivity
import dagger.android.AndroidInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.study_create_activity.*
import javax.inject.Inject
import androidx.appcompat.app.AlertDialog
import com.jakewharton.rxbinding2.view.RxView

class CreateStudyActivity : MxViewActivity<CreateStudyIntent, CreateStudyRenderAction, CreateStudyViewState, CreateStudyLayout>(), CreateStudyLayout {

    @Inject lateinit var viewModelFactory: ViewModelFactory<CreateStudyViewModel>
    @Inject lateinit var render: CreateStudyRenderer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.study_create_activity)
        setSupportActionBar(study_create_toolbar)
        supportActionBar!!.apply {
            title = getString(R.string.study_create_title)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun intents(): Observable<CreateStudyIntent> = Observable.merge(
        Observable.just(CreateStudyIntent.Init),
        RxView.clicks(study_create_english_translation_cta).map {
            CreateStudyIntent.EnterEnglishTranslation(study_create_english_translation_input.text.toString())
        },
        RxView.clicks(study_create_chinese_phrase_cta).map {
            CreateStudyIntent.EnterChinesePhrase(listOf())
        },
        RxView.clicks(study_create_confirm_cta).map {
            CreateStudyIntent.ConfirmPhrase
        }
    )

    override fun onBackPressed() {
        model().publish(CreateStudyIntent.GoBack)
    }

    override fun inject() {
        AndroidInjection.inject(this)
    }

    override fun layout(): CreateStudyLayout = this

    override fun render(): CreateStudyRenderer = render

    override fun model(): CreateStudyViewModel = getViewModel(viewModelFactory)

    // region CreateStudyLayout
    override fun enterEnglishTranslation(englishTranslation: String) {
        hideAllGroups()
        study_create_english_translation_group.visible()
        study_create_english_translation_input.setText(englishTranslation)
    }

    override fun enterChinesePhrase(phrase: List<Pinyin>) {
        hideAllGroups()
        study_create_chinese_phrase_group.visible()
        // TODO: populate adapter with phrases
    }

    override fun confirmPhrase(englishTranslation: String, pinyin: List<Pinyin>) {
        hideAllGroups()
        study_create_confirm_group.visible()
        study_create_confirm_chinese_translation.text = pinyin.joinToString(" ") {
            it.chineseCharacters
        }
        study_create_confirm_english_translation.text = englishTranslation
    }

    override fun exit() {
        finish()
    }

    override fun loseChanges() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.study_create_lose_changes_title))
            .setMessage(getString(R.string.study_create_lose_changes_body))
            .setPositiveButton(android.R.string.yes) { _, _ ->
                finish()
            }
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
    // endregion

    private fun hideAllGroups() {
        study_create_english_translation_group.gone()
        study_create_chinese_phrase_group.gone()
        study_create_confirm_group.gone()
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, CreateStudyActivity::class.java)
    }
}