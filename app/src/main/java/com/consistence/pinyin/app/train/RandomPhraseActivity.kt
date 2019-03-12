package com.consistence.pinyin.app.train

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.consistence.pinyin.R
import com.consistence.pinyin.ViewModelFactory
import com.consistence.pinyin.domain.pinyin.Pinyin
import com.consistence.pinyin.domain.pinyin.formatChineseCharacterString
import com.consistence.pinyin.domain.study.Study
import com.consistence.pinyin.kit.closeKeyboard
import com.consistence.pinyin.kit.gone
import com.consistence.pinyin.kit.visible
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.memtrip.mxandroid.MxViewActivity
import dagger.android.AndroidInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.train_phrase_activity.*
import javax.inject.Inject

class RandomPhraseActivity : MxViewActivity<RandomPhraseIntent, RandomPhraseRenderAction, RandomPhraseViewState, RandomPhraseLayout>(), RandomPhraseLayout {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<RandomPhraseViewModel>
    @Inject
    lateinit var render: RandomPhraseRenderer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.train_phrase_activity)

        train_phrase_toolbar.title = getString(R.string.train_phrase_title)
        train_phrase_toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        train_phrase_toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun intents(): Observable<RandomPhraseIntent> =
        Observable.just(RandomPhraseIntent.Init)

    // region RandomPhraseLayout
    override fun next(study: Study) {
        startActivityForResult(TrainPhraseActivity.newIntent(this, study), 0)
    }

    override fun finished(results: List<Pair<Study, Boolean>>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    // endregion

    override fun inject() {
        AndroidInjection.inject(this)
    }

    override fun layout(): RandomPhraseLayout = this

    override fun render(): RandomPhraseRenderer = render

    override fun model(): RandomPhraseViewModel = getViewModel(viewModelFactory)

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, RandomPhraseActivity::class.java)
        }
    }
}
