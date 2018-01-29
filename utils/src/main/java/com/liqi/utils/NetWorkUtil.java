package com.liqi.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络监测帮助类
 *
 * @author LiQi
 */
public class NetWorkUtil {

    /**
     * 网络是否可用
     *
     * @return true|false
     */
    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断是否有连接网络（wifi Or 移动网络）
     *
     * @param context 上下文
     * @return true是有网络连接，false是无网络连接。
     */
    public static boolean isNetworkConnectionsOK(Context context) {
        return isNetWorkWifi(context) || isNetWorkGPRS(context);
    }

    /**
     * 获取WiFi网络状态
     *
     * @param context 上下文
     * @return true是有网络连接，false是无网络连接。
     */
    public static boolean isNetWorkWifi(Context context) {
        if (isNetworkAvailable(context)) {
            ConnectivityManager conManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 获取手机网络状态
     *
     * @param context 上下文
     * @return true是有网络连接，false是无网络连接。
     */
    public static boolean isNetWorkGPRS(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取代表联网状态的NetWorkInfo对象
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        // 获取当前的网络连接是否可用
        if (null == networkInfo) {
            return false;
        } else {
            boolean available = networkInfo.isAvailable();
            if (!available) {
                return false;
            }
        }
        return true;
    }
    /**
     * 判断网络连接类型
     *
     * @param context 上下文
     * @return 网络链接类型
     */
    public static int getConnectionType(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = manager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isAvailable()) {
                return netInfo.getType();
            }
        }
        return -1;
    }
}