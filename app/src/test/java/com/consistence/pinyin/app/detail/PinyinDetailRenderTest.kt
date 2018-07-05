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
class PinyinDetailRenderTest : Spek({

    given("PinyinDetailRenderer") {

        on("PinyinDetailRenderAction.Populate without audio") {
            val layout: PinyinDetailLayout = mock()
            val render = PinyinDetailRenderer()

            render.layout(layout, PinyinDetailViewState(
                    "nĭ",
                    "you",
                    "你"))

            it("populates the details, but does not show audio controls") {
                verify(layout, never()).showAudioControl()
                verify(layout).populate(
                        "nĭ",
                        "you",
                        "你")
            }
        }

        on("PinyinDetailRenderAction.Populate with audio") {
            val layout: PinyinDetailLayout = mock()
            val render = PinyinDetailRenderer()

            render.layout(layout, PinyinDetailViewState(
                    "nĭ",
                    "you",
                    "你",
                    "file://audio"))

            it("populates the details and show audio controls") {
                verify(layout).showAudioControl()
                verify(layout).populate(
                        "nĭ",
                        "you",
                        "你")
            }
        }

        on("PinyinDetailRenderAction.PlayAudio") {
            val layout: PinyinDetailLayout = mock()
            val render = PinyinDetailRenderer()

            render.layout(layout, PinyinDetailViewState(
                    "nĭ",
                    "you",
                    "你",
                    "file://audio",
                    action = PinyinDetailViewState.Action.PlayAudio("file://audio")))

            it("populates the details and show audio controls") {
                verify(layout).playAudio("file://audio")
            }
        }
    }
})