package com.consistence.pinyin.app.study

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.consistence.pinyin.R
import com.consistence.pinyin.domain.pinyin.formatChineseCharacterString
import com.consistence.pinyin.domain.study.Study
import com.consistence.pinyin.kit.*
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.study_card_view.view.*
import kotlinx.android.synthetic.main.study_list_item.view.*

class StudyAdapter(
    context: Context,
    interaction: PublishSubject<Interaction<Study>>
) : SimpleAdapter<Study>(context, interaction) {

    override fun createViewHolder(parent: ViewGroup): SimpleAdapterViewHolder<Study> {
        val viewHolder = StudyViewHolder(inflater.inflate(
            R.layout.study_list_item, parent, false))

        RxView.clicks(viewHolder.itemView.study_list_card.study_card_item_edit).map {
            Interaction(viewHolder.itemView.study_list_card.study_card_item_edit.id, data[viewHolder.adapterPosition])
        }.subscribe(interaction)

        return viewHolder
    }
}

class StudyViewHolder(itemView: View) : SimpleAdapterViewHolder<Study>(itemView) {

    override fun populate(position: Int, value: Study) {
        itemView.study_list_card.populate(value, true)
    }
}