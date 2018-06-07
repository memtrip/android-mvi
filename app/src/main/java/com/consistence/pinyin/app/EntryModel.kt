package com.consistence.pinyin.app

import com.consistence.pinyin.Model
import com.consistence.pinyin.api.CountPinyin
import com.consistence.pinyin.api.FetchAndSavePinyin
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class EntryModel @Inject internal constructor(
        private val fetchAndSavePinyin: FetchAndSavePinyin,
        private val countPinyin: CountPinyin): Model<EntryIntent, EntryState>() {

    private fun getPinyin() : Observable<EntryState> = countPinyin
            .count()
            .doOnSubscribe {
                publish(EntryIntent.OnProgress)
            }
            .flatMap<EntryState>({ count ->
                if (count > 0) {
                    Single.just(EntryState.OnPinyinLoaded)
                } else {
                    fetchAndSavePinyin.save().map { EntryState.OnPinyinLoaded }
                }
            })
            .onErrorResumeNext {
                Single.just(EntryState.OnError)
            }
            .toObservable()

    override fun processor(intent: EntryIntent): Observable<EntryState> = when(intent) {
        EntryIntent.OnProgress -> Observable.just(EntryState.OnProgress)
        EntryIntent.Init -> getPinyin()
        EntryIntent.Retry -> getPinyin()
    }
}