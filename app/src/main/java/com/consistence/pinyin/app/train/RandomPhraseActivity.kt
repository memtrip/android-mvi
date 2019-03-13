package com.consistence.pinyin.app.train

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.consistence.pinyin.R
import com.consistence.pinyin.ViewModelFactory
import com.consistence.pinyin.domain.study.Study
import com.consistence.pinyin.kit.gone
import com.consistence.pinyin.kit.visible
import com.jakewharton.rxbinding2.view.RxView
import com.memtrip.mxandroid.MxViewActivity
import dagger.android.AndroidInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.train_random_activity.*
import javax.inject.Inject

class RandomPhraseActivity : MxViewActivity<RandomPhraseIntent, RandomPhraseRenderAction, RandomPhraseViewState, RandomPhraseLayout>(), RandomPhraseLayout {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<RandomPhraseViewModel>

    @Inject
    lateinit var render: RandomPhraseRenderer

    private lateinit var adapter: RandomPhraseResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.train_random_activity)

        train_random_toolbar.title = getString(R.string.train_random_title)
        train_random_toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        train_random_toolbar.setNavigationOnClickListener {
            finish()
        }

        adapter = RandomPhraseResultsAdapter(this)
        train_random_results_recycler_view.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (forceQuit(data)) {
            finish()
        } else {
            model().publish(RandomPhraseIntent.Result(result(data)))
        }
    }

    override fun intents(): Observable<RandomPhraseIntent> = Observable.merge(
        Observable.just(RandomPhraseIntent.Init),
        RxView.clicks(train_random_start_cta).map {
            RandomPhraseIntent.Start(studyLimit())
        }
    )

    // region RandomPhraseLayout
    override fun next(study: Study) {
        train_random_start_cta.gone()
        model().publish(RandomPhraseIntent.Idle)
        startActivityForResult(TrainPhraseActivity.newIntent(this, study), 0)
    }

    override fun finished(results: List<Pair<Study, Boolean>>) {
        train_random_start_group.gone()
        train_random_results_recycler_view.visible()

        train_random_toolbar.title = getString(R.string.train_random_results_title, results.count { it.second }, results.size)
        adapter.populate(results)
    }
    // endregion

    private fun result(intent: Intent?): Boolean {
        return intent?.getBooleanExtra(TrainPhraseActivity.RESULT_STATUS, false) ?: false
    }

    private fun forceQuit(intent: Intent?): Boolean {
        return intent?.getBooleanExtra(TrainPhraseActivity.RESULT_FORCE_QUIT, false) ?: false
    }

    private fun studyLimit(): Int = when (train_random_start_limit_tab_layout.selectedTabPosition) {
        0 -> 5
        1 -> 10
        2 -> 20
        3 -> 50
        else -> 20
    }

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
