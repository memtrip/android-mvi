package com.memtrip.pinyin.app.detail

import android.content.Intent
import android.os.Parcelable
import com.memtrip.pinyin.PresenterView

import com.memtrip.pinyin.api.PinyinEntity

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
                    entity.characterImageSrc))
        }

        fun out(intent: Intent) : PinyinParcel = intent.getParcelableExtra(PINYIN_PARCEL)
    }
}

interface PinyinDetailView : PresenterView {

    fun populate(pinyinParcel: PinyinParcel)
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