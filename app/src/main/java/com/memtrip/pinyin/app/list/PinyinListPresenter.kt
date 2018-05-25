package com.memtrip.pinyin.app.list

import com.memtrip.pinyin.*
import com.memtrip.pinyin.api.GetPinyin
import com.memtrip.pinyin.api.PinyinEntity
import com.memtrip.pinyin.api.SearchPinyin
import io.reactivex.functions.Consumer
import javax.inject.Inject

class PinyinListPresenter @Inject internal constructor(val searchPinyin: SearchPinyin) : Presenter<PinyinListView>() {

    override fun first() {
        super.first()
        search("pinyin")
    }

    fun search(terms: String = "") {
        i(searchPinyin.search(terms, Consumer {
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
                is AdapterEnd -> {

                }
                is AdapterClick -> {

                }
            }
        }
    }
}