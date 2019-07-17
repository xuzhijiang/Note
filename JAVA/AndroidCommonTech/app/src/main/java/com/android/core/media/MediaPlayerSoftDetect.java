package com.android.core.media;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

import java.text.SimpleDateFormat;

public class MediaPlayerSoftDetect {

    private final static String TAG = MediaPlayerSoftDetect.class.getCanonicalName();

    //intent message
    private static final String MEDIA_INFO_EXTEND_STRING_MESSAGE_TYPE = "TYPE";
    private static final String MEDIA_INFO_EXTEND_STRING_PLAY_PREPARE = "PLAY_PREPARE";
    private static final String MEDIA_INFO_EXTEND_STRING_ID = "ID";//int
    private static final String MEDIA_INFO_EXTEND_STRING_URL = "URL";//string
    private static final String MEDIA_INFO_EXTEND_STRING_START_TIME = "START_TIME";//long
    private static final String MEDIA_INFO_EXTEND_STRING_PLAY_TIME = "PLAY_TIME";//int
    private static final String MEDIA_INFO_EXTEND_STRING_TIME = "TIME";//long
    private static final String MEDIA_INFO_EXTEND_STRING_BUFFER_LENGTH = "SECONDS";//long

    //soft Detect message type
    public static final int MESSAGE_PLAY_PREPARE = 3001;
    public static final int MESSAGE_BUFFER_LENGTH = 3009;

    //MediaPlayer message
    public static final int MEDIA_GET_BUFFER_LENGTH = 300;

    private Context mPlayerCtx;
    private MediaPlayer mPlayer;
    private long mPlayableReportCycle;
    private long mPlayabeReportTime = 0;
    private static int mConnectId = 0;//session id.
    private String mMediaUrl = "";
    private long mBufferInfoSeconds;

    private boolean mbStart;


    public static MediaPlayerSoftDetect getSoftDetectInstance(MediaPlayer playerOwner, Context ctx){
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

    public void sendSoftDetectMsg(int what, int arg0, String extra){
        boolean bSend = true;
        Intent intent = new Intent("MEDIA_PLAY_MONITOR_MESSAGE");
        switch(what){
            case MESSAGE_PLAY_PREPARE:{
                packMsgPlayPrepare(intent, arg0, extra);
            }break;
            case MESSAGE_BUFFER_LENGTH:{
                packMsgBufferLen(intent, arg0, extra);
            }break;
            default:
                bSend = false;
                Log.w(TAG,"error, unkown message index:"+what);
                break;
        }
        if (bSend) {
            mPlayerCtx.sendBroadcast(intent);
        }
    }

    private void packMsgPlayPrepare(Intent intent, int arg0, String extra){
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        long currenttime = System.currentTimeMillis();

        getConnectID();
        getMediaURL();

        intent.putExtra(MEDIA_INFO_EXTEND_STRING_ID, mConnectId);
        intent.putExtra(MEDIA_INFO_EXTEND_STRING_URL, mMediaUrl);
        intent.putExtra(MEDIA_INFO_EXTEND_STRING_START_TIME, currenttime);

        Log.d(TAG,"broadcast "+MEDIA_INFO_EXTEND_STRING_MESSAGE_TYPE + ": " +MEDIA_INFO_EXTEND_STRING_PLAY_PREPARE);
        Log.d(TAG,"--------- "+MEDIA_INFO_EXTEND_STRING_ID + ": " +mConnectId);
        Log.d(TAG,"--------- "+MEDIA_INFO_EXTEND_STRING_URL + ": " +mMediaUrl);
        Log.d(TAG,"--------- "+MEDIA_INFO_EXTEND_STRING_START_TIME + ": " +formater.format(currenttime));
    }

    private boolean packMsgBufferLen(Intent intent, int arg0, String extra){
        boolean bret = true;

        mPlayabeReportTime += mPlayableReportCycle;
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

        if (mbStart) {
            int position = getCurrentPosition();
            intent.putExtra(MEDIA_INFO_EXTEND_STRING_ID, mConnectId);
            intent.putExtra(MEDIA_INFO_EXTEND_STRING_TIME, mPlayabeReportTime);

            Log.d(TAG, "--------- "+MEDIA_INFO_EXTEND_STRING_BUFFER_LENGTH
                    + " " +(int)(mBufferInfoSeconds/3600)
                    + ":" +(int)((mBufferInfoSeconds%3600)/60)
                    + ":" +(int)(mBufferInfoSeconds%60));
            Log.d(TAG, "--------- "+MEDIA_INFO_EXTEND_STRING_PLAY_TIME
                    +" "+(position/3600)
                    +":"+((position%3600)/60)
                    +":"+(position%60));
            Log.d(TAG, "--------- "+MEDIA_INFO_EXTEND_STRING_TIME + ": " +formater.format(mPlayabeReportTime));
        } else {
            bret = false;
        }

        return bret;
    }

    private void getConnectID(){
        mConnectId = -1;
    }

    private void getMediaURL(){
        mMediaUrl = "";
    }

    private int getCurrentPosition() {
        return -1;
    }
}
