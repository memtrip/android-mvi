package com.consistence.pinyin.app

import com.consistence.pinyin.TestSchedulerProvider
import com.consistence.pinyin.api.CountPinyin
import com.consistence.pinyin.api.FetchAndSavePinyin
import com.consistence.pinyin.api.PinyinDao
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
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
class EntryModelTest: Spek({

    given("EntryIntent.Init", {

        on("Pinyin entries already exist", {

            val countPinyin: CountPinyin = mock {
                on { count() }.doReturn(Single.just(1))
            }

            val model = EntryModel(mock(), countPinyin, mock())
            val stateSequence = model.reducer(EntryIntent.Init).blockingIterable().asSequence()

            it("should return EntryState.OnPinyinLoaded", {
                assertEquals(EntryState.OnProgress, stateSequence.elementAt(0))
                assertEquals(EntryState.OnPinyinLoaded, stateSequence.elementAt(1))
            })
        })

        on("Failed to count pinyin", {

            val pinyinDao: PinyinDao = mock {
                on { count() }.doThrow(IllegalStateException())
            }

            val countPinyin = CountPinyin(pinyinDao, TestSchedulerProvider())

            val model = EntryModel(mock(), countPinyin, mock())
            val stateSequence = model.reducer(EntryIntent.Init).blockingIterable().asSequence()

            it("should return EntryState.OnError", {
                assertEquals(EntryState.OnProgress, stateSequence.elementAt(0))
                assertEquals(EntryState.OnError, stateSequence.elementAt(1))
            })
        })

        on("Fetch pinyin entries", {

            val countPinyin: CountPinyin = mock {
                on { count() }.doReturn(Single.just(0))
            }

            val fetchAndSavePinyin:FetchAndSavePinyin = mock()
            whenever(fetchAndSavePinyin.save()).thenReturn(Single.just(asList(mock())))

            val model = EntryModel(fetchAndSavePinyin, countPinyin, mock())
            val stateSequence = model.reducer(EntryIntent.Init).blockingIterable().asSequence()

            it("should return EntryState.OnPinyinLoaded", {
                assertEquals(EntryState.OnProgress, stateSequence.elementAt(0))
                assertEquals(EntryState.OnPinyinLoaded, stateSequence.elementAt(1))
            })
        })

        on("Failed to fetch pinyin entries", {

            val countPinyin: CountPinyin = mock {
                on { count() }.doReturn(Single.just(0))
            }

            val fetchAndSavePinyin:FetchAndSavePinyin = mock()
            whenever(fetchAndSavePinyin.save()).thenReturn(Single.error(IllegalStateException()))

            val model = EntryModel(fetchAndSavePinyin, countPinyin, mock())
            val stateSequence = model.reducer(EntryIntent.Init).blockingIterable().asSequence()

            it("should return EntryState.OnError", {
                assertEquals(EntryState.OnProgress, stateSequence.elementAt(0))
                assertEquals(EntryState.OnError, stateSequence.elementAt(1))
            })
        })
    })
})