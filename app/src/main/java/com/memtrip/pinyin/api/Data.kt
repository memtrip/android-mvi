package com.memtrip.pinyin.api

data class PinyinWrapper(
        val pinyin: List<Pinyin>)

data class Pinyin(
        val sourceUrl: String,
        val phoneticScriptText: String,
        val romanLetterText: String,
        val audioSrc: String,
        val englishTranslationText: String,
        val characterImageSrc: String)