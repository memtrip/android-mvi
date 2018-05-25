package com.memtrip.pinyin.app.favourites

import com.memtrip.pinyin.*
import com.memtrip.pinyin.api.CharacterSearch
import com.memtrip.pinyin.api.PinyinEntity
import io.reactivex.functions.Consumer
import javax.inject.Inject

class PinyinCharacterPresenter @Inject internal constructor(
        val characterSearch: CharacterSearch) : Presenter<PinyinCharacterView>() {

    override fun first() {
        super.first()
        search()
    }

    fun search(terms: String = "拼音") {
        i(characterSearch.search(terms, Consumer {
            view.populate(it)
        }, Consumer {
            view.error()
        }))
    }

    override fun event(): Consumer<Event> = Consumer {
        when (it) {
            is SearchEvent -> search(it.terms)
        }
    }

    internal fun adapterEvent(): Consumer<AdapterEvent<PinyinEntity>> {
        return Consumer {
            when (it) {
                is AdapterClick -> {
                    if (it.id == R.id.pinyin_character_list_audio_button) {
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