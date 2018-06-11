package com.consistence.pinyin.app.detail

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
class PinyinDetailModelTest: Spek({

    given("PinyinDetailIntent.Init", {

        on("Populate pinyin details without audio", {

            val pinyinParcel: PinyinParcel = mock {

                on { phoneticScriptText }
                        .doReturn("nĭ")

                on { englishTranslationText }
                        .doReturn("you")

                on { chineseCharacters }
                        .doReturn("你")
            }

            val model = PinyinDetailModel(pinyinParcel, mock())
            val stateSequence = model.reducer(PinyinDetailIntent.Init).blockingIterable().asSequence()

            it("should return PinyinDetailState.Populate", {
                Assert.assertEquals(PinyinDetailState.Populate(
                        "nĭ",
                        "you",
                        "你",
                        null
                ), stateSequence.elementAt(0))
            })
        })

        on("Play audio", {

            val pinyinParcel: PinyinParcel = mock {

                on { audioSrc }
                        .doReturn("file://")
            }

            val model = PinyinDetailModel(pinyinParcel, mock())
            val stateSequence = model.reducer(PinyinDetailIntent.PlayAudio).blockingIterable().asSequence()

            it("should return PinyinDetailState.PlayAudio", {
                Assert.assertEquals(PinyinDetailState.PlayAudio("file://"), stateSequence.elementAt(0))
            })
        })
    })
})