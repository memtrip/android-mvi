package com.memtrip.pinyin.app.list

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.memtrip.pinyin.AdapterEvent
import com.memtrip.pinyin.R
import com.memtrip.pinyin.api.PinyinEntity
import com.memtrip.pinyin.kit.SimpleAdapter
import com.memtrip.pinyin.kit.SimpleAdapterViewHolder
import io.reactivex.functions.Consumer

class PinyinListAdapter(
        context: Context,
        interaction: Consumer<AdapterEvent<PinyinEntity>>) : SimpleAdapter<PinyinEntity>(context, interaction) {

    override fun createViewHolder(parent: ViewGroup): SimpleAdapterViewHolder<PinyinEntity> {
        return PinyinListViewHolder(inflater.inflate(
                R.layout.pinyin_list_item, parent, false))
    }
}

class PinyinListViewHolder(itemView: View) : SimpleAdapterViewHolder<PinyinEntity>(itemView) {

    @BindView(R.id.pinyin_list_item_phonetic_script_value)
    lateinit var phoneticTextView: TextView

    init {
        ButterKnife.bind(this, itemView)
    }

    override fun populate(position: Int, value: PinyinEntity) {
        phoneticTextView.text = value.phoneticScriptText
    }
}