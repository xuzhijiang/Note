package com.android.core.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class AppUtils {

    /**
     * 判断应用是否安装
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        if (checkNull(packageName)) return false;

        packageName = packageName.trim();
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            // packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            packageInfo = null;
        } catch (Exception e) {
            packageInfo = null;
        }
        return packageInfo == null ? false : true;
    }

    public static String getAppVersion(Context context, String pkg){
        String version = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(pkg, 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public void restartAppByName(Context context, String pkg){
        Intent intent;
        if (!TextUtils.isEmpty(pkg)) {
            intent = context.getPackageManager().getLaunchIntentForPackage(pkg);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            if (intent != null) {
                context.startActivity(intent);
            }
        }
    }

    /**
     * 拉起应用
     * @param context
     * @param packageName
     * @return
     */
    public static boolean launchAppByPackageName(Context context, String packageName) {
        if (checkNull(packageName))
            return false;

        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        final List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(mainIntent, 0);
        if (apps != null) {
            int count = apps.size();
            for (int i=0;i<count;i++){
                final ResolveInfo resolveInfo = apps.get(i);
                final ActivityInfo activityInfo = resolveInfo.activityInfo;
                if (activityInfo.packageName.equals(packageName)) {
                    String className = activityInfo.name;
                    ComponentName cn = new ComponentName(packageName, className);
                    final Intent it = new Intent(Intent.ACTION_MAIN);
                    it.addCategory(Intent.CATEGORY_LAUNCHER);
                    it.setComponent(cn);
                    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(it);
                    // 成功打开
                    return true;
                }
            }
        }
        // 打开失败，原因是应用未安装或者不能从桌面上启动
        return false;
    }

    public static void startAppByName(Context context, String pkg) {
        Intent intent = null;
        if (!TextUtils.isEmpty(pkg)) {
            intent = context.getPackageManager().getLaunchIntentForPackage(pkg);
            // 如果没有对应pkg的应用就选择一个默认的.
            if (intent == null) {
                intent = context.getPackageManager().getLaunchIntentForPackage("default_pkg");
            }

            if (intent != null) {
                context.startActivity(intent);
            }
        }
    }

    /**
     * 获取Application context
     */
    public static Context getApplicationContext(Context context) {
        Context appContext;
        if (context == null || context instanceof Application) {
            appContext = context;
        } else {
            appContext = context.getApplicationContext();
        }
        return appContext;
    }

    /**
     * 跳转到指定包名的应用详情页
     */
    public static void jumpToAppDetail(Context context, String packageName) {
        if (checkNull(packageName))
            return;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        String appDetailUrl = String.format("mmtv://app_info_forward?requestid=app_detail&packagename=%s", packageName);
        Uri uri = Uri.parse(appDetailUrl);
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 判断字符串是否为空
     * @param value
     * @return
     */
    public static boolean checkNull(String value) {
        if (value == null || value.length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断对象是否为null
     * @param obj
     * @return
     */
    public static boolean checkNull(Object obj) {
        return obj == null ? true : false;
    }

    /**
     * 判断当前应用是否正在前台。
     *
     * @param packageName
     * @return
     */
    public boolean isTopActivity(Context context, String packageName) {
        try {
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

            @SuppressWarnings("deprecation")
            List<RunningTaskInfo> tasksInfo = manager.getRunningTasks(1);

            if (tasksInfo.size() > 0) {
                if (packageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getTimeStr() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        String dateNowStr = sdf.format(date);
        return dateNowStr;
    }

    public static void printSDCardPath() {
        String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * https://www.runoob.com/w3cnote/android-tutorial-alarmmanager.html
     * @param context
     */
    public static void startAlarm(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent();
        intent.putExtra("url", "http://www.google.com");
        intent.setAction("custom.intent.action.catch");
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        // 设置一次性闹钟
        /**
         * AndroidL开始，设置的alarm的触发时间必须大于当前时间 5秒
         *
         * 设置一次闹钟-(5s后启动闹钟)
         *
         * @AlarmType int type：闹钟类型
         * ELAPSED_REALTIME：在指定的延时过后，发送广播，但不唤醒设备（闹钟在睡眠状态下不可用）。如果在系统休眠时闹钟触发，它将不会被传递，直到下一次设备唤醒。
         * ELAPSED_REALTIME_WAKEUP：在指定的延时过后，发送广播，并唤醒设备（即使关机也会执行operation所对应的组件）。延时是要把系统启动的时间SystemClock.elapsedRealtime()算进去的。
         * RTC：指定当系统调用System.currentTimeMillis()方法返回的值与triggerAtTime相等时启动operation所对应的设备（在指定的时刻，发送广播，但不唤醒设备）。如果在系统休眠时闹钟触发，它将不会被传递，直到下一次设备唤醒（闹钟在睡眠状态下不可用）。
         * RTC_WAKEUP：指定当系统调用System.currentTimeMillis()方法返回的值与triggerAtTime相等时启动operation所对应的设备（在指定的时刻，发送广播，并唤醒设备）。即使系统关机也会执行 operation所对应的组件。
         *
         * long triggerAtMillis：触发闹钟的时间。
         * PendingIntent operation：闹钟响应动作（发广播，启动服务等）
         */
        // am.set(@AlarmType int type, long triggerAtMillis, PendingIntent operation);

        // 3、设置精准周期重复性闹钟
        /**
         * AndroidL开始repeat的周期必须大于60秒
         *
         * 设置精准周期闹钟-该方法提供了设置周期闹钟的入口，闹钟执行时间严格按照startTime来处理，使用该方法需要的资源更多，不建议使用。
         *
         * @AlarmType int type：闹钟类型
         * long triggerAtMillis：触发闹钟的时间。
         * long intervalMillis：闹钟两次执行的间隔时间
         * PendingIntent operation：闹钟响应动作（发广播，启动服务等）
         */
        // am.setRepeating(@AlarmType int type, long triggerAtMillis, long intervalMillis, PendingIntent operation);


        // 4、设置不精准周期重复性闹钟
        /**
         * AndroidL开始repeat的周期必须大于60秒
         *
         * 设置不精准周期闹钟- 该方法也用于设置重复闹钟，与第二个方法相似，不过其两个闹钟执行的间隔时间不是固定的而已。它相对而言更省电（power-efficient）一些，因为系统可能会将几个差不多的闹钟合并为一个来执行，减少设备的唤醒次数。
         *
         * @AlarmType int type：闹钟类型
         * long triggerAtMillis：触发闹钟的时间。
         * long intervalMillis：闹钟两次执行的间隔时间
         * 内置变量
         * INTERVAL_DAY： 设置闹钟，间隔一天
         * INTERVAL_HALF_DAY： 设置闹钟，间隔半天
         * INTERVAL_FIFTEEN_MINUTES：设置闹钟，间隔15分钟
         * INTERVAL_HALF_HOUR： 设置闹钟，间隔半个小时
         * INTERVAL_HOUR： 设置闹钟，间隔一个小时
         *
         * PendingIntent operation：闹钟响应动作（发广播，启动服务等）
         */
        // am.setInexactRepeating(@AlarmType int type, long triggerAtMillis, long intervalMillis, PendingIntent operation);

        // 5、设置时区
        // alarmManager.setTimeZone(String timeZone);
        // 如：东八区
        // alarmManager.setTimeZone("GMT+08:00");

        // 时区设置需要SET_TIME_ZONE，所以要在清单文件中添加权限：
        // <!-- 允许设置时区-->
        // <uses-permission android:name="android.permission.SET_TIME_ZONE" />

        // 6、取消闹钟
        // alarmManager.cancel(PendingIntent operation)

        long interval = TimeUnit.HOURS.toMillis(2);
        long startTime = SystemClock.elapsedRealtime(); // 闹钟的开始时间,如果为0就是马上执行闹钟

        // 在time的时候触发闹钟, 每隔 interval毫秒 执行上面的intent
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, startTime + interval, interval, pendingIntent);
    }

    /**
     * 判断是否为数字
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static float getScreenDensity(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }

    public static void setValue(Context context, String spName, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getValue(Context context, String spName, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static void setValue(Context context, String spName, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getValue(Context context, String spName, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public static void runOnUIThread(Activity activity, Runnable target) {
        activity.runOnUiThread(target);
    }

    public static void startAppByComponent(Context context) {
        Intent intent = null;
        ComponentName cn = new ComponentName("pkg", "cls");
        intent = new Intent();
        intent.setComponent(cn);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void deleteAttributeByKey(Context context, String spName, String fileName, String key) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor = editor.remove(key);
        editor.commit();
    }


}
