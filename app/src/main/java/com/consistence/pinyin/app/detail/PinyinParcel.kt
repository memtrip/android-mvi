package com.consistence.pinyin.app.detail

import android.content.Intent
import android.os.Parcelable
import com.consistence.pinyin.database.pinyin.PinyinEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
class PinyinParcel(
    val phoneticScriptText: String,
    val audioSrc: String?,
    val englishTranslationText: String,
    val chineseCharacters: String
) : Parcelable {

    companion object {

        val PINYIN_PARCEL = "PINYIN_PARCEL"

        fun into(entity: PinyinEntity, intent: Intent) {
            intent.putExtra(PINYIN_PARCEL, PinyinParcel(
                    entity.phoneticScriptText,
                    entity.audioSrc,
                    entity.englishTranslationText,
                    entity.chineseCharacters))
        }

        fun out(intent: Intent): PinyinParcel = intent.getParcelableExtra(PINYIN_PARCEL)
    }
}