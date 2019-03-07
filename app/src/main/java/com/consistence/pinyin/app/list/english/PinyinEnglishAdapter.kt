package com.consistence.pinyin.app.list.english

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.consistence.pinyin.R
import com.consistence.pinyin.domain.pinyin.Pinyin
import com.consistence.pinyin.kit.*
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.pinyin_english_list_item.view.*

class PinyinEnglishAdapter(
    context: Context,
    interaction: PublishSubject<Interaction<Pinyin>>
) : SimpleAdapter<Pinyin>(context, interaction) {

    override fun createViewHolder(parent: ViewGroup): SimpleAdapterViewHolder<Pinyin> {
        val viewHolder = PinyinEnglishViewHolder(inflater.inflate(
                R.layout.pinyin_english_list_item, parent, false))

        RxView.clicks(viewHolder.audioButton).map {
            Interaction(viewHolder.audioButton.id, data[viewHolder.adapterPosition])
        }.subscribe(interaction)

        return viewHolder
    }
}

class PinyinEnglishViewHolder(itemView: View) : SimpleAdapterViewHolder<Pinyin>(itemView) {

    val audioButton: ImageButton = itemView.pinyin_list_audio_button

    override fun populate(position: Int, value: Pinyin) {
        itemView.pinyin_english_list_item_value.text = value.englishTranslationText
        itemView.pinyin_english_list_item_phonetic_translation_value.text = value.phoneticScriptText
        itemView.pinyin_english_list_item_chinese_character_value.text = value.chineseCharacters
        value.audioSrc?.let { itemView.pinyin_list_audio_button.visible() } ?: itemView.pinyin_list_audio_button.gone()
    }
}