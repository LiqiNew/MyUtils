package com.liqi.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.widget.DatePicker;
import android.widget.TextView;

import com.liqi.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 静态实用工具
 *
 * @author Liqi
 */
public class StaticUtility {

    private static final String TAG = StaticUtility.class.getName();

    /**
     * 获取本地APP版本号
     *
     * @param context 上下文
     * @return APP版本号，转换的Int类型
     */
    public static int getVersionInt(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
        return Integer.parseInt(versionName.replace(".", ""));
    }

    /**
     * 获取本地APP版本号
     *
     * @param context 上下文
     * @return APP版本号，String类型
     */
    public static String getVersionString(Context context) {
        String versionName;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                versionName = "App版本为空";
            }
        } catch (Exception e) {
            versionName = "App版本获取异常";
        }
        return versionName;
    }

    /**
     * 判断一个服务是否已经开启
     *
     * @param context   上下文
     * @param className 服务对象所在的包名+对象名
     * @return true是启动了，false未启动
     */
    public static boolean isWorked(Context context, String className) {
        ActivityManager myManager = (ActivityManager) context
                .getApplicationContext().getSystemService(
                        Context.ACTIVITY_SERVICE);
        ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager
                .getRunningServices(100);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取手机的AndroidID
     *
     * @param context 上下文
     * @return Android ID
     */
    public static String getAndroidID(Context context) {
        String androidId = Secure.getString(context.getContentResolver(),
                Secure.ANDROID_ID);
        if (androidId == null)
            androidId = "404";
        return androidId;
    }

    /**
     * 获取设备的唯一标识，deviceId.需要当前系统具备号码
     *
     * @param context 上下文
     * @return 设备的唯一标识，device Id.
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        if (deviceId == null) {
            return "-";
        } else {
            return deviceId;
        }
    }

    /**
     * 获取手机品牌
     *
     * @return 手机品牌
     */
    public static String getPhoneBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机Android API等级（22、23 ...）
     *
     * @return Android API等级
     */
    public static int getBuildLevel() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机Android 版本（4.4、5.0、5.1 ...）
     *
     * @return 手机Android 版本
     */
    public static String getBuildVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取mac地址。高低版本兼容
     *
     * @param context 上下文
     * @return mac
     */
    public static String getMacAddress(Context context) {
        String mac;
        if (StaticUtility.getBuildLevel() < Build.VERSION_CODES.M) {
            mac = getLowVersionMacAddress(context);
        } else {
            mac = getHighVersionMacAddress();
        }
        return mac;
    }

    /**
     * 低版本获取手机的MAC地址
     *
     * @param context 上下文
     * @return 手机的MAC地址
     */
    private static String getLowVersionMacAddress(Context context) {
        String rs = "02:00:00:00:00:00";
        try {
            WifiManager wifi = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            if (wifi == null) {
                return rs;
            }

            WifiInfo info = wifi.getConnectionInfo();
            rs = info.getMacAddress();
            if (TextUtils.isEmpty(rs)) {
                rs = "02:00:00:00:00:00";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    // Android 6.0以上获取Mac地址
    private static String getHighVersionMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }
                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 获取手机当前连的网络IP地址
     *
     * @param context 上下文
     * @return 网络IP地址
     */
    public static String getNetworkIP(Context context) {
        String ip = "";
        // 得到网络连接管理者
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface
                            .getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf
                                .getInetAddresses(); enumIpAddr
                                     .hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress()) {
                                ip = inetAddress.getHostAddress().toString();
                            }
                        }
                    }
                } catch (SocketException ex) {
                    System.out.println("获取手机IP出错>>" + ex.toString());
                }
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                WifiManager wifiManager = (WifiManager) context
                        .getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ipAddress = wifiInfo.getIpAddress();
                ip = intToIp(ipAddress);
            } else {
                System.out.println("连上未知的网络");
            }
        }
        return ip;

    }

    private static String intToIp(int i) {

        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + (i >> 24 & 0xFF);
    }

    /**
     * 根据标识打开一个其它APPA
     *
     * @param context 上下文
     * @param comp    创建ComponentName对象需要传启动项目的包名和启动对象路径
     */
    public static void startActivityNew(Context context, ComponentName comp) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(comp);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.intent.action.VIEW");
        System.out.println("<<<开启一个新的APP>>>");
        context.startActivity(intent);
    }

    /**
     * 调用拨号界面
     *
     * @param context 上下文
     * @param phone   电话号码
     */
    public static void callView(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 通过ID获取Bitmap
     *
     * @param context 上下文
     * @param resId   图片ID
     * @param dpi     屏幕DPI密度
     * @return Bitmap
     */
    public static Bitmap readBiamap(Context context, int resId, int dpi) {
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options op = new BitmapFactory.Options();
            DisplayMetrics displayMetrics = context.getResources()
                    .getDisplayMetrics();
            op.inPreferredConfig = Bitmap.Config.RGB_565;// 设置让解码器以最佳方式解码
            op.inJustDecodeBounds = false; // 设置是否返回图片的宽和高
            // 下面两个字段需要组合使用
            op.inPurgeable = true;
            op.inInputShareable = true;
            // 如果 inTargetDensity图片的密度大于inDensity屏幕密度，那么就对图片进行缩放
            op.inScaled = true; // 是否进行缩放
            op.inTargetDensity = displayMetrics.densityDpi; // 设置图片的密度
            op.inDensity = dpi;// 设置屏幕的密度
            InputStream is = context.getResources().openRawResource(resId);
            bitmap = BitmapFactory.decodeStream(is, null, op);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 调用系统的时间Dialog
     *
     * @param context 上下文
     * @param text    赋值控件
     * @param date    控件时间（格式：2012-02-05）
     * @return 系统的时间Dialog
     */
//    public static Dialog onCreateDialog(Context context, final TextView text,
//                                        String date) {
//        Dialog dialog = null;
//        String time[] = date.split("-");
//        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month,
//                                  int dayOfMonth) {
//
//                String month_s = month + 1 + "";
//
//                if ((month + 1) < 10) {
//                    month_s = "0" + month_s;
//                }
//
//                String dayOfMonth_s = dayOfMonth + "";
//
//                if (dayOfMonth < 10) {
//                    dayOfMonth_s = "0" + dayOfMonth_s;
//                }
//
//                text.setText(year + "-" + month_s + "-" + dayOfMonth_s);
//            }
//        };
//        dialog = new DatePickerDialog(context, dateListener,
//                Integer.valueOf(time[0]), Integer.valueOf(time[1]) - 1,
//                Integer.valueOf(time[2]));
//
//        return dialog;
//    }

    /**
     * 从Assets中读取图片
     *
     * @param fileName 本地资源文件名
     * @param context  上下文
     * @return Bitmap
     */
    public static Bitmap getImageFromAssetsFile(String fileName, Context context) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;

    }

    /**
     * 判断应用是否运行
     *
     * @return true是运行，false没有运行。
     */
    public static boolean isAppRunning(Context context) {
        boolean isAppRunning = false;
        if (null != context) {
            String pgkName = context.getPackageName();
            ActivityManager am = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.topActivity.getPackageName().equals(pgkName)
                        && info.baseActivity.getPackageName().equals(pgkName)) {
                    isAppRunning = true;
                    break;
                }
            }
        } else {
            isAppRunning = true;
        }
        return isAppRunning;
    }

    /**
     * 返回屏幕获取的高度和宽度
     */
    public static int getPixelsHeightWidth(Context context, @NonNull PixelsHeightWidth heightWidth) {
        return getPixelsHeightWidth(context).get(heightWidth);
    }

    /**
     * 返回屏幕获取的高度和宽度
     *
     * @return 装有设备的高度和宽度的Map对象
     */
    public static Map<PixelsHeightWidth, Integer> getPixelsHeightWidth(Context context) {
        Map<PixelsHeightWidth, Integer> floatMap = new HashMap<>();
        // 获取屏幕的对象
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        floatMap.put(PixelsHeightWidth.HEIGHT, displayMetrics.heightPixels);
        floatMap.put(PixelsHeightWidth.WIDTH, displayMetrics.widthPixels);
        return floatMap;
    }

    /**
     * 获取状态栏高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeightAttribute = -1;
        if (null != context) {
            statusBarHeightAttribute = getStatusBarHeightAttribute(context);
            if (statusBarHeightAttribute == -1) {
                statusBarHeightAttribute = getStatusBarHeightClass(context);
            }
        } else {
            Logger.e(TAG, "getStatusBarHeight()：context=null");
        }
        return statusBarHeightAttribute;
    }

    /**
     * 通过系统属性获取状态栏高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    private static int getStatusBarHeightAttribute(Context context) {
        int result = -1;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 通过反射机制获取状态栏高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    private static int getStatusBarHeightClass(Context context) {
        int statusBarHeight2 = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight2 = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight2;
    }

    /**
     * 获取导航栏高度
     *
     * @param context
     * @return
     */
    public static int getDaoHangHeight(Context context) {
        //有导航栏才获取高度
        if (checkDeviceHasNavigationBar(context)) {
            int resourceId;
            int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
            if (rid != 0) {
                resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
                Logger.e(TAG, "导航栏ID：" + resourceId);
                Logger.e(TAG, "导航栏高度：" + context.getResources().getDimensionPixelSize(resourceId) + "");
                return context.getResources().getDimensionPixelSize(resourceId);
            } else {
                return 0;
            }
        }
        return 0;
    }

    /**
     * 判断是否有导航栏
     *
     * @param context 上下文
     * @return true是有，false是没有
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {

        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(context)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);
        Logger.e(TAG, "是否有导航栏>>：" + (!hasMenuKey && !hasBackKey));
        return !hasMenuKey && !hasBackKey;
    }

    /**
     * 判断是否已经root
     *
     * @return true是已经root，false不是。
     */
    public static boolean isDeviceRooted() {
        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3();
    }

    private static boolean checkRootMethod1() {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private static boolean checkRootMethod2() {
        String[] paths = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }

    private static boolean checkRootMethod3() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) return true;
            return false;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }

    /**
     * 判断是否在模拟器上运行
     *
     * @param context
     * @return true是模拟器上运行，false不是
     */
    public static boolean isEmulator(Context context) {
        String url = "tel:" + "123456";
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_DIAL);
        // 是否可以处理跳转到拨号的 Intent
        boolean canResolveIntent = intent.resolveActivity(context.getPackageManager()) != null;

        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.SERIAL.equalsIgnoreCase("unknown")
                || Build.SERIAL.equalsIgnoreCase("android")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT)
                || ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
                .getNetworkOperatorName().toLowerCase().equals("android")
                || !canResolveIntent;
    }

    /**
     * 跳转到首页面
     *
     * @param context
     */
    public static void skipHome(Context context) {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(home);
    }

    public enum PixelsHeightWidth {
        //高度
        HEIGHT,
        //宽度
        WIDTH
    }
}
