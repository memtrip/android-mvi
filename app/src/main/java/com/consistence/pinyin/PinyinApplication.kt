package com.consistence.pinyin

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

class PinyinApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        Timber.plant(Timber.DebugTree())
    }
}