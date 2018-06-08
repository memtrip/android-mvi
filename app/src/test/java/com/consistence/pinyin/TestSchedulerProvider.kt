package com.consistence.pinyin

import com.consistence.pinyin.api.SchedulerProvider

import io.reactivex.schedulers.Schedulers

class TestSchedulerProvider : SchedulerProvider {
    override fun main() = Schedulers.trampoline()
    override fun thread() = Schedulers.trampoline()
}