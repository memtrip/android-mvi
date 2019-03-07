package com.consistence.pinyin.app.list.character

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.consistence.pinyin.R
import com.consistence.pinyin.domain.pinyin.Pinyin
import com.consistence.pinyin.kit.*
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.pinyin_character_list_item.view.*

class PinyinCharacterAdapter(
    context: Context,
    interaction: PublishSubject<Interaction<Pinyin>>
) : SimpleAdapter<Pinyin>(context, interaction) {

    override fun createViewHolder(parent: ViewGroup): SimpleAdapterViewHolder<Pinyin> {
        val viewHolder = PinyinCharacterViewHolder(inflater.inflate(
                R.layout.pinyin_character_list_item, parent, false))

        RxView.clicks(viewHolder.audioButton).map {
            Interaction(viewHolder.audioButton.id, data[viewHolder.adapterPosition])
        }.subscribe(interaction)

        return viewHolder
    }
}

class PinyinCharacterViewHolder(itemView: View) : SimpleAdapterViewHolder<Pinyin>(itemView) {

    val audioButton: ImageButton = itemView.pinyin_list_audio_button

    override fun populate(position: Int, value: Pinyin) {
        itemView.pinyin_character_list_item_value.text = value.chineseCharacters
        itemView.pinyin_character_list_item_english_translation_value.text = value.englishTranslationText
        itemView.pinyin_character_list_item_phonetic_translation_value.text = value.phoneticScriptText
        value.audioSrc?.let { audioButton.visible() } ?: audioButton.gone()
    }
}