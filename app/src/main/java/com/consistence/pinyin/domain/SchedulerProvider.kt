package com.consistence.pinyin.domain

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun main(): Scheduler
    fun thread(): Scheduler
}