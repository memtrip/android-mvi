package com.consistence.pinyin.domain.pinyin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pinyin(
    val uid: Int,
    val sourceUrl: String,
    val phoneticScriptText: String,
    val romanLetterText: String,
    val audioSrc: String?,
    val englishTranslationText: String,
    val chineseCharacters: String,
    val characterImageSrc: String
) : Parcelable

fun List<Pinyin>.createString(): String {
    return joinToString(" ") {
        it.chineseCharacters
    }
}