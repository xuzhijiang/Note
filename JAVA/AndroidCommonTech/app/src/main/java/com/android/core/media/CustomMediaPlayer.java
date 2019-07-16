package com.android.core.media;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

public class CustomMediaPlayer extends MediaPlayer implements SoftDetectorMediaListener {

    private Handler mMediaEventHandler;

    public CustomMediaPlayer() {
        Looper looper = Looper.myLooper();
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        mMediaEventHandler = new SoftDetectorMediaHandler(looper);
    }

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

    @Override
    public void onMediaEvent(MediaPlayer player) {

    }

}
