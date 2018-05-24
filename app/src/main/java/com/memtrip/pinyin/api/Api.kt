package com.memtrip.pinyin.api

import dagger.Module
import dagger.Provides
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

internal interface PinyinApi {

    @get:GET("/pinyin/")
    val pinyin: Single<PinyinWrapper>
}

@Module
class PinyinModule {

    @Singleton
    @Provides
    internal fun pinyinApi(retrofit: Retrofit): PinyinApi = retrofit.create(PinyinApi::class.java)
}

class GetPinyin @Inject internal constructor(
        retrofit: Retrofit,
        private val schedulerProvider: SchedulerProvider) {

    private val api = retrofit.create(PinyinApi::class.java)

    fun values(success: Consumer<PinyinWrapper>, error: Consumer<Throwable>): Disposable {
        return api.pinyin
                .subscribeOn(schedulerProvider.thread())
                .observeOn(schedulerProvider.main())
                .subscribe(success, error)
    }
}