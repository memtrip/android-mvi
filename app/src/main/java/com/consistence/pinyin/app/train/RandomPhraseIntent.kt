package com.consistence.pinyin.app.train

import com.memtrip.mxandroid.MxViewIntent

sealed class RandomPhraseIntent : MxViewIntent {
    object Init : RandomPhraseIntent()
    data class Result(val correct: Boolean) : RandomPhraseIntent()
}