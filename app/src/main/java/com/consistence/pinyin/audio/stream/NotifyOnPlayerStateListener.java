package com.consistence.pinyin.audio.stream;

import android.os.Handler;

import java.io.IOException;

class NotifyOnPlayerStateListener implements OnPlayerStateListener {

    private final Notify notify;
    private final Handler mainThreadHandler;

    NotifyOnPlayerStateListener(Notify notify, Handler mainThreadHandler) {
        this.notify = notify;
        this.mainThreadHandler = mainThreadHandler;
    }

    @Override
    public void startBuffering() {
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                notify.startBuffering();
            }
        });
    }

    @Override
    public void onBufferingError(IOException e) {
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                notify.bufferingError();
            }
        });
    }

    @Override
    public void onPlay() {
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                notify.play();
            }
        });
    }

    @Override
    public void onCompleted() {
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                notify.completed();
            }
        });
    }
}