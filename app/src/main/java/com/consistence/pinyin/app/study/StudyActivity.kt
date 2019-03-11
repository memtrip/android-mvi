package com.consistence.pinyin.app.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.consistence.pinyin.R
import com.consistence.pinyin.ViewModelFactory
import com.consistence.pinyin.domain.study.Study
import com.consistence.pinyin.kit.Interaction
import com.consistence.pinyin.kit.gone
import com.consistence.pinyin.kit.visible
import com.memtrip.mxandroid.MxViewActivity
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.study_activity.*
import javax.inject.Inject

class StudyActivity : MxViewActivity<StudyIntent, StudyRenderAction, StudyViewState, StudyLayout>(), StudyLayout {

    @Inject lateinit var viewModelFactory: ViewModelFactory<StudyViewModel>
    @Inject lateinit var render: StudyRenderer

    private lateinit var adapter: StudyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.study_activity)
        setSupportActionBar(study_toolbar)
        supportActionBar!!.apply {
            title = getString(R.string.study_title)
            setDisplayHomeAsUpEnabled(true)
        }
        study_create_button.setOnClickListener {
            startCreateStudyActivity()
        }

        val adapterInteraction: PublishSubject<Interaction<Study>> = PublishSubject.create()
        adapter = StudyAdapter(this, adapterInteraction)
        study_recyclerview.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.study_menu, menu)

        menu.findItem(R.id.study_menu_add).setOnMenuItemClickListener {
            startCreateStudyActivity()
            true
        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        model().publish(StudyIntent.Refresh)
    }

    private fun startCreateStudyActivity() {
        startActivityForResult(CreateStudyActivity.newIntent(this), 1)
    }

    override fun intents(): Observable<StudyIntent> = Observable.merge(
        Observable.just(StudyIntent.Init),
        adapter.interaction.map {
            when (it.id) {
                R.id.study_card_item_train -> StudyIntent.StudyPhrase(it.data)
                else -> StudyIntent.SelectStudy(it.data)
            }
        }
    )

    override fun navigateToStudy(study: Study) {
        model().publish(StudyIntent.Idle)
        startActivityForResult(CreateStudyActivity.newIntent(this, study), 1)
    }

    override fun inject() {
        AndroidInjection.inject(this)
    }

    override fun layout(): StudyLayout = this

    override fun render(): StudyRenderer = render

    override fun model(): StudyViewModel = getViewModel(viewModelFactory)

    override fun noResults() {
        study_no_results_group.visible()
        study_recyclerview.gone()
    }

    override fun populate(study: List<Study>) {
        study_no_results_group.gone()
        study_recyclerview.visible()
        adapter.clear()
        adapter.populate(study)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, StudyActivity::class.java)
    }
}