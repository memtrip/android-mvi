package com.consistence.pinyin

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.squareup.leakcanary.LeakCanary
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class PinyinApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun activityInjector()= activityInjector

    override fun supportFragmentInjector() = fragmentInjector

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) { return }

        LeakCanary.install(this)

        DaggerPinyinApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
    }
}