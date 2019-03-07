package com.consistence.pinyin.app.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.consistence.pinyin.R
import com.consistence.pinyin.ViewModelFactory

import com.memtrip.mxandroid.MxViewActivity
import dagger.android.AndroidInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.study_create_activity.*
import javax.inject.Inject

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

    override fun intents(): Observable<CreateStudyIntent> = Observable.just(CreateStudyIntent.Init)

    override fun inject() {
        AndroidInjection.inject(this)
    }

    override fun layout(): CreateStudyLayout = this

    override fun render(): CreateStudyRenderer = render

    override fun model(): CreateStudyViewModel = getViewModel(viewModelFactory)

    companion object {
        fun newIntent(context: Context) = Intent(context, CreateStudyActivity::class.java)
    }
}