package com.consistence.pinyin.app.list

import com.consistence.pinyin.TestSchedulerProvider
import com.consistence.pinyin.api.EnglishSearch
import com.consistence.pinyin.api.PinyinDao
import com.consistence.pinyin.api.PinyinEntity
import com.consistence.pinyin.app.list.english.PinyinEnglishModel
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.Arrays.asList

@RunWith(JUnitPlatform::class)
class PinyinEnglishModelTest: Spek({

    given("PinyinListIntent.Search", {

        on("Pinyin entries exist for search query", {

            val pinyinList: List<PinyinEntity> = asList(mock())

            val search: EnglishSearch = mock {
                on { search("hello") }.doReturn(Single.just(pinyinList))
            }

            val model = PinyinEnglishModel(search, mock())
            val stateSequence = model.reducer(PinyinListIntent.Search("hello")).blockingIterable().asSequence()

            it("should return PinyinListState.Populate", {
                assertEquals(PinyinListState.Populate(pinyinList), stateSequence.elementAt(0))
            })
        })

        on("OnError searching pinyin", {

            val pinyinDao: PinyinDao = mock {
                on { englishSearch("%hello%") }.doThrow(IllegalStateException())
            }

            val search = EnglishSearch(pinyinDao, TestSchedulerProvider())

            val model = PinyinEnglishModel(search, mock())
            val stateSequence = model.reducer(PinyinListIntent.Search("hello")).blockingIterable().asSequence()

            it("should return PinyinListState.OnError", {
                assertEquals(PinyinListState.OnError, stateSequence.elementAt(0))
            })
        })
    })

    given("PinyinListIntent.SelectItem", {

        on("pinyin entry selected", {

            val pinyin: PinyinEntity = mock()

            val model = PinyinEnglishModel(mock(), mock())
            val stateSequence = model.reducer(PinyinListIntent.SelectItem(pinyin)).blockingIterable().asSequence()

            it("should return PinyinListState.NavigateToDetails", {
                assertEquals(PinyinListState.NavigateToDetails(pinyin), stateSequence.elementAt(0))
            })
        })
    })

    given("PinyinListIntent.PlayAudio", {

        on("select to play pinyin audio", {

            val model = PinyinEnglishModel(mock(), mock())
            val stateSequence = model.reducer(PinyinListIntent.PlayAudio("file://audio"))
                    .blockingIterable().asSequence()

            it("should return PinyinListState.NavigateToDetails", {
                assertEquals(PinyinListState.PlayAudio("file://audio"), stateSequence.elementAt(0))
            })
        })
    })
})