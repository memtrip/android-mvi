package com.consistence.pinyin.app.list.phonetic

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.consistence.pinyin.R
import com.consistence.pinyin.api.PinyinEntity
import com.consistence.pinyin.kit.*
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject

class PinyinPhoneticAdapter(
        context: Context,
        interaction: PublishSubject<Interaction<PinyinEntity>>) : SimpleAdapter<PinyinEntity>(context, interaction) {

    override fun createViewHolder(parent: ViewGroup): SimpleAdapterViewHolder<PinyinEntity> {
        val viewHolder = PinyinPhoneticViewHolder(inflater.inflate(
                R.layout.pinyin_phonetic_list_item, parent, false))

        RxView.clicks(viewHolder.audioButton)
                .map({Interaction(viewHolder.audioButton.id, data[viewHolder.adapterPosition])})
                .subscribe(interaction)

        return viewHolder
    }
}

class PinyinPhoneticViewHolder(itemView: View) : SimpleAdapterViewHolder<PinyinEntity>(itemView) {

    @BindView(R.id.pinyin_phonetic_list_item_value)
    lateinit var phoneticTextView: TextView

    @BindView(R.id.pinyin_list_audio_button)
    lateinit var audioButton: ImageButton

    init {
        ButterKnife.bind(this, itemView)
    }

    override fun populate(position: Int, value: PinyinEntity) {
        phoneticTextView.text = value.phoneticScriptText
        value.audioSrc?.let { audioButton.visible() } ?: audioButton.gone()
    }
}