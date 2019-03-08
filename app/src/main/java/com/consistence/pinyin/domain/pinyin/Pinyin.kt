package com.consistence.pinyin.domain.pinyin

data class Pinyin(
    val uid: Int,
    val sourceUrl: String,
    val phoneticScriptText: String,
    val romanLetterText: String,
    val audioSrc: String?,
    val englishTranslationText: String,
    val chineseCharacters: String,
    val characterImageSrc: String
)

fun List<Pinyin>.createString(): String {
    return joinToString(" ") {
        it.chineseCharacters
    }
}