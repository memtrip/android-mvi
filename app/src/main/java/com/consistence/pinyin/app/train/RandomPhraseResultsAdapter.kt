package com.consistence.pinyin.app.train

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.consistence.pinyin.R
import com.consistence.pinyin.domain.study.Study
import com.consistence.pinyin.kit.*
import io.reactivex.subjects.PublishSubject

import kotlinx.android.synthetic.main.train_random_results_list_item.view.*

class RandomPhraseResultsAdapter(
    context: Context
) : SimpleAdapter<Pair<Study, Boolean>>(context, PublishSubject.create()) {

    override fun createViewHolder(parent: ViewGroup): SimpleAdapterViewHolder<Pair<Study, Boolean>> {
        return StudyViewHolder(inflater.inflate(
            R.layout.train_random_results_list_item, parent, false))
    }
}

class StudyViewHolder(itemView: View) : SimpleAdapterViewHolder<Pair<Study, Boolean>>(itemView) {

    override fun populate(position: Int, value: Pair<Study, Boolean>) {
        val (study, correct) = value

        itemView.train_random_results_list_card.populate(study, false)

        if (correct) {
            itemView.train_random_results_item.setBackgroundColor(
                ContextCompat.getColor(itemView.context, R.color.colorPositive))
        } else {
            itemView.train_random_results_item.setBackgroundColor(
                ContextCompat.getColor(itemView.context, R.color.colorAccent))
        }
    }
}