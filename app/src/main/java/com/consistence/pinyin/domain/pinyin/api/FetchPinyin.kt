package com.consistence.pinyin.domain.pinyin.api

import com.consistence.pinyin.domain.SchedulerProvider
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject

class FetchPinyin @Inject internal constructor(
    retrofit: Retrofit,
    private val schedulerProvider: SchedulerProvider
) {

    private val api = retrofit.create(PinyinApi::class.java)

    fun values(): Single<PinyinWrapper> {
        return api.pinyin
            .subscribeOn(schedulerProvider.thread())
            .observeOn(schedulerProvider.main())
    }
}