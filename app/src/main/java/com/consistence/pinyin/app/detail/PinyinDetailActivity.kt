package com.consistence.pinyin.app.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.consistence.pinyin.legacy.Presenter
import com.consistence.pinyin.legacy.PresenterActivity
import com.consistence.pinyin.R
import com.consistence.pinyin.api.PinyinEntity
import com.consistence.pinyin.kit.visible
import kotlinx.android.synthetic.main.pinyin_detail_activity.*
import javax.inject.Inject

class PinyinDetailActivity : PresenterActivity<PinyinDetailView>(), PinyinDetailView {

    @Inject lateinit var presenter: PinyinDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pinyin_detail_activity)

        setSupportActionBar(pinyin_detail_activity_toolbar)

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_home_up)

        onClickEvent(pinyin_detail_activity_audio_button)
                .subscribe(presenter.event())
    }

    override fun inject() {
        DaggerPinyinDetailComponent
                .builder()
                .pinyinEntity(PinyinParcel.out(intent))
                .build()
                .inject(this)
    }

    override fun presenter(): Presenter<PinyinDetailView> = presenter

    override fun view(): PinyinDetailView = this

    override fun populate(pinyinParcel: PinyinParcel) {
        supportActionBar!!.setTitle(pinyinParcel.phoneticScriptText)
        pinyin_detail_activity_phonetic_script_value.text = pinyinParcel.phoneticScriptText
        pinyin_detail_activity_english_translation_value.text = pinyinParcel.englishTranslationText
        pinyin_detail_activity_chinese_character_value.text = pinyinParcel.chineseCharacters
    }

    override fun showAudioControl() {
        pinyin_detail_activity_audio_button.visible()
    }

    companion object {
        fun newIntent(context: Context, pinyinEntity: PinyinEntity): Intent {
            val intent = Intent(context, PinyinDetailActivity::class.java)
            PinyinParcel.into(pinyinEntity, intent)
            return intent
        }
    }
}