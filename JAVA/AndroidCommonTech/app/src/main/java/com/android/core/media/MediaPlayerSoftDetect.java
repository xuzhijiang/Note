package com.android.core.media;

import android.content.Context;
import android.media.MediaPlayer;

public class MediaPlayerSoftDetect {

    private Context mPlayerCtx;

    private MediaPlayer mPlayer;

    private long mPlayableReportCycle;

    public static MediaPlayerSoftDetect getSoftDetectInst(MediaPlayer playerOwner, Context ctx){
        MediaPlayerSoftDetect self = new MediaPlayerSoftDetect(playerOwner, ctx, 10000);
        return self;
    }

    private MediaPlayerSoftDetect(MediaPlayer PlayerOwner, Context ctx, long cycle) {
        mPlayer = PlayerOwner;
        mPlayableReportCycle = cycle;
        mPlayerCtx = ctx;
    }

    public long getBufferPlayerbleCycle(){
        return mPlayableReportCycle;
    }
}
