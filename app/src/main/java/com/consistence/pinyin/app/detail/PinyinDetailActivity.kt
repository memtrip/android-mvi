package com.consistence.pinyin.app.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.consistence.pinyin.R
import com.consistence.pinyin.ViewActivity
import com.consistence.pinyin.ViewModelFactory
import com.consistence.pinyin.api.PinyinEntity

import com.consistence.pinyin.audio.PlayPinyAudioInPresenter
import com.consistence.pinyin.kit.visible
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.pinyin_detail_activity.*
import javax.inject.Inject

class PinyinDetailActivity : ViewActivity<PinyinDetailIntent, PinyinDetailState, PinyinDetailModel,
        PinyinDetailRender>(), PinyinDetailLayout {

    @Inject lateinit var viewModelFactory: ViewModelFactory<PinyinDetailModel>

    val pinyinAudio = PlayPinyAudioInPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pinyin_detail_activity)

        setSupportActionBar(pinyin_detail_activity_toolbar)

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_home_up)

        RxView.clicks(pinyin_detail_activity_audio_button)
                .map({ PinyinDetailIntent.PlayAudio })
                .subscribe(model().intents)
    }

    override fun onStart() {
        super.onStart()
        pinyinAudio.attach(this)
    }

    override fun onStop() {
        super.onStop()
        pinyinAudio.detach(this)
    }

    override fun initIntent() = PinyinDetailIntent.Init

    override fun inject() {
        DaggerPinyinDetailComponent
                .builder()
                .pinyinParcel(PinyinParcel.out(intent))
                .application(application)
                .build()
                .inject(this)
    }

    override fun model(): PinyinDetailModel = getViewModel(viewModelFactory)

    override fun render() = lazy { PinyinDetailRender(this) }.value

    override fun populate(pinyinParcel: PinyinParcel) {
        supportActionBar!!.setTitle(pinyinParcel.phoneticScriptText)
        pinyin_detail_activity_phonetic_script_value.text = pinyinParcel.phoneticScriptText
        pinyin_detail_activity_english_translation_value.text = pinyinParcel.englishTranslationText
        pinyin_detail_activity_chinese_character_value.text = pinyinParcel.chineseCharacters
    }

    override fun showAudioControl() {
        pinyin_detail_activity_audio_button.visible()
    }

    override fun playAudio(audioSrc: String) {
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