package com.liqi.utils.wifi;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;

import java.util.List;

/**wifi操作对象扫描之后操作方法接口
 * Created by LiQi on 2017/12/15.
 */

public interface OnWifiControllerListener {
    /**
     * 得到配置好的网络.
     */
    List<WifiConfiguration> getConfiguration();
    /**
     * 指定配置好的网络进行连接.
     */
    void connetionConfiguration(int index);
    /**
     * 获取wifi列表.
     */
    List<ScanResult> getWifiList();
    /**
     * 查看扫描结果.
     */
    StringBuffer lookUpScan();
}
