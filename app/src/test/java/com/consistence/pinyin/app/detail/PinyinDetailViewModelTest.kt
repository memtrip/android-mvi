package com.consistence.pinyin.app.detail

import com.consistence.pinyin.get
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class PinyinDetailViewModelTest : Spek({

    given("a PinyinDetailsViewModel") {

        val pinyinParcel by memoized { mock<PinyinParcel>() }

        val viewModel by memoized { PinyinDetailViewModel(pinyinParcel, mock()) }

        on("Populate pinyin details without audio") {

            whenever(pinyinParcel.phoneticScriptText).doReturn("nĭ")
            whenever(pinyinParcel.englishTranslationText).doReturn("you")
            whenever(pinyinParcel.chineseCharacters).doReturn("你")

            val states = viewModel.states().test()

            viewModel.processIntents(Observable.just(PinyinDetailIntent.Idle))

            it("should return phoneticScriptText, englishTranslationText, chineseCharacters" +
                    "with audioSrc null and with a None Action") {
                val element = states.get(0)
                assertThat(element.phoneticScriptText).isEqualTo("nĭ")
                assertThat(element.englishTranslationText).isEqualTo("you")
                assertThat(element.chineseCharacters).isEqualTo("你")
                assertThat(element.audioSrc).isNull()
                assertThat(element.action).isEqualTo(PinyinDetailViewState.Action.None)
            }
        }
    }
})