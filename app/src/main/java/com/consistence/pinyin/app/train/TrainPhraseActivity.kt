package com.consistence.pinyin.app.train

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.consistence.pinyin.R
import com.consistence.pinyin.ViewModelFactory
import com.consistence.pinyin.app.pinyin.PinyinActivity
import com.consistence.pinyin.app.study.CreateStudyActivity
import com.consistence.pinyin.app.study.StudyActivity
import com.consistence.pinyin.domain.pinyin.Pinyin
import com.consistence.pinyin.domain.pinyin.formatChineseCharacterString
import com.consistence.pinyin.domain.pinyin.pinyinUidForDatabase
import com.consistence.pinyin.domain.study.Study
import com.consistence.pinyin.kit.gone
import com.consistence.pinyin.kit.visible
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.memtrip.mxandroid.MxViewActivity
import dagger.android.AndroidInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.entry_activity.*
import kotlinx.android.synthetic.main.kit_error_retry.view.*
import kotlinx.android.synthetic.main.train_phrase_activity.*
import javax.inject.Inject

class TrainPhraseActivity : MxViewActivity<TrainPhraseIntent, TrainPhraseRenderAction, TrainPhraseViewState, TrainPhraseLayout>(), TrainPhraseLayout {

    @Inject lateinit var viewModelFactory: ViewModelFactory<TrainPhraseViewModel>
    @Inject lateinit var render: TrainPhraseRenderer

    private val study: Study by lazy {
        intent.getParcelableExtra<Study>(ARG_STUDY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.train_phrase_activity)

        train_phrase_toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        train_phrase_toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun intents(): Observable<TrainPhraseIntent> = Observable.merge(
        Observable.just(TrainPhraseIntent.Init(study)),
        Observable.merge(
            RxView.clicks(train_phrase_english_cta),
            RxTextView.editorActions(train_phrase_english_question_input)
        ).map {
            TrainPhraseIntent.AnswerEnglish(train_phrase_english_question_input.text.toString())
        },
        Observable.merge(
            RxView.clicks(train_phrase_chinese_cta),
            RxTextView.editorActions(train_phrase_chinese_question_input)
        ).map {
            TrainPhraseIntent.AnswerChinese(train_phrase_chinese_question_input.text.toString())
        }
    )

    // region TrainPhraseLayout
    override fun englishQuestion(englishTranslation: String) {
        train_phrase_english.visible()
        train_phrase_english_question_label.text = englishTranslation
    }

    override fun chineseQuestion(chineseQuestion: List<Pinyin>) {
        train_phrase_chinese.visible()
        train_phrase_chinese_question_label.text = chineseQuestion.formatChineseCharacterString()
    }

    override fun correct(study: Study) {
        train_phrase_container.setBackgroundColor(ContextCompat.getColor(this, R.color.colorTextInverse))
    }

    override fun incorrect(entered: Study, answer: Study) {
        train_phrase_container.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
    }
    // endregion

    override fun inject() {
        AndroidInjection.inject(this)
    }

    override fun layout(): TrainPhraseLayout = this

    override fun render(): TrainPhraseRenderer = render

    override fun model(): TrainPhraseViewModel = getViewModel(viewModelFactory)

    companion object {

        private const val ARG_STUDY = "ARG_STUDY"

        fun newIntent(context: Context, study: Study): Intent  {
            return Intent(context, TrainPhraseActivity::class.java).apply {
                putExtra(ARG_STUDY, study)
            }
        }
    }
}