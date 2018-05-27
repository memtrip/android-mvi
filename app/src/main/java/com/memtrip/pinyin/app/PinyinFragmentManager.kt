package com.memtrip.pinyin.app

import android.content.Context
import android.support.annotation.IdRes
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.memtrip.pinyin.Event
import com.memtrip.pinyin.PresenterFragment
import com.memtrip.pinyin.R
import com.memtrip.pinyin.app.list.character.PinyinCharacterFragment
import com.memtrip.pinyin.app.list.english.PinyinEnglishFragment
import com.memtrip.pinyin.app.list.phonetic.PinyinPhoneticFragment

internal class PinyinFragmentAdapter(@IdRes val container: Int,
                                     tabLayout: TabLayout,
                                     val fm: FragmentManager,
                                     context: Context) {

    val pages: LinkedHashMap<Page, PageFragment> = LinkedHashMap()

    var currentPosition = 0
    var firstFragment = true

    init {
        pages.put(Page.PHONETIC, PhoneticPageFragment(context))
        pages.put(Page.ENGLISH, EnglishPageFragment(context))
        pages.put(Page.CHARACTER, CharacterPageFragment(context))

        createTab(Page.PHONETIC, tabLayout)
        createTab(Page.ENGLISH, tabLayout)
        createTab(Page.CHARACTER, tabLayout)

        // set initial fragment
        setFragment()

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListenerAdapter() {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                setFragment(tab!!.position)
            }
        })
    }

    fun sendEvent(event: Event) {
        pages.values.toTypedArray().map {
            val presenterFragment = it.getFragment() as PresenterFragment<*>
            if (presenterFragment.isAdded) {
                presenterFragment.sendEvent(event)
            }
        }
    }

    private fun setFragment(newPosition: Int = 0) {

        val fragment = pages.values.toTypedArray()
                .get(newPosition)
                .getFragment()

        val transaction = fm.beginTransaction()

        if (firstFragment) {
            firstFragment = false
            transaction.setCustomAnimations(R.anim.pinyin_list_transition_enter_bottom, R.anim.pinyin_list_transition_exit_left)
        } else if (newPosition > currentPosition) {
            transaction.setCustomAnimations(R.anim.pinyin_list_transition_enter_bottom, R.anim.pinyin_list_transition_exit_left)
        } else {
            transaction.setCustomAnimations(R.anim.pinyin_list_transition_enter_bottom, R.anim.pinyin_list_transition_exit_right)
        }

        transaction.replace(container, fragment)
        transaction.commit()

        currentPosition = newPosition
    }

    private fun createTab(page: Page, tabLayout: TabLayout) {
        val tab = tabLayout.newTab()
        tab.text = pages[page]!!.title
        tabLayout.addTab(tab)
    }
}

internal abstract class PageFragment constructor(val title: String) {
    abstract fun getFragment() : Fragment
}

internal class PhoneticPageFragment(context: Context) : PageFragment(
        context.getString(R.string.pinyin_activity_phonetic_tab)) {

    private var pinyinPhoneticFragment: PinyinPhoneticFragment? = null

    fun createPinyinPhoneticFragment() : PinyinPhoneticFragment =
            pinyinPhoneticFragment?.let { it } ?: PinyinPhoneticFragment.newInstance()


    override fun getFragment(): Fragment {
        pinyinPhoneticFragment = createPinyinPhoneticFragment()
        return pinyinPhoneticFragment as Fragment
    }
}

internal class EnglishPageFragment(context: Context) : PageFragment(
        context.getString(R.string.pinyin_activity_english_tab)) {

    private var pinyinEnglishFragment: PinyinEnglishFragment? = null

    fun createPinyinEnglishFragment() : PinyinEnglishFragment =
            pinyinEnglishFragment?.let { it } ?: PinyinEnglishFragment.newInstance()


    override fun getFragment(): Fragment {
        pinyinEnglishFragment = createPinyinEnglishFragment()
        return pinyinEnglishFragment as Fragment
    }
}

internal class CharacterPageFragment(context: Context) : PageFragment(
        context.getString(R.string.pinyin_activity_character_tab)) {

    private var pinyinCharacterFragment: PinyinCharacterFragment? = null

    fun createPinyinCharacterFragment() : PinyinCharacterFragment =
            pinyinCharacterFragment?.let { it } ?: PinyinCharacterFragment.newInstance()

    override fun getFragment(): Fragment {
        pinyinCharacterFragment = createPinyinCharacterFragment()
        return pinyinCharacterFragment as Fragment
    }
}

enum class Page {
    PHONETIC,
    ENGLISH,
    CHARACTER
}

abstract class OnTabSelectedListenerAdapter : TabLayout.OnTabSelectedListener {
    override fun onTabReselected(tab: TabLayout.Tab?) { }
    override fun onTabUnselected(tab: TabLayout.Tab?) { }
    override fun onTabSelected(tab: TabLayout.Tab?) { }
}
