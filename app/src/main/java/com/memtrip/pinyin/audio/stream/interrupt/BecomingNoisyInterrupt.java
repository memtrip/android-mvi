package com.memtrip.pinyin.audio.stream.interrupt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;

public class BecomingNoisyInterrupt extends BroadcastReceiver {

    private final IntentFilter intentFilter;
    private final InterruptAudio interruptAudio;
    private final Context context;

    public BecomingNoisyInterrupt(InterruptAudio interruptAudio,
                                  Context context) {

        this(new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY), interruptAudio, context);
    }

    private BecomingNoisyInterrupt(IntentFilter intentFilter,
                                   InterruptAudio interruptAudio,
                                   Context context) {

        this.intentFilter = intentFilter;
        this.interruptAudio = interruptAudio;
        this.context = context;
    }

    public void register() {
        context.registerReceiver(this, intentFilter);
    }

    public void unregister() {
        context.registerReceiver(this, intentFilter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
            interruptAudio.interrupt();
        }
    }
}