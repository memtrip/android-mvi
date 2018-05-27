package com.consistence.pinyin.kit

import android.content.Context

import android.support.annotation.Nullable

import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

class SimpleRecyclerView @JvmOverloads constructor(
        context: Context,
        @Nullable attrs: AttributeSet? = null,
        defStyle: Int = 0) : RecyclerView(context, attrs, defStyle) {

    private lateinit var simpleAdapter: SimpleAdapter<*>

    init {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView!!.canScrollVertically(1))
                    atEnd()
            }
        })
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<*>) {

        if (adapter !is SimpleAdapter<*>)
            throw IllegalStateException("SimpleRecyclerView must use a SimpleAdapter as its source")

        super.setAdapter(adapter)

        simpleAdapter = adapter
    }

    private fun atEnd() {
        simpleAdapter.atEnd(id)
    }
}