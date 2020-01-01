package com.android.core.media.listener;

import android.media.MediaPlayer;

public interface SoftDetectListener {
    void onMediaEvent(MediaPlayer player, int what);
}
