package com.consistence.pinyin.app.detail

import android.content.Intent
import android.os.Parcelable
import com.consistence.pinyin.PresenterView

import com.consistence.pinyin.api.PinyinEntity

import dagger.BindsInstance
import dagger.Component
import kotlinx.android.parcel.Parcelize
import javax.inject.Singleton

@Parcelize
class PinyinParcel(val sourceUrl: String,
                   val phoneticScriptText: String,
                   val romanLetterText: String,
                   val audioSrc: String?,
                   val englishTranslationText: String,
                   val chineseCharacters: String,
                   val characterImageSrc: String) : Parcelable {

    companion object {

        val PINYIN_PARCEL = "PINYIN_PARCEL"

        fun into(entity: PinyinEntity, intent: Intent) {
            intent.putExtra(PINYIN_PARCEL, PinyinParcel(
                    entity.sourceUrl,
                    entity.phoneticScriptText,
                    entity.romanLetterText,
                    entity.audioSrc,
                    entity.englishTranslationText,
                    entity.chineseCharacters,
                    entity.characterImageSrc))
        }

        fun out(intent: Intent) : PinyinParcel = intent.getParcelableExtra(PINYIN_PARCEL)
    }
}

interface PinyinDetailView : PresenterView {

    fun populate(pinyinParcel: PinyinParcel)

    fun showAudioControl()
}

@Singleton
@Component
interface PinyinDetailComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun pinyinEntity(pinyinParcel: PinyinParcel): Builder

        fun build(): PinyinDetailComponent
    }

    fun inject(pinyinDetailsActivity: PinyinDetailActivity)
}