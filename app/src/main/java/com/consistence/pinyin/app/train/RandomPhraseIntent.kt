package com.consistence.pinyin.app.train

import com.memtrip.mxandroid.MxViewIntent

sealed class RandomPhraseIntent : MxViewIntent {
    object Idle : RandomPhraseIntent()
    object Init : RandomPhraseIntent()
    object Start : RandomPhraseIntent()
    data class Result(val correct: Boolean) : RandomPhraseIntent()
}