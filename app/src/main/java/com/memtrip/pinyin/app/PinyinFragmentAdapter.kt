package com.memtrip.pinyin.app

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.memtrip.pinyin.Event
import com.memtrip.pinyin.PresenterFragment
import com.memtrip.pinyin.R
import com.memtrip.pinyin.app.list.character.PinyinCharacterFragment
import com.memtrip.pinyin.app.list.english.PinyinEnglishFragment
import com.memtrip.pinyin.app.list.phonetic.PinyinPhoneticFragment

internal class PinyinFragmentAdapter(context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var pinyinPhoneticFragment: PinyinPhoneticFragment? = null
    private var pinyinCharacterFragment: PinyinCharacterFragment? = null
    private var pinyinEnglishFragment: PinyinEnglishFragment? = null

    val pages: Array<PageSet> = arrayOf(
            PageSet(Page.PHONETIC, context.getString(R.string.pinyin_activity_phonetic_tab)),
            PageSet(Page.ENGLISH, context.getString(R.string.pinyin_activity_english_tab)),
            PageSet(Page.CHARACTER, context.getString(R.string.pinyin_activity_character_tab))
    )

    fun sendEvent(position: Int, event: Event) {
        val fragment = getItem(position) as PresenterFragment<*>
        if (fragment.isAdded) {
            fragment.sendEvent(event)
        }
    }

    fun createPinyinPhoneticFragment() : PinyinPhoneticFragment {
        return pinyinPhoneticFragment?.let { it }
                ?: return PinyinPhoneticFragment.newInstance()
    }

    fun createPinyinCharacterFragment() : PinyinCharacterFragment {
        return pinyinCharacterFragment?.let { it }
                ?: return PinyinCharacterFragment.newInstance()
    }

    fun createPinyinEnglishFragment() : PinyinEnglishFragment {
        return pinyinEnglishFragment?.let { it }
                ?: return PinyinEnglishFragment.newInstance()
    }

    override fun getItem(position: Int): Fragment {
        val (page) = this.pages[position]

        when (page) {
            Page.PHONETIC -> {
                pinyinPhoneticFragment = createPinyinPhoneticFragment()
                return pinyinPhoneticFragment as Fragment
            }
            Page.ENGLISH -> {
                pinyinEnglishFragment = createPinyinEnglishFragment()
                return pinyinEnglishFragment as Fragment
            }
            Page.CHARACTER -> {
                pinyinCharacterFragment = createPinyinCharacterFragment()
                return pinyinCharacterFragment as Fragment
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
    PHONETIC,
    ENGLISH,
    CHARACTER
}