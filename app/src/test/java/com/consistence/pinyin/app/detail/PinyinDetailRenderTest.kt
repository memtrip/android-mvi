package com.consistence.pinyin.app.detail

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class PinyinDetailRenderTest: Spek({

    given("PinyinDetailRender", {

        on("PinyinDetailState.Populate without audio", {
            val layout: PinyinDetailLayout = mock()
            val render = PinyinDetailRender(layout)

            render.state(PinyinDetailState.Populate(
                    "nĭ",
                    "you",
                    "你",
                    null))

            it("populates the details, but does not show audio controls", {
                verify(layout, never()).showAudioControl()
                verify(layout).populate(
                        "nĭ",
                        "you",
                        "你")
            })
        })

        on("PinyinDetailState.Populate with audio", {
            val layout: PinyinDetailLayout = mock()
            val render = PinyinDetailRender(layout)

            render.state(PinyinDetailState.Populate(
                    "nĭ",
                    "you",
                    "你",
                    "file://audio"))

            it("populates the details and show audio controls", {
                verify(layout).showAudioControl()
                verify(layout).populate(
                        "nĭ",
                        "you",
                        "你")
            })
        })

        on("PinyinDetailState.PlayAudio", {
            val layout: PinyinDetailLayout = mock()
            val render = PinyinDetailRender(layout)

            render.state(PinyinDetailState.PlayAudio("file://audio"))

            it("populates the details and show audio controls", {
                verify(layout).playAudio("file://audio")
            })
        })
    })
})