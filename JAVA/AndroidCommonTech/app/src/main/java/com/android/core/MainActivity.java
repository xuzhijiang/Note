package com.android.core;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.android.core.receiver.NetworkStateChangeReceiver;

public class MainActivity extends Activity {

    private NetworkStateChangeReceiver mNetworkStateChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVariables();
    }

    private void initVariables() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        // intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE_IMMEDIATE");
        mNetworkStateChangeReceiver = new NetworkStateChangeReceiver();
        registerReceiver(mNetworkStateChangeReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        unregisterReceiver(mNetworkStateChangeReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
