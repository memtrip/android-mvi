package com.memtrip.pinyin

import android.support.annotation.IdRes

interface Event {
    val id: Int
}

class StartEvent(@IdRes override val id: Int = R.id.presenter_start) : Event

class ClickEvent(@IdRes override val id: Int) : Event

class SearchEvent(@IdRes override val id: Int, val terms: String) : Event

interface AdapterEvent<T> : Event {
    override val id: Int
    val value: T?
}

class AdapterClick<T>(
        override val id: Int,
        override val value: T?) : AdapterEvent<T>

class AdapterEnd<T>(
        override val id: Int,
        override val value: T?) : AdapterEvent<T>