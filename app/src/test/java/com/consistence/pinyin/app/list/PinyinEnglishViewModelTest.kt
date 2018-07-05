package com.consistence.pinyin.app.list

import com.consistence.pinyin.api.EnglishSearch
import com.consistence.pinyin.api.PinyinEntity
import com.consistence.pinyin.app.list.english.PinyinEnglishViewModel
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.Single
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.Arrays.asList

@RunWith(JUnitPlatform::class)
class PinyinEnglishViewModelTest : Spek({

    given("PinyinListIntent.Search") {

        val search by memoized { mock<EnglishSearch>() }

        val viewModel by memoized { PinyinEnglishViewModel(search, mock()) }

        on("Pinyin entries exist for search query") {

            val pinyinList: List<PinyinEntity> = asList(mock())

            whenever(search.search("hello")).doReturn(Single.just(pinyinList))

            val states = viewModel.states().blockingIterable().asSequence()

            viewModel.processIntents(Observable.just(PinyinListIntent.Search("hello")))

            it("should populate the view with the pinyin list") {
                Assert.assertEquals(
                        PinyinListViewState(view = PinyinListViewState.View.Populate(pinyinList)),
                        states.elementAt(0))
            }
        }

        on("Pinyin entries could not be fetched") {

            whenever(search.search("hello")).doReturn(Single.error(IllegalStateException()))

            val states = viewModel.states().blockingIterable().asSequence()

            viewModel.processIntents(Observable.just(PinyinListIntent.Search("hello")))

            it("should show an error") {
                Assert.assertEquals(
                        PinyinListViewState(view = PinyinListViewState.View.OnError),
                        states.elementAt(0))
            }
        }

        on("pinyin entry selected") {

            val pinyin: PinyinEntity = mock()

            val states = viewModel.states().blockingIterable().asSequence()

            viewModel.processIntents(Observable.just(PinyinListIntent.SelectItem(pinyin)))

            it("should navigate to pinyin details") {
                Assert.assertEquals(
                        PinyinListViewState(view = PinyinListViewState.View.SelectItem(pinyin)),
                        states.elementAt(0))
            }
        }

        on("select to play pinyin audio") {

            val states = viewModel.states().blockingIterable().asSequence()

            viewModel.processIntents(Observable.just(PinyinListIntent.PlayAudio("file://audio")))

            it("should play the audio") {
                Assert.assertEquals(
                        PinyinListViewState(view = PinyinListViewState.View.PlayAudio("file://audio")),
                        states.elementAt(0))
            }
        }
    }
})