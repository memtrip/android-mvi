package com.consistence.pinyin.app

import com.consistence.pinyin.database.pinyin.CountPinyin
import com.consistence.pinyin.database.pinyin.FetchAndSavePinyin
import com.consistence.pinyin.get
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
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
class EntryViewModelTest : Spek({

    given("a EntryViewModel") {

        val fetchAndSavePinyin by memoized { mock<FetchAndSavePinyin>() }

        val countPinyin by memoized { mock<CountPinyin>() }

        val viewModel by memoized { EntryViewModel(fetchAndSavePinyin, countPinyin, mock()) }

        on("pinyin entries already exist") {

            whenever(countPinyin.count()).doReturn(Single.just(1))

            val state = viewModel.states().test()

            viewModel.processIntents(intents = Observable.just(EntryIntent.Init))

            it("should show pinyin loaded") {
                assertEquals(EntryViewState(view = EntryViewState.View.OnProgress), state.get(0))
                assertEquals(EntryViewState(view = EntryViewState.View.OnPinyinLoaded), state.get(1))
            }
        }

        on("failed to count pinyin") {

            whenever(countPinyin.count()).doReturn(Single.error(IllegalStateException()))

            val state = viewModel.states().test()

            viewModel.processIntents(intents = Observable.just(EntryIntent.Init))

            it("should show an error") {
                assertEquals(EntryViewState(view = EntryViewState.View.OnProgress), state.get(0))
                assertEquals(EntryViewState(view = EntryViewState.View.OnError), state.get(1))
            }
        }

        on("fetch pinyin entries") {

            whenever(countPinyin.count()).doReturn(Single.just(0))
            whenever(fetchAndSavePinyin.save()).doReturn(Single.just(asList(mock())))

            val state = viewModel.states().test()

            viewModel.processIntents(intents = Observable.just(EntryIntent.Init))

            it("should show pinyin loaded") {
                assertEquals(EntryViewState(view = EntryViewState.View.OnProgress), state.get(0))
                assertEquals(EntryViewState(view = EntryViewState.View.OnPinyinLoaded), state.get(1))
            }
        }

        on("failed to fetch pinyin entries") {

            whenever(countPinyin.count()).doReturn(Single.just(0))
            whenever(fetchAndSavePinyin.save()).doThrow(IllegalStateException())

            val state = viewModel.states().test()

            viewModel.processIntents(intents = Observable.just(EntryIntent.Init))

            it("should show an error") {
                assertEquals(EntryViewState(view = EntryViewState.View.OnProgress), state.get(0))
                assertEquals(EntryViewState(view = EntryViewState.View.OnError), state.get(1))
            }
        }
    }
})