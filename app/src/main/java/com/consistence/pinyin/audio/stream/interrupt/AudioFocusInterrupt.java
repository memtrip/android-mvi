package com.consistence.pinyin.audio.stream.interrupt;

import android.content.Context;
import android.media.AudioManager;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;

public class AudioFocusInterrupt implements AudioManager.OnAudioFocusChangeListener {

    private final InterruptAudio interruptAudio;
    private final AudioManager audioManager;

    public AudioFocusInterrupt(InterruptAudio interruptAudio, Context context) {
        this(interruptAudio, (AudioManager)context.getSystemService(Context.AUDIO_SERVICE));
    }

    private AudioFocusInterrupt(InterruptAudio interruptAudio, AudioManager audioManager) {
        this.interruptAudio = interruptAudio;
        this.audioManager = audioManager;
    }

    public void attach() {
        audioManager.requestAudioFocus(this,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);
    }

    public void remove() {
        audioManager.abandonAudioFocus(this);
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        if (focusChange == AudioManager.AUDIOFOCUS_LOSS || focusChange == AUDIOFOCUS_LOSS_TRANSIENT) {
            interruptAudio.interrupt();
        }
    }
}