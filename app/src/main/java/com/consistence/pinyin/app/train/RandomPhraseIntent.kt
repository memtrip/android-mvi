package com.consistence.pinyin.app.train

import com.memtrip.mxandroid.MxViewIntent

sealed class RandomPhraseIntent : MxViewIntent {
    object Idle : RandomPhraseIntent()
    object Init : RandomPhraseIntent()
    data class Start(val limit: Int) : RandomPhraseIntent()
    data class Result(val correct: Boolean) : RandomPhraseIntent()
}