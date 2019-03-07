package com.consistence.pinyin.domain.pinyin.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PinyinJson(
    val sourceUrl: String,
    val phoneticScriptText: String,
    val romanLetterText: String,
    val audioSrc: String?,
    val englishTranslationText: String,
    val chineseCharacters: String,
    val characterImageSrc: String
)