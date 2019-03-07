package com.consistence.pinyin.domain.pinyin.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PinyinWrapper(val pinyin: List<PinyinJson>)