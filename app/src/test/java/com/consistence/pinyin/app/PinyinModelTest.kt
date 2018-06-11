package com.consistence.pinyin.app

import android.app.Application
import com.consistence.pinyin.R
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class PinyinModelTest: Spek({

    given("PinyinIntent.TabSelected", {

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

        on("Phonetic tab selected", {
            val model = PinyinModel(context)
            val stateSequence = model.reducer(PinyinIntent.TabSelected(Page.PHONETIC)).blockingIterable().asSequence()

            it("should return PinyinState.SearchHint with phonetic hint", {
                Assert.assertEquals(PinyinState.SearchHint("phonetic hint"), stateSequence.elementAt(0))
            })
        })

        on("English tab selected", {
            val model = PinyinModel(context)
            val stateSequence = model.reducer(PinyinIntent.TabSelected(Page.ENGLISH)).blockingIterable().asSequence()

            it("should return PinyinState.SearchHint with english hint", {
                Assert.assertEquals(PinyinState.SearchHint("english hint"), stateSequence.elementAt(0))
            })
        })

        on("Character tab selected", {
            val model = PinyinModel(context)
            val stateSequence = model.reducer(PinyinIntent.TabSelected(Page.CHARACTER)).blockingIterable().asSequence()

            it("should return PinyinState.SearchHint with character hint", {
                Assert.assertEquals(PinyinState.SearchHint("character hint"), stateSequence.elementAt(0))
            })
        })
    })
})