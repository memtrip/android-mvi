package com.consistence.pinyin.audio.stream

interface OnPlayerStateListener {
    fun startBuffering()
    fun onBufferingError()
    fun onPlay()
    fun onCompleted()
}