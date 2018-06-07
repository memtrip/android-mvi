package com.consistence.pinyin.app.list.english

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.jakewharton.rxbinding2.view.RxView

import com.consistence.pinyin.R
import com.consistence.pinyin.api.PinyinEntity
import com.consistence.pinyin.kit.*

import io.reactivex.subjects.PublishSubject

class PinyinEnglishAdapter(
        context: Context,
        interaction: PublishSubject<Interaction<PinyinEntity>>) : SimpleAdapter<PinyinEntity>(context, interaction) {

    override fun createViewHolder(parent: ViewGroup): SimpleAdapterViewHolder<PinyinEntity> {
        val viewHolder = PinyinEnglishViewHolder(inflater.inflate(
                R.layout.pinyin_english_list_item, parent, false))

        RxView.clicks(viewHolder.audioButton)
                .map({Interaction(viewHolder.audioButton.id, data[viewHolder.adapterPosition]) })
                .subscribe(interaction)

        return viewHolder
    }
}

class PinyinEnglishViewHolder(itemView: View) : SimpleAdapterViewHolder<PinyinEntity>(itemView) {

    @BindView(R.id.pinyin_english_list_item_value)
    lateinit var englishTextView: TextView

    @BindView(R.id.pinyin_list_audio_button)
    lateinit var audioButton: ImageButton

    init {
        ButterKnife.bind(this, itemView)
    }

    override fun populate(position: Int, value: PinyinEntity) {
        englishTextView.text = value.englishTranslationText
        value.audioSrc?.let { audioButton.visible() } ?: audioButton.gone()
    }
}