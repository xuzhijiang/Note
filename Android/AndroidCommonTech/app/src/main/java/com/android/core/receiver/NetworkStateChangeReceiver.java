package com.android.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.core.util.NetworkUtils;

public class NetworkStateChangeReceiver extends BroadcastReceiver {

    private final String TAG = NetworkStateChangeReceiver.class.getCanonicalName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "ACTION: " + intent.getAction());

        String status = NetworkUtils.getConnectivityStatusString(context);

        Log.d(TAG, "NETWORK STATUS: " + status);
        Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }
}
