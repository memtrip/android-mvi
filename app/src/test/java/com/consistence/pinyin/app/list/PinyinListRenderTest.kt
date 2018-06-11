package com.consistence.pinyin.app.list

import com.consistence.pinyin.api.PinyinEntity
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.Arrays.asList

@RunWith(JUnitPlatform::class)
class PinyinListRenderTest: Spek({

    given("PinyinListRender", {

        on("populate", {

            val layout: PinyinListLayout = mock()
            val render = PinyinListRender(layout)

            val pinyinList: List<PinyinEntity> = asList(mock())

            render.state(PinyinListState.Populate(pinyinList))

            it("populates the pinyin list items", {
                verify(layout).populate(pinyinList)
            })
        })

        on("navigateToPinyinDetails", {

            val layout: PinyinListLayout = mock()
            val render = PinyinListRender(layout)

            val pinyinItem: PinyinEntity = mock()

            render.state(PinyinListState.NavigateToDetails(pinyinItem))

            it("navigates to pinyin details with the selected pinyin item", {
                verify(layout).navigateToPinyinDetails(pinyinItem)
            })
        })

        on("error", {

            val layout: PinyinListLayout = mock()
            val render = PinyinListRender(layout)

            render.state(PinyinListState.OnError)

            it("displays the error", {
                verify(layout).error()
            })
        })

        on("playAudio", {

            val layout: PinyinListLayout = mock()
            val render = PinyinListRender(layout)

            render.state(PinyinListState.PlayAudio("file://audio"))

            it("plays the audio src", {
                verify(layout).playAudio("file://audio")
            })
        })
    })
})