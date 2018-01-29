package com.liqi.utils.wifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;

import java.util.List;

/**
 * wifi操作对象
 *
 * @author Liqi
 */
public class WifiController implements OnWifiControllerListener {
    private static WifiController wifiController;
    /**
     * 定义一个WifiManager对象.
     */
    private WifiManager mWifiManager;
    /**
     * 扫描出的网络连接列表.
     */
    private List<ScanResult> mWifiList;
    /**
     * 网络连接列表.
     */
    private List<WifiConfiguration> mWifiConfigurations;
    /**
     * Wifi锁.
     */
    private WifiLock mWifiLock;

    /**
     * 初始化
     *
     * @param context
     */
    private WifiController(Context context) {
        mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    private WifiController() {
    }

    /**
     * 单列获取WifiController对象
     * @param context 上下文
     * @return
     */
    public static WifiController getWifiController(Context context) {
        synchronized (WifiController.class.getName()) {
            if (wifiController == null) {
                wifiController = new WifiController(context);
            }
        }
        return wifiController;
    }

    /**
     * 判断wifi是否加密.
     *
     * @param capabilities wifi内部属性值
     * @return int 0:未加密 其他:加密
     */
    public static int getSecurity(String capabilities) {
        if (capabilities.contains("WEP")) {
            return 1;
        } else if (capabilities.contains("WPA")) {
            return 2;
        } else if (capabilities.contains("EAP")) {
            return 3;
        }
        return 0;
    }

    /**
     * 获取Wifi信息
     *
     * @return WifiInfo
     */
    public WifiInfo getWifiInfo() {
        return mWifiManager.getConnectionInfo();
    }

    /**
     * wifi是否启用
     *
     * @return true 启用，false没有启用。
     */
    public boolean isWifiEnabled() {
        return mWifiManager.isWifiEnabled();
    }

    /**
     * 打开/关闭wifi
     *
     * @param val true打开，false关闭。
     */
    private void setWifiEnabled(boolean val) {
        mWifiManager.setWifiEnabled(val);
    }

    /**
     * 打开wifi.
     */
    public void openWifi() {
        if (!isWifiEnabled()) {
            setWifiEnabled(true);
        }
    }

    /**
     * 关闭wifi.
     */
    public void closeWifi() {
        if (isWifiEnabled()) {
            setWifiEnabled(false);
        }
    }

    /**
     * 检查当前wifi状态. WIFI_STATE_DISABLED : WIFI网卡不可用（1） WIFI_STATE_DISABLING :
     * WIFI网卡正在关闭（0） WIFI_STATE_ENABLED : WIFI网卡可用（3） WIFI_STATE_ENABLING :
     * WIFI网正在打开（2）（WIFI启动需要一段时间） WIFI_STATE_UNKNOWN : 未知网卡状态
     */
    public int checkState() {
        return mWifiManager.getWifiState();
    }

    /**
     * 创建一个wifiLock.
     */
    public void createWifiLock() {
        mWifiLock = mWifiManager.createWifiLock("wifi_lock");
    }

    /**
     * 锁定wifiLock，在屏幕被关掉后，阻止WiFi进入睡眠状态.
     */
    public void acquireWifiLock() {
        mWifiLock.acquire();
    }

    /**
     * 解锁wifiLock.
     */
    public void releaseWifiLock() {
        // 判断是否锁定
        if (mWifiLock.isHeld()) {
            mWifiLock.acquire();
        }
    }

    /**
     * 得到配置好的网络.
     */
    @Override
    public List<WifiConfiguration> getConfiguration() {
        return mWifiConfigurations;
    }

    /**
     * 指定配置好的网络进行连接.
     */
    @Override
    public void connetionConfiguration(int index) {
        if (index > mWifiConfigurations.size()) {
            return;
        }
        // 连接配置好指定ID的网络
        mWifiManager.enableNetwork(mWifiConfigurations.get(index).networkId,
                true);
    }

    /**
     * 开启扫描
     * @return
     */
    public OnWifiControllerListener startScan() {
        mWifiManager.startScan();
        // 得到扫描结果
        mWifiList = mWifiManager.getScanResults();
        // 得到配置好的网络连接
        mWifiConfigurations = mWifiManager.getConfiguredNetworks();
        return this;
    }

    /**
     * 获取wifi列表.
     */
    @Override
    public List<ScanResult> getWifiList() {
        return mWifiList;
    }

    /**
     * 查看扫描结果.
     */
    @Override
    public StringBuffer lookUpScan() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mWifiList.size(); i++) {
            sb.append("Index_" + new Integer(i + 1).toString() + ":");
            // 将ScanResult信息转换成一个字符串包
            // 其中把包括：BSSID、SSID、capabilities、frequency、level
            sb.append((mWifiList.get(i)).toString()).append("\n");
        }
        return sb;
    }

    /**
     * 添加一个网络并连接.
     */
    public void addNetWork(WifiConfiguration configuration) {
        int wcgId = mWifiManager.addNetwork(configuration);
        mWifiManager.enableNetwork(wcgId, true);
    }

    /**
     * 断开指定ID的网络.
     */
    public void disConnectionWifi(int netId) {
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
    }

    /**
     * 连接wifi（有密码）.
     *
     * @param ssid   热点名
     * @param strPSW 热点密码
     */
    public void connect(String ssid, String strPSW) {
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = "\"" + ssid + "\"";
        config.preSharedKey = "\"" + strPSW + "\"";
        config.allowedKeyManagement.set(KeyMgmt.WPA_PSK);
        config.status = WifiConfiguration.Status.ENABLED;
        int netId = mWifiManager.addNetwork(config);
        mWifiManager.disconnect();
        mWifiManager.enableNetwork(netId, true);
        mWifiManager.reconnect();
    }

    /**
     * 连接wifi（无密码）.
     *
     * @param ssid 热点名
     */
    public void connectNoPwd(String ssid) {
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = "\"" + ssid + "\"";
        config.preSharedKey = null;
        config.allowedKeyManagement.set(KeyMgmt.NONE);
        config.status = WifiConfiguration.Status.ENABLED;
        int netId = mWifiManager.addNetwork(config);
        mWifiManager.disconnect();
        mWifiManager.enableNetwork(netId, true);
        mWifiManager.reconnect();
    }
}
