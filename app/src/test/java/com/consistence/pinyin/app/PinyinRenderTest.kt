package com.consistence.pinyin.app

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class PinyinRenderTest: Spek({

    given("PinyinRender", {

        on("PinyinState.SearchHint", {
            val layout: PinyinLayout = mock()
            val render = PinyinRender()

            render.state(layout, PinyinState.SearchHint("hello"))

            it("shows search hint", {
                verify(layout).updateSearchHint("hello")
            })
        })
    })
})