package com.consistence.pinyin.app

import android.content.Context
import android.support.annotation.IdRes
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import com.consistence.pinyin.R
import com.consistence.pinyin.ViewFragment
import com.consistence.pinyin.app.list.*
import com.consistence.pinyin.app.list.character.PinyinCharacterFragment
import com.consistence.pinyin.app.list.english.PinyinEnglishFragment
import com.consistence.pinyin.app.list.phonetic.PinyinPhoneticFragment

internal class PinyinFragmentAdapter(@IdRes val container: Int,
                                     tabLayout: TabLayout,
                                     private val fm: FragmentManager,
                                     context: Context) {

    private val pages: LinkedHashMap<Page, PageFragment> = LinkedHashMap()

    private var currentPosition = 0
    private var firstFragment = true

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

    fun sendIntent(intent: PinyinListIntent) {
        pages.values.toTypedArray().map {
            val viewFragment = it.getFragment()
            if (viewFragment.isAdded) {
                viewFragment.model().intents.onNext(intent)
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
    abstract fun getFragment() : ViewFragment<PinyinListIntent, PinyinListState, PinyinListModel, PinyinListRender>
}

internal class PhoneticPageFragment(context: Context) : PageFragment(
        context.getString(R.string.pinyin_activity_phonetic_tab)) {

    private var pinyinPhoneticFragment: PinyinPhoneticFragment? = null

    fun createPinyinPhoneticFragment() : PinyinPhoneticFragment =
            pinyinPhoneticFragment?.let { it } ?: PinyinPhoneticFragment.newInstance()


    override fun getFragment(): PinyinListFragment {
        pinyinPhoneticFragment = createPinyinPhoneticFragment()
        return pinyinPhoneticFragment as PinyinListFragment
    }
}

internal class EnglishPageFragment(context: Context) : PageFragment(
        context.getString(R.string.pinyin_activity_english_tab)) {

    private var pinyinEnglishFragment: PinyinEnglishFragment? = null

    fun createPinyinEnglishFragment() : PinyinEnglishFragment =
            pinyinEnglishFragment?.let { it } ?: PinyinEnglishFragment.newInstance()


    override fun getFragment(): PinyinListFragment {
        pinyinEnglishFragment = createPinyinEnglishFragment()
        return pinyinEnglishFragment as PinyinListFragment
    }
}

internal class CharacterPageFragment(context: Context) : PageFragment(
        context.getString(R.string.pinyin_activity_character_tab)) {

    private var pinyinCharacterFragment: PinyinCharacterFragment? = null

    fun createPinyinCharacterFragment() : PinyinCharacterFragment =
            pinyinCharacterFragment?.let { it } ?: PinyinCharacterFragment.newInstance()

    override fun getFragment(): PinyinListFragment {
        pinyinCharacterFragment = createPinyinCharacterFragment()
        return pinyinCharacterFragment as PinyinListFragment
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
