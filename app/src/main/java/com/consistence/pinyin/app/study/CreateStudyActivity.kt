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
import androidx.appcompat.widget.SearchView
import com.consistence.pinyin.app.pinyin.PinyinFragmentAdapter
import com.consistence.pinyin.app.pinyin.list.PinyinListFragment
import com.consistence.pinyin.app.pinyin.list.PinyinListIntent
import com.consistence.pinyin.kit.invisible
import com.jakewharton.rxbinding2.view.RxView

class CreateStudyActivity(
    override var currentSearchQuery: String = "",
    override val consumeSelection: Boolean = true
) : MxViewActivity<CreateStudyIntent, CreateStudyRenderAction, CreateStudyViewState, CreateStudyLayout>(),
    CreateStudyLayout, PinyinListFragment.PinyinListDelegate {

    @Inject lateinit var viewModelFactory: ViewModelFactory<CreateStudyViewModel>
    @Inject lateinit var render: CreateStudyRenderer

    private lateinit var fragmentAdapter: PinyinFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.study_create_activity)

        study_create_toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        study_create_toolbar.setNavigationOnClickListener {
            model().publish(CreateStudyIntent.GoBack)
        }
        study_create_toolbar.title = getString(R.string.study_create_title)

        fragmentAdapter = PinyinFragmentAdapter(
            R.id.study_create_chinese_phrase_fragment_container,
            study_create_chinese_phrase_tab_layout,
            supportFragmentManager,
            this)

        study_create_chinese_phrase_search_view.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                study_create_chinese_phrase_search_view_label.invisible()
            }
        }

        study_create_chinese_phrase_search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p: String): Boolean { return false }
            override fun onQueryTextChange(terms: String): Boolean {
                currentSearchQuery = terms
                sendSearchEvent(terms)
                return true
            }
        })

        study_create_chinese_phrase_search_view.setOnCloseListener {
            sendSearchEvent()
            study_create_chinese_phrase_search_view_label.visible()
            false
        }

        study_create_chinese_phrase_search_view_label.setOnClickListener {
            study_create_chinese_phrase_search_view.isIconified = false
        }
    }

    override fun intents(): Observable<CreateStudyIntent> = Observable.mergeArray(
        Observable.just(CreateStudyIntent.Init),
        RxView.clicks(study_create_english_translation_cta).map {
            CreateStudyIntent.EnterEnglishTranslation(study_create_english_translation_input.text.toString())
        },
        RxView.clicks(study_create_chinese_phrase_cta).map {
            CreateStudyIntent.EnterChinesePhrase(listOf()) // TODO pinyin here
        },
        RxView.clicks(study_create_chinese_phrase_composition_remove).map {
            CreateStudyIntent.RemovePinyin
        },
        RxView.clicks(study_create_confirm_cta).map {
            CreateStudyIntent.Confirm
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

    private fun sendSearchEvent(terms: String = "") {
        fragmentAdapter.sendIntent(PinyinListIntent.Search(terms))
    }

    // region PinyinListFragment.PinyinListDelegate
    override fun pinyinSelection(pinyin: Pinyin) {
        model().publish(CreateStudyIntent.AddPinyin(pinyin))
    }
    // endregion

    // region CreateStudyLayout
    override fun enterEnglishTranslation(englishTranslation: String) {
        hideAllGroups()
        study_create_english_translation_group.visible()
        study_create_english_translation_input.setText(englishTranslation)
    }

    override fun updateChinesePhrase(pinyin: List<Pinyin>) {
        hideAllGroups()
        study_create_chinese_phrase_group.visible().run {
            study_create_chinese_phrase_search_view_label.visible()
        }

        study_create_chinese_phrase_composition_text.text = pinyin.joinToString(" ") {
            it.chineseCharacters
        }
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
        study_create_chinese_phrase_group.gone().run {
            study_create_chinese_phrase_search_view_label.gone()
        }
        study_create_confirm_group.gone()
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, CreateStudyActivity::class.java)
    }
}