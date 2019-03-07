package com.consistence.pinyin.app

import android.app.Application
import com.consistence.pinyin.R
import com.consistence.pinyin.app.pinyin.Page
import com.consistence.pinyin.app.pinyin.PinyinIntent
import com.consistence.pinyin.app.pinyin.PinyinViewModel
import com.consistence.pinyin.app.pinyin.PinyinViewState
import com.consistence.pinyin.get
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class PinyinViewModelTest : Spek({

    given("a PinyinViewModel") {

        val context: Application = mock {
            on {
                getString(R.string.pinyin_activity_search_phonetic_hint)
            } doReturn ("phonetic hint")

            on {
                getString(R.string.pinyin_activity_search_english_hint)
            } doReturn ("english hint")

            on {
                getString(R.string.pinyin_activity_search_character_hint)
            } doReturn ("character hint")
        }

        val viewModel by memoized { PinyinViewModel(context) }

        on("Phonetic tab selected") {

            viewModel.processIntents(intents = Observable.just(PinyinIntent.TabSelected(Page.PHONETIC)))

            val state = viewModel.states().test()

            it("should update the search bar with the phonetic hint") {
                assertThat(state.get(0)).isEqualTo(PinyinViewState("phonetic hint"))
            }
        }

        on("English tab selected") {

            viewModel.processIntents(intents = Observable.just(PinyinIntent.TabSelected(Page.ENGLISH)))

            val state = viewModel.states().test()

            it("should update the search bar with the english hint") {
                assertThat(state.get(0)).isEqualTo(PinyinViewState("english hint"))
            }
        }

        on("Character tab selected") {

            viewModel.processIntents(intents = Observable.just(PinyinIntent.TabSelected(Page.CHARACTER)))

            val state = viewModel.states().test()

            it("should update the search bar with the character hint") {
                assertThat(state.get(0)).isEqualTo(PinyinViewState("character hint"))
            }
        }
    }
})