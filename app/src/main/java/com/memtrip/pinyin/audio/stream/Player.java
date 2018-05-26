package com.memtrip.pinyin.audio.stream;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.memtrip.pinyin.BuildConfig;

import java.io.IOException;

import static com.google.android.exoplayer2.Player.STATE_BUFFERING;
import static com.google.android.exoplayer2.Player.STATE_READY;

public class Player {

    private final SimpleExoPlayer player;
    private final MediaSource mediaSource;
    private final OnPlayerStateListener onPlayerStateListener;

    public Player(String streamUrl,
                  final OnPlayerStateListener onPlayerStateListener,
                  Context context) {

        this(
                ExoPlayerFactory.newSimpleInstance(
                        new DefaultRenderersFactory(context),
                        new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter())),
                        new DefaultLoadControl()),

                new ExtractorMediaSource(
                        Uri.parse(streamUrl),
                        new DefaultHttpDataSourceFactory(new UserAgent().get(), null),
                        new DefaultExtractorsFactory(),
                        new Handler(Looper.getMainLooper()),
                        new ExtractorMediaSource.EventListener() {
                            @Override
                            public void onLoadError(IOException error) {
                                onPlayerStateListener.onBufferingError(error);
                            }
                        }),
                onPlayerStateListener
        );
    }

    private Player(SimpleExoPlayer player,
                   MediaSource mediaSource,
                   OnPlayerStateListener onPlayerStateListener) {

        this.player = player;
        this.mediaSource = mediaSource;
        this.onPlayerStateListener = onPlayerStateListener;
    }

    void prepare() {

        player.addListener(new com.google.android.exoplayer2.Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                if (isLoading) {
                    onPlayerStateListener.startBuffering();
                }
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (player.getPlaybackState() != STATE_BUFFERING) {
                    if (player.getPlaybackState() == STATE_READY && playWhenReady) {
                        onPlayerStateListener.onPlay();
                    } else {
                        onPlayerStateListener.onCompleted();
                    }
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                onPlayerStateListener.onBufferingError(null);
            }

            @Override
            public void onPositionDiscontinuity() {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }
        });
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
    }

    void release() {
        player.stop();
        player.release();
    }

    void pause() {
        player.setPlayWhenReady(false);
    }

    private static class UserAgent {
        public String get() {
            return BuildConfig.APPLICATION_ID + "/" + BuildConfig.VERSION_NAME;
        }
    }
}
