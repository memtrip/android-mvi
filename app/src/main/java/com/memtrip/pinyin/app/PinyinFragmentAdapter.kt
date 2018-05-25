package com.memtrip.pinyin.app

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.memtrip.pinyin.Event
import com.memtrip.pinyin.PresenterFragment
import com.memtrip.pinyin.R
import com.memtrip.pinyin.app.favourites.PinyinFavouriteFragment
import com.memtrip.pinyin.app.list.PinyinListFragment

internal class PinyinFragmentAdapter(context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var pinyinListFragment: PinyinListFragment? = null
    private var pinyinFavouriteFragment: PinyinFavouriteFragment? = null

    val pages: Array<PageSet> = arrayOf(PageSet(Page.LIST, context.getString(R.string.pinyin_activity_list_tab)))

    fun sendEvent(position: Int, event: Event) {
        val fragment = getItem(position) as PresenterFragment<*>
        if (fragment.isAdded) {
            fragment.sendEvent(event)
        }
    }

    fun createPinyinListFragment() : PinyinListFragment {
        return pinyinListFragment?.let { it }
                ?: return PinyinListFragment.newInstance()
    }

    fun createPinyinFavouriteFragment() : PinyinFavouriteFragment {
        return pinyinFavouriteFragment?.let { it }
                ?: return PinyinFavouriteFragment.newInstance()
    }

    override fun getItem(position: Int): Fragment {
        val (page) = this.pages[position]

        when (page) {
            Page.LIST -> {
                pinyinListFragment = createPinyinListFragment()
                return pinyinListFragment as Fragment
            }
            Page.FAVOURITE -> {
                pinyinFavouriteFragment = createPinyinFavouriteFragment()
                return pinyinFavouriteFragment as Fragment
            }
            else ->
                throw IllegalStateException("Page Fragment has not been defined.")
        }
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