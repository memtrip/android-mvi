package com.consistence.pinyin.kit

import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject

abstract class SimpleAdapter<T>(
        context: Context,
        protected val interaction: PublishSubject<Interaction<T>>,
        protected val inflater: LayoutInflater = LayoutInflater.from(context),
        internal val data: MutableList<T> = ArrayList()) : RecyclerView.Adapter<SimpleAdapterViewHolder<T>>() {

    private lateinit var recyclerView: RecyclerView

    fun insert(item: T) {
        data.add(0, item)
        notifyDataSetChanged()
    }

    fun populate(items: List<T>) {
        this.data.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleAdapterViewHolder<T> {

        val viewHolder = createViewHolder(parent)

        RxView.clicks(viewHolder.itemView)
                .map({ Interaction(viewHolder.itemView.id, data[viewHolder.adapterPosition]) })
                .subscribe(interaction)

        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: SimpleAdapterViewHolder<T>, position: Int) {
        viewHolder.populate(position, data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return DEFAULT_ITEM_TYPE
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    internal fun atEnd(id: Int) {
        //interaction.accept(AdapterEnd(id, data.last()))
    }

    abstract fun createViewHolder(parent: ViewGroup): SimpleAdapterViewHolder<T>

    companion object {
        private val DEFAULT_ITEM_TYPE = 0x100
    }
}

abstract class SimpleAdapterViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun populate(position: Int, value: T)

    fun getString(@StringRes id: Int): String {
        return itemView.context.getString(id)
    }
}

data class Interaction<T>(@IdRes val id: Int, val data: T)