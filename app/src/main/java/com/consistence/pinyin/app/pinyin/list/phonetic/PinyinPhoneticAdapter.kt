package com.consistence.pinyin.app.pinyin.list.phonetic

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
import kotlinx.android.synthetic.main.pinyin_phonetic_list_item.view.*

class PinyinPhoneticAdapter(
    context: Context,
    private val fullListStyle: Boolean,
    interaction: PublishSubject<Interaction<Pinyin>>
) : SimpleAdapter<Pinyin>(context, interaction) {

    override fun createViewHolder(parent: ViewGroup): SimpleAdapterViewHolder<Pinyin> {
        val viewHolder = PinyinPhoneticViewHolder(inflater.inflate(
                R.layout.pinyin_phonetic_list_item, parent, false), fullListStyle)

        RxView.clicks(viewHolder.itemView.pinyin_list_audio_button).map {
            Interaction(viewHolder.itemView.pinyin_list_audio_button.id, data[viewHolder.adapterPosition])
        }.subscribe(interaction)

        return viewHolder
    }
}

class PinyinPhoneticViewHolder(
    itemView: View,
    private val fullListStyle: Boolean
) : SimpleAdapterViewHolder<Pinyin>(itemView) {

    override fun populate(position: Int, value: Pinyin) {
        itemView.pinyin_phonetic_list_item_value.text = value.phoneticScriptText
        itemView.pinyin_phonetic_list_item_english_translation_value.text = value.englishTranslationText
        itemView.pinyin_phonetic_list_item_chinese_character_value.text = value.chineseCharacters

        if (fullListStyle) {
            itemView.pinyin_phonetic_list_item_english_translation.visible()
            itemView.pinyin_phonetic_list_item_english_translation_value.visible()
            itemView.pinyin_phonetic_list_item_chinese_character.visible()
            itemView.pinyin_phonetic_list_item_chinese_character_value.visible()
            value.audioSrc?.let { itemView.pinyin_list_audio_button.visible() }
                ?: itemView.pinyin_list_audio_button.gone()
        } else {
            itemView.pinyin_phonetic_list_item_english_translation.visible()
            itemView.pinyin_phonetic_list_item_english_translation_value.visible()
            itemView.pinyin_phonetic_list_item_chinese_character.gone()
            itemView.pinyin_phonetic_list_item_chinese_character_value.gone()
            itemView.pinyin_list_audio_button.gone()
        }
    }
}