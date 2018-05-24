package com.memtrip.pinyin.app

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.memtrip.pinyin.R
import com.memtrip.pinyin.app.favourites.PinyinFavouriteFragment
import com.memtrip.pinyin.app.list.PinyinListFragment

internal class PinyinFragmentAdapter(
        context: Context,
        val fm: FragmentManager) : FragmentPagerAdapter(fm) {

    val pages: Array<PageSet> = arrayOf(
            PageSet(Page.LIST, context.getString(R.string.pinyin_activity_list_tab)))

    private fun create(position: Int): Fragment {

        val (page) = this.pages[position]

        when (page) {
            Page.LIST -> return PinyinListFragment.newInstance()
            Page.FAVOURITE -> return PinyinFavouriteFragment.newInstance()
            else -> throw IllegalStateException("Page Fragment has not been defined.")
        }
    }

    override fun getItem(position: Int): Fragment {
        val fragment = fm.findFragmentByTag(pages[position].title.hashCode().toString())
        return fragment ?: create(position)
    }

    override fun getPageTitle(position: Int): CharSequence? = pages.get(position).title

    override fun getItemId(position: Int): Long = pages.get(position).title.hashCode().toLong()

    override fun getCount(): Int = pages.size
}

internal data class PageSet constructor(
        val page: Page,
        val title: String)

internal enum class Page {
    LIST,
    FAVOURITE
}