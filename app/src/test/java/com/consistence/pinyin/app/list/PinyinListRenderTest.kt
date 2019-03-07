package com.consistence.pinyin.app.list

import com.consistence.pinyin.domain.pinyin.db.PinyinEntity
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
class PinyinListRenderTest : Spek({

    given("PinyinListRenderer") {

        on("populate") {

            val layout: PinyinListLayout = mock()
            val render = PinyinListRenderer()

            val pinyinList: List<PinyinEntity> = asList(mock())

            render.layout(layout, PinyinListViewState(view = PinyinListViewState.View.Populate(pinyinList)))

            it("populates the pinyin list items") {
                verify(layout).populate(pinyinList)
            }
        }

        on("navigateToPinyinDetails") {

            val layout: PinyinListLayout = mock()
            val render = PinyinListRenderer()

            val pinyinItem: PinyinEntity = mock()

            render.layout(layout, PinyinListViewState(view = PinyinListViewState.View.SelectItem(pinyinItem)))

            it("navigates to pinyin details with the selected pinyin item") {
                verify(layout).navigateToPinyinDetails(pinyinItem)
            }
        }

        on("showError") {

            val layout: PinyinListLayout = mock()
            val render = PinyinListRenderer()

            render.layout(layout, PinyinListViewState(view = PinyinListViewState.View.OnError))

            it("displays the showError") {
                verify(layout).showError()
            }
        }

        on("playAudio") {

            val layout: PinyinListLayout = mock()
            val render = PinyinListRenderer()

            render.layout(layout, PinyinListViewState(view = PinyinListViewState.View.PlayAudio("file://audio")))

            it("plays the audio src") {
                verify(layout).playAudio("file://audio")
            }
        }
    }
})