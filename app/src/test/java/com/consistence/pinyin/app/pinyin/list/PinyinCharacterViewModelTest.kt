package com.consistence.pinyin.app.pinyin.list

import com.consistence.pinyin.domain.pinyin.db.CharacterSearch
import com.consistence.pinyin.domain.pinyin.db.PinyinEntity
import com.consistence.pinyin.app.pinyin.list.character.PinyinCharacterViewModel
import com.consistence.pinyin.get
import com.memtrip.mxandroid.MxViewState
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.mockk.every
import io.mockk.mockkObject
import io.reactivex.Observable
import io.reactivex.Single
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.Arrays.asList

@RunWith(JUnitPlatform::class)
class PinyinCharacterViewModelTest : Spek({

    mockkObject(MxViewState)
    every {
        MxViewState.id()
    } returns 0

    given("a PinyinCharacterViewModel") {

        val search by memoized { mock<CharacterSearch>() }

        val viewModel by memoized { PinyinCharacterViewModel(search, mock()) }

        on("Pinyin entries exist for search query") {

            val pinyinList: List<PinyinEntity> = asList(mock())

            whenever(search.search("汉语")).doReturn(Single.just(pinyinList))

            viewModel.processIntents(Observable.just(PinyinListIntent.Search("汉语")))

            val states = viewModel.states().test()

            it("should populate the view with the pinyin list") {
                assertEquals(
                        PinyinListViewState(view = PinyinListViewState.View.Populate(pinyinList)),
                        states.get(0))
            }
        }

        on("Pinyin entries could not be fetched") {

            whenever(search.search("汉语")).doReturn(Single.error(IllegalStateException()))

            viewModel.processIntents(Observable.just(PinyinListIntent.Search("汉语")))

            val states = viewModel.states().test()

            it("should show an error") {
                assertEquals(
                        PinyinListViewState(view = PinyinListViewState.View.OnError),
                        states.get(0))
            }
        }

        on("pinyin entry selected") {

            val pinyin: PinyinEntity = mock()

            viewModel.processIntents(Observable.just(PinyinListIntent.SelectItem(pinyin)))

            val states = viewModel.states().test()

            it("should navigate to pinyin details") {
                assertEquals(
                        PinyinListViewState(view = PinyinListViewState.View.SelectItem(pinyin)),
                        states.get(0))
            }
        }

        on("play pinyin audio selected") {

            viewModel.processIntents(Observable.just(PinyinListIntent.PlayAudio("file://audio")))

            val states = viewModel.states().test()

            it("should play the audio") {
                assertEquals(
                        PinyinListViewState(view = PinyinListViewState.View.PlayAudio("file://audio", 0)),
                        states.get(0))
            }
        }
    }
})