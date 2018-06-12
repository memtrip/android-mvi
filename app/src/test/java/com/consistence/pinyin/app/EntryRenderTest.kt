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
class EntryRenderTest: Spek({

    given("EntryRender", {

        on("EntryState.OnProgress", {
            val layout: EntryLayout = mock()
            val render = EntryRender()

            render.state(layout, EntryState.OnProgress)

            it("shows progress indicator", {
                verify(layout).showProgress()
            })
        })

        on("EntryState.OnError", {
            val layout: EntryLayout = mock()
            val render = EntryRender()

            render.state(layout, EntryState.OnError)

            it("hides layout indicator and shows the error", {
                verify(layout).hideProgress()
                verify(layout).error()
            })
        })

        on("EntryState.OnPinyinLoaded", {
            val layout: EntryLayout = mock()
            val render = EntryRender()

            render.state(layout, EntryState.OnPinyinLoaded)

            it("hides layout indicator and navigate to pinyin list", {
                verify(layout).hideProgress()
                verify(layout).navigateToPinyin()
            })
        })
    })
})