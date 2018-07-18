package com.consistence.pinyin

import java.util.Random

interface MxViewState {

    companion object {

        private val random by lazy {
            Random()
        }

        internal fun id(): Int = random.nextInt(99999 - 0) + 0
    }
}