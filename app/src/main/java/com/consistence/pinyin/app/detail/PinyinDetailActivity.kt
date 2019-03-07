package com.consistence.pinyin.app.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.consistence.pinyin.R
import com.consistence.pinyin.ViewModelFactory
import com.consistence.pinyin.domain.pinyin.db.PinyinEntity
import com.consistence.pinyin.audio.PlayPinyAudioInPresenter
import com.consistence.pinyin.kit.visible
import com.jakewharton.rxbinding2.view.RxView
import com.memtrip.mxandroid.MxViewActivity
import dagger.android.AndroidInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.pinyin_detail_activity.*
import javax.inject.Inject

class PinyinDetailActivity : MxViewActivity<PinyinDetailIntent, PinyinDetailRenderAction, PinyinDetailViewState, PinyinDetailLayout>(), PinyinDetailLayout {

    @Inject lateinit var viewModelFactory: ViewModelFactory<PinyinDetailViewModel>
    @Inject lateinit var render: PinyinDetailRenderer

    private val pinyinAudio = PlayPinyAudioInPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pinyin_detail_activity)

        setSupportActionBar(pinyin_detail_activity_toolbar)

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_home_up)
    }

    override fun onStart() {
        super.onStart()
        pinyinAudio.attach(this)
    }

    override fun onStop() {
        super.onStop()
        pinyinAudio.detach(this)
    }

    override fun intents(): Observable<PinyinDetailIntent> = Observable.merge(
            Observable.just(PinyinDetailIntent.Idle),
            RxView.clicks(pinyin_detail_activity_audio_button)
                    .map({ PinyinDetailIntent.PlayAudio })
    )

    override fun inject() {
        AndroidInjection.inject(this)
    }

    override fun layout(): PinyinDetailLayout = this

    override fun model(): PinyinDetailViewModel = getViewModel(viewModelFactory)

    override fun render(): PinyinDetailRenderer = render

    override fun populate(
        phoneticScriptText: String,
        englishTranslationText: String,
        chineseCharacters: String
    ) {
        supportActionBar!!.title = phoneticScriptText
        pinyin_detail_activity_phonetic_script_value.text = phoneticScriptText
        pinyin_detail_activity_english_translation_value.text = englishTranslationText
        pinyin_detail_activity_chinese_character_value.text = chineseCharacters
    }

    override fun showAudioControl() {
        pinyin_detail_activity_audio_button.visible()
    }

    override fun playAudio(audioSrc: String) {
        model().publish(PinyinDetailIntent.Idle)
        pinyinAudio.playPinyinAudio(audioSrc, this)
    }

    companion object {
        fun newIntent(context: Context, pinyinEntity: PinyinEntity): Intent {
            val intent = Intent(context, PinyinDetailActivity::class.java)
            PinyinParcel.into(pinyinEntity, intent)
            return intent
        }
    }
}