package com.android.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

public class BootCompletedReceiver extends BroadcastReceiver {

    private final String TAG = BootCompletedReceiver.class.getCanonicalName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "ACTION: " + intent.getAction());
    }
}
