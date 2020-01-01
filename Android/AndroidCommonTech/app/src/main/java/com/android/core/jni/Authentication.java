package com.android.core.jni;

public class Authentication {

    private static Authentication sInstance;

    private Authentication() {
        // native_setup(new WeakReference<Authentication>(this));
    }

    public static Authentication getInstance() {
        if (sInstance == null) {
            synchronized (Authentication.class) {
                if (sInstance == null) {
                    sInstance = new Authentication();
                }
            }
        }
        return sInstance;
    }

    static{
        // 加载iptvplayer_jni.so
        // System.loadLibrary("iptvplayer_jni");
        // System.load("iptvplayer_jni.so");
        // native_init();
    }

    // public native String getHWIndenty(String sessionId , String mac,String shareKey);

    // 初始化
    // private static native void init();

    // private native void native_setup(Object objReference);

    /**
     * 底层回调函数
     * @param objReference 对象引用
     * @param what
     */
    private static void postEventFromNative(Object objReference,int what){}

    //释放资源
    // public native void finalize();
}
