package com.consistence.pinyin.audio.stream;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import com.consistence.pinyin.audio.stream.interrupt.AudioFocusInterrupt;
import com.consistence.pinyin.audio.stream.interrupt.BecomingNoisyInterrupt;
import com.consistence.pinyin.audio.stream.interrupt.InterruptAudio;

import timber.log.Timber;

public abstract class StreamingService<T extends Stream> extends Service {

    private Player player;
    private BecomingNoisyInterrupt becomingNoisyInterrupt;
    private AudioFocusInterrupt audioFocusInterrupt;
    private boolean intentReceiverAttached;

    protected abstract StreamIntent<T> streamIntent();

    protected abstract Player createPlayer(String audioStreamUrl,
                                           OnPlayerStateListener onPlayerStateListener,
                                           Context context);

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {

            Timber.d(">> Streaming service onStartCommand: " + this + "" +
                    "\n data: " + intent);

            T stream = streamIntent().get(intent);

            setupInterruptReceiver();

            start(stream, new Notify(stream.streamUrl(), this));
        }

        return START_NOT_STICKY;
    }

    private void setupInterruptReceiver() {

        if (!intentReceiverAttached) {

            intentReceiverAttached = true;

            becomingNoisyInterrupt = new BecomingNoisyInterrupt(new InterruptAudio() {
                @Override
                public void interrupt() {
                    player.pause();
                }
            }, getApplication());

            becomingNoisyInterrupt.register();

            audioFocusInterrupt = new AudioFocusInterrupt(new InterruptAudio() {
                @Override
                public void interrupt() {
                    player.pause();
                }
            }, getApplication());

            audioFocusInterrupt.attach();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void start(T stream, Notify notify) {

        Timber.d(">> Start with stream: %s", stream);

        player = createPlayer(
                stream.streamUrl(),
                new NotifyOnPlayerStateListener(notify, new Handler(Looper.getMainLooper())),
                this);

        player.prepare();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (becomingNoisyInterrupt != null) {
            becomingNoisyInterrupt.unregister();
        }

        if (audioFocusInterrupt != null) {
            audioFocusInterrupt.remove();
        }

        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

        if (player != null) {
            player.release();
            player = null;
        }

        stopSelf();
    }
}