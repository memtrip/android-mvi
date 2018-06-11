package com.consistence.pinyin.app.list

import com.consistence.pinyin.TestSchedulerProvider
import com.consistence.pinyin.api.CharacterSearch
import com.consistence.pinyin.api.PinyinDao
import com.consistence.pinyin.api.PinyinEntity
import com.consistence.pinyin.app.list.character.PinyinCharacterModel
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.mock
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
class PinyinCharacterModelTest: Spek({

    given("PinyinListIntent.Search", {

        on("Pinyin entries exist for search query", {

            val pinyinList: List<PinyinEntity> = asList(mock())

            val search: CharacterSearch = mock {
                on { search("汉语") }.doReturn(Single.just(pinyinList))
            }

            val model = PinyinCharacterModel(search, mock())
            val stateSequence = model.reducer(PinyinListIntent.Search("汉语")).blockingIterable().asSequence()

            it("should return PinyinListState.Populate", {
                Assert.assertEquals(PinyinListState.Populate(pinyinList), stateSequence.elementAt(0))
            })
        })

        on("OnError searching pinyin", {

            val pinyinDao: PinyinDao = mock {
                on { characterSearch("汉语%") }.doThrow(IllegalStateException())
            }

            val search = CharacterSearch(pinyinDao, TestSchedulerProvider())

            val model = PinyinCharacterModel(search, mock())
            val stateSequence = model.reducer(PinyinListIntent.Search("汉语")).blockingIterable().asSequence()

            it("should return PinyinListState.OnError", {
                Assert.assertEquals(PinyinListState.OnError, stateSequence.elementAt(0))
            })
        })
    })

    given("PinyinListIntent.SelectItem", {

        on("pinyin entry selected", {

            val pinyin: PinyinEntity = mock()

            val model = PinyinCharacterModel(mock(), mock())
            val stateSequence = model.reducer(PinyinListIntent.SelectItem(pinyin)).blockingIterable().asSequence()

            it("should return PinyinListState.NavigateToDetails", {
                Assert.assertEquals(PinyinListState.NavigateToDetails(pinyin), stateSequence.elementAt(0))
            })
        })
    })

    given("PinyinListIntent.PlayAudio", {

        on("select to play pinyin audio", {

            val model = PinyinCharacterModel(mock(), mock())
            val stateSequence = model.reducer(PinyinListIntent.PlayAudio("file://audio"))
                    .blockingIterable().asSequence()

            it("should return PinyinListState.NavigateToDetails", {
                Assert.assertEquals(PinyinListState.PlayAudio("file://audio"), stateSequence.elementAt(0))
            })
        })
    })
})