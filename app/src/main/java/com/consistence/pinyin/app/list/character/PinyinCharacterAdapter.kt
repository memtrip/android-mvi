package com.consistence.pinyin.app.list.character

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.consistence.pinyin.R
import com.consistence.pinyin.api.PinyinEntity
import com.consistence.pinyin.kit.Interaction
import com.consistence.pinyin.kit.SimpleAdapter
import com.consistence.pinyin.kit.SimpleAdapterViewHolder
import com.consistence.pinyin.kit.gone
import com.consistence.pinyin.kit.visible
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject

class PinyinCharacterAdapter(
    context: Context,
    interaction: PublishSubject<Interaction<PinyinEntity>>
) : SimpleAdapter<PinyinEntity>(context, interaction) {

    override fun createViewHolder(parent: ViewGroup): SimpleAdapterViewHolder<PinyinEntity> {
        val viewHolder = PinyinCharacterViewHolder(inflater.inflate(
                R.layout.pinyin_character_list_item, parent, false))

        RxView.clicks(viewHolder.audioButton)
                .map({
                    Interaction(viewHolder.audioButton.id, data[viewHolder.adapterPosition])
                })
                .subscribe(interaction)

        return viewHolder
    }
}

class PinyinCharacterViewHolder(itemView: View) : SimpleAdapterViewHolder<PinyinEntity>(itemView) {

    @BindView(R.id.pinyin_character_list_item_value)
    lateinit var characterTextView: TextView

    @BindView(R.id.pinyin_list_audio_button)
    lateinit var audioButton: ImageButton

    init {
        ButterKnife.bind(this, itemView)
    }

    override fun populate(position: Int, value: PinyinEntity) {
        characterTextView.text = value.chineseCharacters
        value.audioSrc?.let { audioButton.visible() } ?: audioButton.gone()
    }
}