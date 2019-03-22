package com.consistence.pinyin.app.pinyin.list.english

import android.content.Context
import android.view.View
import android.view.ViewGroup

import com.consistence.pinyin.R
import com.consistence.pinyin.domain.pinyin.Pinyin
import com.consistence.pinyin.kit.Interaction
import com.consistence.pinyin.kit.SimpleAdapter
import com.consistence.pinyin.kit.SimpleAdapterViewHolder
import com.consistence.pinyin.kit.visible
import com.consistence.pinyin.kit.gone
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.pinyin_english_list_item.view.*

class PinyinEnglishAdapter(
    context: Context,
    private val fullListStyle: Boolean,
    interaction: PublishSubject<Interaction<Pinyin>>
) : SimpleAdapter<Pinyin>(context, interaction) {

    override fun createViewHolder(parent: ViewGroup): SimpleAdapterViewHolder<Pinyin> {
        val viewHolder = PinyinEnglishViewHolder(inflater.inflate(
                R.layout.pinyin_english_list_item, parent, false), fullListStyle)

        RxView.clicks(viewHolder.itemView.pinyin_list_audio_button).map {
            Interaction(viewHolder.itemView.pinyin_list_audio_button.id, data[viewHolder.adapterPosition])
        }.subscribe(interaction)

        return viewHolder
    }
}

class PinyinEnglishViewHolder(
    itemView: View,
    private val fullListStyle: Boolean
) : SimpleAdapterViewHolder<Pinyin>(itemView) {

    override fun populate(position: Int, value: Pinyin) {
        itemView.pinyin_english_list_item_value.text = value.englishTranslationText
        itemView.pinyin_english_list_item_phonetic_translation_value.text = value.phoneticScriptText
        itemView.pinyin_english_list_item_chinese_character_value.text = value.chineseCharacters

        if (fullListStyle) {
            itemView.pinyin_english_list_item_phonetic_translation.visible()
            itemView.pinyin_english_list_item_phonetic_translation_value.visible()
            itemView.pinyin_english_list_item_chinese_character.visible()
            itemView.pinyin_english_list_item_chinese_character_value.visible()
            value.audioSrc?.let { itemView.pinyin_list_audio_button.visible() }
                ?: itemView.pinyin_list_audio_button.gone()
        } else {
            itemView.pinyin_english_list_item_phonetic_translation.gone()
            itemView.pinyin_english_list_item_phonetic_translation_value.gone()
            itemView.pinyin_english_list_item_chinese_character.visible()
            itemView.pinyin_english_list_item_chinese_character_value.visible()
            itemView.pinyin_list_audio_button.gone()
        }
    }
}