package com.consistence.pinyin.app.list.phonetic

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

import com.consistence.pinyin.R
import com.consistence.pinyin.api.PinyinEntity
import com.consistence.pinyin.kit.Interaction
import com.consistence.pinyin.kit.SimpleAdapter
import com.consistence.pinyin.kit.SimpleAdapterViewHolder
import com.consistence.pinyin.kit.gone
import com.consistence.pinyin.kit.visible
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.pinyin_phonetic_list_item.view.*

class PinyinPhoneticAdapter(
    context: Context,
    interaction: PublishSubject<Interaction<PinyinEntity>>
) : SimpleAdapter<PinyinEntity>(context, interaction) {

    override fun createViewHolder(parent: ViewGroup): SimpleAdapterViewHolder<PinyinEntity> {
        val viewHolder = PinyinPhoneticViewHolder(inflater.inflate(
                R.layout.pinyin_phonetic_list_item, parent, false))

        RxView.clicks(viewHolder.audioButton)
                .map({
                    Interaction(viewHolder.audioButton.id, data[viewHolder.adapterPosition])
                })
                .subscribe(interaction)

        return viewHolder
    }
}

class PinyinPhoneticViewHolder(itemView: View) : SimpleAdapterViewHolder<PinyinEntity>(itemView) {

    val audioButton: ImageButton = itemView.pinyin_list_audio_button

    override fun populate(position: Int, value: PinyinEntity) {
        itemView.pinyin_phonetic_list_item_value.text = value.phoneticScriptText
        itemView.pinyin_phonetic_list_item_english_translation_value.text = value.englishTranslationText
        itemView.pinyin_phonetic_list_item_chinese_character_value.text = value.chineseCharacters
        value.audioSrc?.let { audioButton.visible() } ?: audioButton.gone()
    }
}