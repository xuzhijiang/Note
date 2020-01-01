package com.android.core.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.android.core.R;
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

    /**
     * 动态注册BroadcastReceiver
     */
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        // intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE_IMMEDIATE");
        mNetworkStateChangeReceiver = new NetworkStateChangeReceiver();
        registerReceiver(mNetworkStateChangeReceiver, intentFilter);
    }

    /**
     * 动态注销BroadcastReceiver
     */
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
