package com.android.core.media;

public class MediaPlayerHolder {

    private CustomMediaPlayer player;

    private MediaPlayerListener listener;

    public MediaPlayerHolder() {
        player = new CustomMediaPlayer();
        listener = new MediaPlayerListener();

        player.setOnBufferingUpdateListener(listener);
        player.setOnCompletionListener(listener);
        player.setOnPreparedListener(listener);
        player.setOnVideoSizeChangedListener(listener);
        player.setOnSeekCompleteListener(listener);
        player.setOnErrorListener(listener);
        player.setOnInfoListener(listener);
    }
}
