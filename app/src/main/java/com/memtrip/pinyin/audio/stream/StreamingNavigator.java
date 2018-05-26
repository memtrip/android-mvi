package com.memtrip.pinyin.audio.stream;

import android.content.Context;
import android.content.Intent;

import static com.memtrip.pinyin.audio.stream.StreamingAction.ACTION_NOTIFICATION_PLAY;

public abstract class StreamingNavigator<T extends Stream> implements Navigator<T> {

    private final StreamIntent<T> streamIntent;
    private final Class<? extends StreamingService> classDef;

    public StreamingNavigator(StreamIntent<T> streamIntent,
                              Class<? extends StreamingService> classDef) {

        this.streamIntent = streamIntent;
        this.classDef = classDef;
    }

    @Override
    public void play(T data, Context context) {
        Intent intent = new Intent(context, classDef);
        intent.setAction(ACTION_NOTIFICATION_PLAY.toString());

        streamIntent.into(data, intent);

        context.startService(intent);
    }
}