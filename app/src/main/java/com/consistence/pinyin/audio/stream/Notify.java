package com.consistence.pinyin.audio.stream;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import timber.log.Timber;

public class Notify {

    private static final String ACTION_STREAM_NOTIFY = "ACTION_STREAM_NOTIFY";

    private static final String EXTRA_NOTIFY_TYPE = "EXTRA_NOTIFY_TYPE";

    private final String url;
    private final Context context;

    public enum NotifyType {
        PLAYING,
        COMPLETED
    }

    public static IntentFilter getIntentFilter() {
        return new IntentFilter(Notify.ACTION_STREAM_NOTIFY);
    }

    public static NotifyType getNotifyType(Intent intent) {
        return (NotifyType)intent.getSerializableExtra(EXTRA_NOTIFY_TYPE);
    }

    Notify(String url, Context context) {
        this.url = url;
        this.context = context;
    }

    void startBuffering() {

        Timber.v("<<>> Notify: startBuffering");

        Intent intent = notifyIntent();
        intent.putExtra(EXTRA_NOTIFY_TYPE, NotifyType.PLAYING);

        sendBroadcast(intent);
    }

    void bufferingError() {

        Timber.v("<<>> Notify: bufferingError");

        Intent intent = notifyIntent();
        intent.putExtra(EXTRA_NOTIFY_TYPE, NotifyType.COMPLETED);

        sendBroadcast(intent);
    }

    void play() {

        Timber.v("<<>> Notify: play");

        Intent intent = notifyIntent();
        intent.putExtra(EXTRA_NOTIFY_TYPE, NotifyType.PLAYING);
        sendBroadcast(intent);
    }

    void completed() {

        Timber.v("<<>> Notify: completed");

        Intent intent = notifyIntent();
        intent.putExtra(EXTRA_NOTIFY_TYPE, NotifyType.COMPLETED);
        sendBroadcast(intent);
    }

    private void sendBroadcast(Intent intent) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private Intent notifyIntent() {
        Intent intent = new Intent();
        intent.setAction(ACTION_STREAM_NOTIFY);
        return intent;
    }
}