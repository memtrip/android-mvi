package com.consistence.pinyin.app

import com.consistence.pinyin.app.pinyin.PinyinLayout
import com.consistence.pinyin.app.pinyin.PinyinRenderer
import com.consistence.pinyin.app.pinyin.PinyinViewState
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class PinyinRenderTest : Spek({

    given("PinyinRenderer") {

        on("PinyinRenderAction.SearchHint") {
            val layout: PinyinLayout = mock()
            val render = PinyinRenderer()

            render.layout(layout, PinyinViewState("hello"))

            it("shows search hint") {
                verify(layout).updateSearchHint("hello")
            }
        }
    }
})