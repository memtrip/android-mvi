package com.consistence.pinyin

import com.consistence.pinyin.domain.SchedulerProvider
import io.reactivex.observers.TestObserver

import io.reactivex.schedulers.Schedulers

class TestSchedulerProvider : SchedulerProvider {
    override fun main() = Schedulers.trampoline()
    override fun thread() = Schedulers.trampoline()
}

fun <T> TestObserver<T>.get(index: Int): T = this.values()[index]

fun <T> TestObserver<T>.first(): T = this.values()[0]