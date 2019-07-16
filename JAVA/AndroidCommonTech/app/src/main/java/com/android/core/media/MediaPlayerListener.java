package com.android.core.media;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;

public class MediaPlayerListener implements OnBufferingUpdateListener,
        OnPreparedListener, OnInfoListener, OnSeekCompleteListener,
        OnVideoSizeChangedListener, OnCompletionListener, OnErrorListener,
        SoftDetectorMediaListener{

    public static final int MEDIA_EVENT_PLAYBEGIN = 12; // 开始播放事件
    public static final int MEDIA_EVENT_PLAYEND = 13; // 播放结束事件
    public static final int MEDIA_EVENT_STREAM2BEGIN = 14; // 流回到开始位置
    public static final int MEDIA_EVENT_STREAM2END = 15; // 流结束事件
    public static final int MEDIA_EVENT_PLAY2BEGIN = 16; // 播放到开始
    public static final int MEDIA_EVENT_PLAY2END = 17; // 播放到结束
    public static final int MEDIA_EVENT_BUFFERING_START = 18;

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        onMediaEvent(mp, what);
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

    }

    @Override
    public void onMediaEvent(MediaPlayer player, int what) {
//        switch(what) {
//            case MEDIA_EVENT_PLAYBEGIN:
//                getSoftDetect(player);
//                sendSoftDetectMsg(MediaPlayerSoftDetect.MESSAGE_PLAY_PREPARE, 0, null);
//                if(null != mPlayerSoftDetect){
//                    mMediaHandler.sendEmptyMessageDelayed(MediaPlayerSoftDetect.MEDIA_GET_BUFFER_LENGTH, getBufferPlayerbleCycle());
//                }
//                //sendSoftDetectMsg(MediaPlayerSoftDetect.MESSAGE_PLAY_PREPARE_COMPLETED, 0, null);
//                //sendSoftDetectMsg(MediaPlayerSoftDetect.MESSAGE_PLAY_START_UP, 0, null);
//                break;
//            case SWIPTVPlayer.MEDIA_EVENT_STOP:
//                sendSoftDetectMsg(MediaPlayerSoftDetect.MESSAGE_PLAY_START, 0, null);
//                sendSoftDetectMsg(MediaPlayerSoftDetect.MESSAGE_PLAY_RESUME, 0, null);
//                sendSoftDetectMsg(MediaPlayerSoftDetect.MESSAGE_SEEK_END, 0, null);
//                sendSoftDetectMsg(MediaPlayerSoftDetect.MESSAGE_BUFFER_END, 0, null);
//                sendSoftDetectMsg(MediaPlayerSoftDetect.MESSAGE_UNLOAD_END, 0, extra);
//                sendSoftDetectMsg(MediaPlayerSoftDetect.MESSAGE_BLURREDSCREEN_END, 0, extra);
//                sendSoftDetectMsg(MediaPlayerSoftDetect.MESSAGE_PLAY_STOP, 0, null);
//                mPlayerSoftDetect = null;
//                if (mMediaHandler != null) {
//                    mMediaHandler.removeCallbacksAndMessages(null);
//                }
//                break;
//            default:
//                break;
//        }
    }

    MediaPlayerSoftDetect mPlayerSoftDetect;

    private void getSoftDetect(MediaPlayer player){
        if(null == mPlayerSoftDetect){
            //mPlayerSoftDetect = MediaPlayerSoftDetect.getSoftDetectInst(player);
        }
    }
}
