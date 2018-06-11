package com.consistence.pinyin.app

import android.app.Application
import com.consistence.pinyin.Model
import com.consistence.pinyin.R
import io.reactivex.Observable
import javax.inject.Inject

class PinyinModel @Inject internal constructor(application: Application) : Model<PinyinIntent, PinyinState>(application) {

    override fun reducer(intent: PinyinIntent): Observable<PinyinState> = when(intent) {
        is PinyinIntent.TabSelected -> {
            o(
                    when (intent.page) {
                        Page.PHONETIC -> PinyinState.SearchHint(
                                context().getString(R.string.pinyin_activity_search_phonetic_hint))
                        Page.ENGLISH -> PinyinState.SearchHint(
                                context().getString(R.string.pinyin_activity_search_english_hint))
                        Page.CHARACTER -> PinyinState.SearchHint(
                                context().getString(R.string.pinyin_activity_search_character_hint))
                    }
            )
        }
    }
}