package com.android.core.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.InetAddress;

public class NetworkUtils {

    /**
     * 判断网络是否连接
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if (!AppUtils.checkNull(activeNetworkInfo) && activeNetworkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_ETHERNET = 3;
    public static int TYPE_NOT_CONNECTED = -1;

    /**
     * 获取网络连接类型
     * @param context
     * @return
     */
    public static int getNetworkType(Context context) {
        int type = TYPE_NOT_CONNECTED;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if (!AppUtils.checkNull(activeNetworkInfo)) {
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            }

            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE;
            }

            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
                return TYPE_ETHERNET;
            }
        }
        return type;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = getNetworkType(context);
        String status = "";
        if (conn == TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if(conn == TYPE_ETHERNET) {
            status = "Ethernet enable";
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }

    /**
     * 测试IP是否可达
     * @param ipStr
     * @return
     */
    public static boolean checkReachableByIP(String ipStr) {
        boolean result = false;

        try {
            result = InetAddress.getByName(ipStr).isReachable(2000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
