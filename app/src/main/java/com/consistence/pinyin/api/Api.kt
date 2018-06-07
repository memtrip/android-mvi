package com.consistence.pinyin.api

import dagger.Module
import dagger.Provides
import io.reactivex.Single

import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

data class PinyinWrapper(
        val pinyin: List<PinyinJson>)

data class PinyinJson(
        val sourceUrl: String,
        val phoneticScriptText: String,
        val romanLetterText: String,
        val audioSrc: String?,
        val englishTranslationText: String,
        val chineseCharacters: String,
        val characterImageSrc: String)

internal interface PinyinApi {

    @get:GET("/pinyin/")
    val pinyin: Single<PinyinWrapper>
}

@Module
class ApiModule {

    @Singleton
    @Provides
    internal fun pinyinApi(retrofit: Retrofit): PinyinApi = retrofit.create(PinyinApi::class.java)
}

class FetchPinyin @Inject internal constructor(
        retrofit: Retrofit,
        private val schedulerProvider: SchedulerProvider) {

    private val api = retrofit.create(PinyinApi::class.java)

    fun values(): Single<PinyinWrapper> = api.pinyin
            .subscribeOn(schedulerProvider.thread())
            .observeOn(schedulerProvider.main())
}