package com.memtrip.pinyin

import android.support.annotation.IdRes

interface Event {
    val id: Int
}

class StartEvent(@IdRes override val id: Int = R.id.presenter_start) : Event

class ClickEvent(@IdRes override val id: Int) : Event