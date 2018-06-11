package com.consistence.pinyin.audio.stream

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import com.consistence.pinyin.BuildConfig
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.STATE_BUFFERING
import com.google.android.exoplayer2.Player.STATE_READY
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

class Player constructor(
        streamUrl: String,
        private val onPlayerStateListener: OnPlayerStateListener,
        context: Context,
        private val player: SimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                DefaultRenderersFactory(context),
                DefaultTrackSelector(AdaptiveTrackSelection.Factory(DefaultBandwidthMeter())),
                DefaultLoadControl()),
        private val mediaSource: MediaSource = ExtractorMediaSource(
                Uri.parse(streamUrl),
                DefaultHttpDataSourceFactory(
                        BuildConfig.APPLICATION_ID + "/" + BuildConfig.VERSION_NAME, null),
                DefaultExtractorsFactory(),
                Handler(Looper.getMainLooper()),
                ExtractorMediaSource.EventListener { onPlayerStateListener.onBufferingError() })) {

    internal fun prepare() {

        player.addListener(object : EventListenerAdapter {

            override fun onLoadingChanged(isLoading: Boolean) {
                if (isLoading) {
                    onPlayerStateListener.startBuffering()
                }
            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (player.playbackState != STATE_BUFFERING) {
                    if (player.playbackState == STATE_READY && playWhenReady) {
                        onPlayerStateListener.onPlay()
                    } else {
                        onPlayerStateListener.onCompleted()
                    }
                }
            }

            override fun onPlayerError(error: ExoPlaybackException?) {
                onPlayerStateListener.onBufferingError()
            }
        })

        player.prepare(mediaSource)
        player.playWhenReady = true
    }

    internal fun release() {
        player.stop()
        player.release()
    }

    internal fun pause() {
        player.playWhenReady = false
    }
}

interface EventListenerAdapter :  Player.EventListener {
    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?) { }
    override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) { }
    override fun onLoadingChanged(isLoading: Boolean) { }
    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) { }
    override fun onRepeatModeChanged(repeatMode: Int) { }
    override fun onPlayerError(error: ExoPlaybackException?) { }
    override fun onPositionDiscontinuity() { }
    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) { }
}