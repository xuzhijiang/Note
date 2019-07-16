package com.android.core.media;

import android.media.MediaPlayer;

public interface SoftDetectorMediaListener {
    void onMediaEvent(MediaPlayer player, int what);
}
