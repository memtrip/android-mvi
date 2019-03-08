package com.consistence.pinyin.app.study

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.consistence.pinyin.R
import com.consistence.pinyin.domain.pinyin.formatPinyinString
import com.consistence.pinyin.domain.study.Study
import com.consistence.pinyin.kit.*
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.study_list_item.view.*

class StudyAdapter(
    context: Context,
    interaction: PublishSubject<Interaction<Study>>
) : SimpleAdapter<Study>(context, interaction) {

    override fun createViewHolder(parent: ViewGroup): SimpleAdapterViewHolder<Study> {
        val viewHolder = StudyViewHolder(inflater.inflate(
            R.layout.study_list_item, parent, false))

        RxView.clicks(viewHolder.deleteButton).map {
            Interaction(viewHolder.deleteButton.id, data[viewHolder.adapterPosition])
        }.subscribe(interaction)

        return viewHolder
    }
}

class StudyViewHolder(itemView: View) : SimpleAdapterViewHolder<Study>(itemView) {

    val deleteButton = itemView.study_list_item_delete

    override fun populate(position: Int, value: Study) {
        itemView.study_list_item_chinese_phrase_value.text = value.pinyin.formatPinyinString()
        itemView.study_list_item_english_translation_value.text = value.englishTranslation
    }
}