package com.memtrip.pinyin.app.list

import com.memtrip.pinyin.*
import com.memtrip.pinyin.api.GetPinyin
import com.memtrip.pinyin.api.PinyinEntity
import io.reactivex.functions.Consumer
import javax.inject.Inject

class PinyinListPresenter @Inject internal constructor(val getPinyin: GetPinyin) : Presenter<PinyinListView>() {

    override fun first() {
        super.first()

        i(getPinyin.get(0, 50, Consumer {
            view.populate(it)
        }, Consumer {
            view.error()
        }))
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