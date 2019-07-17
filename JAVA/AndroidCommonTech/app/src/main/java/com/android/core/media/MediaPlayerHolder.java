package com.android.core.media;

public class MediaPlayerHolder {

    private CustomMediaPlayer player;

    private MediaPlayerListenerImpl listener;

    public MediaPlayerHolder() {
        player = new CustomMediaPlayer();
        listener = new MediaPlayerListenerImpl();

        player.setOnBufferingUpdateListener(listener);
        player.setOnCompletionListener(listener);
        player.setOnPreparedListener(listener);
        player.setOnVideoSizeChangedListener(listener);
        player.setOnSeekCompleteListener(listener);
        player.setOnErrorListener(listener);
        player.setOnInfoListener(listener);
    }
}
