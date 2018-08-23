package com.consistence.pinyin.app

import android.content.Context
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.consistence.pinyin.R
import com.consistence.pinyin.app.list.PinyinListFragment
import com.consistence.pinyin.app.list.PinyinListIntent
import com.consistence.pinyin.app.list.PinyinListLayout
import com.consistence.pinyin.app.list.PinyinListRenderAction
import com.consistence.pinyin.app.list.PinyinListViewState
import com.consistence.pinyin.app.list.character.PinyinCharacterFragment
import com.consistence.pinyin.app.list.english.PinyinEnglishFragment
import com.consistence.pinyin.app.list.phonetic.PinyinPhoneticFragment
import com.google.android.material.tabs.TabLayout
import com.memtrip.mxandroid.MxViewFragment

internal class PinyinFragmentAdapter(
    @IdRes val container: Int,
    tabLayout: TabLayout,
    private val fm: FragmentManager,
    context: Context
) {

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
                viewFragment.model().publish(intent)
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
    abstract fun getFragment(): MxViewFragment<PinyinListIntent, PinyinListRenderAction, PinyinListViewState, PinyinListLayout>
}

internal class PhoneticPageFragment(context: Context) : PageFragment(
        context.getString(R.string.pinyin_activity_phonetic_tab)) {

    private var fragment: PinyinPhoneticFragment? = null

    override fun getFragment(): PinyinListFragment {
        fragment = fragment?.let {
            it
        } ?: PinyinPhoneticFragment.newInstance()
        return fragment as PinyinListFragment
    }
}

internal class EnglishPageFragment(context: Context) : PageFragment(
        context.getString(R.string.pinyin_activity_english_tab)) {

    private var fragment: PinyinEnglishFragment? = null

    override fun getFragment(): PinyinListFragment {
        fragment = fragment?.let { it } ?: PinyinEnglishFragment.newInstance()
        return fragment as PinyinListFragment
    }
}

internal class CharacterPageFragment(context: Context) : PageFragment(
        context.getString(R.string.pinyin_activity_character_tab)) {

    private var fragment: PinyinCharacterFragment? = null

    override fun getFragment(): PinyinListFragment {
        fragment = fragment?.let { it } ?: PinyinCharacterFragment.newInstance()
        return fragment as PinyinListFragment
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
