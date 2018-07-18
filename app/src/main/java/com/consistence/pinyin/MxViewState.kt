package com.consistence.pinyin

import java.util.Random

interface MxViewState {

    companion object {
        internal fun id(): Int = Random().nextInt(99999 - 0) + 0
    }
}