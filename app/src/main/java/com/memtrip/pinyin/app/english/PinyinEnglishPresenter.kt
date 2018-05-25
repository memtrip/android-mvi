package com.memtrip.pinyin.app.english

import com.memtrip.pinyin.*

import com.memtrip.pinyin.api.EnglishSearch
import com.memtrip.pinyin.api.PinyinEntity
import io.reactivex.functions.Consumer
import javax.inject.Inject

class PinyinEnglishPresenter @Inject internal constructor(
        val englishSearch: EnglishSearch) : Presenter<PinyinEnglishView>() {

    override fun first() {
        super.first()
        search()
    }

    fun search(terms: String = "pinyin") {
        i(englishSearch.search(terms, Consumer {
            view.populate(it)
        }, Consumer {
            view.error()
        }))
    }

    override fun event(): Consumer<Event> = Consumer {
        when (it) {
            is SearchEvent -> {
                if (it.terms.isEmpty()) {
                    search()
                } else {
                    search(it.terms)
                }
            }
        }
    }

    internal fun adapterEvent(): Consumer<AdapterEvent<PinyinEntity>> {
        return Consumer {
            when (it) {
                is AdapterClick -> {
                    if (it.id == R.id.pinyin_english_list_audio_button) {
                        it.value.audioSrc?.let {

                        }
                    } else {
                        view.navigateToPinyinDetails(it.value)
                    }
                }
            }
        }
    }
}