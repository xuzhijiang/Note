package com.android.core.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.List;

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
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            packageInfo = null;
        } catch (Exception e) {
            packageInfo = null;
        }
        return packageInfo == null ? false : true;
    }

    /**
     * 拉起应用
     * @param context
     * @param packageName
     * @return
     */
    public static boolean launchApp(Context context, String packageName) {
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
}
