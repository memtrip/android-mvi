package com.memtrip.pinyin.app.detail

import com.memtrip.pinyin.Presenter
import javax.inject.Inject

class PinyinDetailPresenter @Inject internal constructor(
        private val pinyinParcel: PinyinParcel): Presenter<PinyinDetailView>() {

    override fun first() {
        super.first()

        view.populate(pinyinParcel)
    }
}