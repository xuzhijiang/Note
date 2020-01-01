package com.android.core.media;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.android.core.media.listener.SoftDetectListener;

public class MediaPlayerListenerImpl implements OnBufferingUpdateListener,
        OnPreparedListener, OnInfoListener, OnSeekCompleteListener,
        OnVideoSizeChangedListener, OnCompletionListener, OnErrorListener,
        SoftDetectListener {

    private static final int MEDIA_INFO = 0x100;
    private static final int MEDIA_BUFFER = 0x101;

    // media player event message
    public static final int MEDIA_EVENT_STOP = 10; // 开始播放事件
    public static final int MEDIA_EVENT_PLAYBEGIN = 12; // 开始播放事件

    // 定义播放错误事件
    public static final int MERROR_BASE = 0;
    public static final int MERROR_INVALID_URL = -1 + MERROR_BASE;
    public static final int MERROR_UNKNOWN_PROFILE = -2 + MERROR_BASE; // profile格式不正确
    public static final int MERROR_UNSUPPORT_PROTOCOL = -3 + MERROR_BASE; // 不支持的协议

    private Handler mMediaEventHandler;

    public MediaPlayerListenerImpl() {
        Looper looper = Looper.myLooper();
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        mMediaEventHandler = new SoftDetectorMediaHandler(looper);
    }

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
        switch(what) {
            case MEDIA_EVENT_PLAYBEGIN:
                getSoftDetect(player);
                sendSoftDetectMsg(MediaPlayerSoftDetect.MESSAGE_PLAY_PREPARE, 0, null);
                if(null != mPlayerSoftDetect){
                    mMediaEventHandler.sendEmptyMessageDelayed(MediaPlayerSoftDetect.MEDIA_GET_BUFFER_LENGTH, getBufferPlayerbleCycle());
                }
                break;
            case MEDIA_EVENT_STOP:
                //sendSoftDetectMsg(MediaPlayerSoftDetect.MESSAGE_PLAY_STOP, 0, null);
                mPlayerSoftDetect = null;
                if (mMediaEventHandler != null) {
                    mMediaEventHandler.removeCallbacksAndMessages(null);
                }
                break;
            default:
                break;
        }
    }

    private void sendSoftDetectMsg(int what, int arg0, String msg){
        if(null == mPlayerSoftDetect){
            return;
        }

        mPlayerSoftDetect.sendSoftDetectMsg(what, arg0, msg);
    }

    MediaPlayerSoftDetect mPlayerSoftDetect;

    private void getSoftDetect(MediaPlayer player){
        if(null == mPlayerSoftDetect){
            mPlayerSoftDetect = MediaPlayerSoftDetect.getSoftDetectInstance(player, null);
        }
    }

    private long getBufferPlayerbleCycle(){
        if(null != mPlayerSoftDetect){
            return mPlayerSoftDetect.getBufferPlayerbleCycle();
        }

        return 10000; //by default.
    }

    private class SoftDetectorMediaHandler extends Handler {

        public SoftDetectorMediaHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MediaPlayerSoftDetect.MEDIA_GET_BUFFER_LENGTH:
                    if(null != mPlayerSoftDetect){
                        sendEmptyMessageDelayed(MediaPlayerSoftDetect.MEDIA_GET_BUFFER_LENGTH, getBufferPlayerbleCycle());
                        sendSoftDetectMsg(MediaPlayerSoftDetect.MESSAGE_BUFFER_LENGTH, 0, null);
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }
}
