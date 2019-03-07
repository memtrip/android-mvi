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
class EntryRenderTest : Spek({

    given("EntryRenderer") {

        on("EntryRenderAction.OnProgress") {
            val layout: StudyLayout = mock()
            val render = StudyRenderer()

            render.layout(layout, StudyViewState(view = StudyViewState.View.OnProgress))

            it("shows progress indicator") {
                verify(layout).showProgress()
            }
        }

        on("EntryRenderAction.OnError") {
            val layout: StudyLayout = mock()
            val render = StudyRenderer()

            render.layout(layout, StudyViewState(view = StudyViewState.View.OnError))

            it("hides layout indicator and shows the showError") {
                verify(layout).showError()
            }
        }

        on("EntryRenderAction.OnPinyinLoaded") {
            val layout: StudyLayout = mock()
            val render = StudyRenderer()

            render.layout(layout, StudyViewState(view = StudyViewState.View.OnPinyinLoaded))

            it("hides layout indicator and navigate to pinyin list") {
                verify(layout).navigateToPinyin()
            }
        }
    }
})