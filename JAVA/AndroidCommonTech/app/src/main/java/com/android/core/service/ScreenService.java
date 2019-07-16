package com.android.core.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.android.core.aidl.screencap.IScreencapListener;
import com.android.core.aidl.screencap.IScreencapService;

public class ScreenService extends Service {

    private final String tag = ScreenService.class.getCanonicalName();

    @Override
    public IBinder onBind(Intent intent) {
        return new ScreencapServiceImpl();
    }

    private class ScreencapServiceImpl extends IScreencapService.Stub {

        @Override
        public int doScreencap(String filename) throws RemoteException {
            Log.d(tag, "do screen cap filename: " + filename);
            // 传入ScreencapListener回调listener，然后执行功能,执行完之后回调listener
            // RootManager.cap(filename, new ScreencapListenerImpl());
            return 100;
        }
    }

    private class ScreencapListenerImpl extends IScreencapListener.Stub {

        @Override
        public int onResult(int code) throws RemoteException {
            Log.d(tag, "code : " + code);
            return 0;
        }
    }
}
