package com.consistence.pinyin.app.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.consistence.pinyin.R
import com.consistence.pinyin.ViewModelFactory
import com.consistence.pinyin.domain.study.Study
import com.consistence.pinyin.kit.visible
import com.memtrip.mxandroid.MxViewActivity
import dagger.android.AndroidInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.study_activity.*
import javax.inject.Inject

class StudyActivity : MxViewActivity<StudyIntent, StudyRenderAction, StudyViewState, StudyLayout>(), StudyLayout {

    @Inject lateinit var viewModelFactory: ViewModelFactory<StudyViewModel>
    @Inject lateinit var render: StudyRenderer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.study_activity)
    }

    override fun intents(): Observable<StudyIntent> = Observable.just(StudyIntent.Init)

    override fun inject() {
        AndroidInjection.inject(this)
    }

    override fun layout(): StudyLayout = this

    override fun render(): StudyRenderer = render

    override fun model(): StudyViewModel = getViewModel(viewModelFactory)

    override fun noResults() {
        study_no_results_group.visible()
    }

    override fun populate(study: List<Study>) {
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, StudyActivity::class.java)
    }
}