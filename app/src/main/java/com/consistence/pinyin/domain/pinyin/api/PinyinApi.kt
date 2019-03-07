package com.consistence.pinyin.domain.pinyin.api

import io.reactivex.Single
import retrofit2.http.GET

internal interface PinyinApi {

    @get:GET("/pinyin/")
    val pinyin: Single<PinyinWrapper>
}