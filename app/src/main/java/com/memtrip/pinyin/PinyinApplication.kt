package com.memtrip.pinyin

import android.app.Application
import timber.log.Timber

class PinyinApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}