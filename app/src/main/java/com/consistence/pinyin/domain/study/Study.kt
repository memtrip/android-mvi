package com.consistence.pinyin.domain.study

import android.os.Parcelable
import com.consistence.pinyin.domain.pinyin.Pinyin
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Study(
    val englishTranslation: String,
    val pinyin: List<Pinyin>,
    val uid: Int = -1
) : Parcelable