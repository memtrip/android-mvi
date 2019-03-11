package com.consistence.pinyin.app.study

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater

import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.consistence.pinyin.R
import com.consistence.pinyin.domain.pinyin.formatChineseCharacterString
import com.consistence.pinyin.domain.study.Study
import com.consistence.pinyin.kit.gone
import com.consistence.pinyin.kit.visible
import kotlinx.android.synthetic.main.study_card_view.view.*

class StudyCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.study_card_view, this, true)
        setCardBackgroundColor(ContextCompat.getColor(context, R.color.cardBackgroundColor))
    }

    fun populate(study: Study, showTrainIcon: Boolean = false) {
        study_card_item_chinese_phrase_value.text = study.pinyin.formatChineseCharacterString()
        study_card_item_english_translation_value.text = study.englishTranslation

        if (showTrainIcon) {
            study_card_item_train.visible()
        } else {
            study_card_item_train.gone()
        }
    }
}