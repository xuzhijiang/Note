package com.android.core.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class CustomDialog extends Dialog {

    /** 默认宽度 */
    private static int default_width = 160;
    /** 默认高度 */
    private static int default_height = 120;
    /** 常规的顶级窗口--该顶级view下的部件可以继续操作 */
    public static final int TYPE_TOPLEVEL_WIN_NORMAL = 1;
    /** 遮罩式(模式)顶级窗口--该顶级view下的部件被遮住，不可以操作 */
    public static final int TYPE_TOPLEVEL_WIN_MODAL = 2;

    private static final int WRAP = WindowManager.LayoutParams.WRAP_CONTENT;

    private static final String TAG = "CustomDialog";

    public CustomDialog(Context context, int layout, int style) {
        this(context, default_width, default_height, layout, style);
    }

    public CustomDialog(Context context, int width, int height, int layout, int style) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = getWinLayParams(width, height, TYPE_TOPLEVEL_WIN_MODAL);
        // set width,height by density and gravity
        window.setAttributes(params);
    }

    public CustomDialog(Context context, int width, int height, View view, int style) {
        super(context, style);
        setContentView(view);
        // set window params
        Window window = getWindow();
        // WindowManager.LayoutParams params = window.getAttributes();
        WindowManager.LayoutParams params = getWinLayParams(width, height, TYPE_TOPLEVEL_WIN_MODAL);
        // set width,height by density and gravity
        window.setAttributes(params);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode >= KeyEvent.KEYCODE_DPAD_UP && keyCode <= KeyEvent.KEYCODE_DPAD_CENTER)
            return super.onKeyDown(keyCode, event);
        return true;

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode >= KeyEvent.KEYCODE_DPAD_UP && keyCode <= KeyEvent.KEYCODE_DPAD_CENTER)
            return super.onKeyUp(keyCode, event);
        return true;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode >= KeyEvent.KEYCODE_DPAD_UP && keyCode <= KeyEvent.KEYCODE_DPAD_CENTER)
            return super.onKeyLongPress(keyCode, event);
        return true;
    }

    private WindowManager.LayoutParams getWinLayParams(int width, int height, int type) {
        WindowManager.LayoutParams wlp = new WindowManager.LayoutParams(WRAP, WRAP);
        // wlp.alpha = 0.5f;
        wlp.dimAmount = 0.0f;
        wlp.format = PixelFormat.TRANSPARENT;
        switch (type) {
            case TYPE_TOPLEVEL_WIN_MODAL:
                // 任何部件都在其之下，可以通过dimAmount(0~1.0)设置透明度
                wlp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                wlp.dimAmount = 0.5f;
                break;
            case TYPE_TOPLEVEL_WIN_NORMAL:
                // 不抢聚焦点
                wlp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                wlp.dimAmount = 0.5f;
                break;
            default:
                break;
        }
        wlp.flags = wlp.flags | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        wlp.flags = wlp.flags | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        wlp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT; // 系统提示类型
        wlp.width = width;// WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = height;// WindowManager.LayoutParams.MATCH_PARENT;
        wlp.gravity = Gravity.CENTER;
        return wlp;
    }

}