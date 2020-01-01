package com.android.core.media;

import android.media.MediaPlayer;

import java.io.IOException;

public class CustomMediaPlayer extends MediaPlayer {

    @Override
    public void prepare() throws IOException, IllegalStateException {
        super.prepare();
    }

    @Override
    public void setDataSource(String path) throws IOException, IllegalArgumentException, IllegalStateException, SecurityException {
        super.setDataSource(path);
    }

    @Override
    public void start() throws IllegalStateException {
        super.start();
    }

    @Override
    public void seekTo(int msec) throws IllegalStateException {
        super.seekTo(msec);
    }

    @Override
    public void pause() throws IllegalStateException {
        super.pause();
    }

    @Override
    public void stop() throws IllegalStateException {
        super.stop();
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public void release() {
        super.release();
    }

}
