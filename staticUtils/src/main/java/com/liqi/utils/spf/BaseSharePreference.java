package com.liqi.utils.spf;

import android.content.Context;
import android.content.SharedPreferences;

import com.liqi.Logger;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

/**
 *  SharePreference偏好设置的工具
 *
 * @author LiQi
 */
public class BaseSharePreference {
    private static Reference<BaseSharePreference> mBaseSharePreference;
    private SharedPreferences spf;

    /**
     * @param spName SharePreference文件名
     */
    private BaseSharePreference(Context context, String spName) {
        spf = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    /**
     * 初始化单列SharePreference
     *
     * @param context 上下文
     * @param spName  SharePreference文件名
     * @return BaseSharePreference对象
     */
    public static BaseSharePreference initBaseSharePreference(Context context, String spName) {
        return null == mBaseSharePreference ? (mBaseSharePreference = new SoftReference<>(new BaseSharePreference(context, spName))).get() : mBaseSharePreference.get();
    }

    /**
     * 创建一个新的SharePreference文件
     *
     * @param context 上下文
     * @param spName  SharePreference文件名
     * @return BaseSharePreference对象
     */
    public static BaseSharePreference getNewBaseSharePreference(Context context, String spName) {
        return new BaseSharePreference(context, spName);
    }

    /**
     * 获取单列BaseSharePreference对象
     *
     * @param context 上下文
     * @return BaseSharePreference对象
     */
    public static BaseSharePreference getBaseSharePreference(Context context) {
        if (null == mBaseSharePreference) {
            Logger.e("BaseSharePreference", "BaseSharePreference没有初始化，创建一个独立的BaseSharePreference实例。");
            return getNewBaseSharePreference(context, "LiQi_Utils");
        } else {
            return mBaseSharePreference.get();
        }
    }

    /**
     * 偏好设置写入方法
     *
     * @param key   写入键
     * @param value 写入值String
     */
    public void putObjectKeyValue(String key, String value) {
        spf.edit().putString(key, value).commit();
    }

    /**
     * 偏好设置写入方法
     *
     * @param key   写入键
     * @param value 写入值int
     */
    public void putObjectKeyValue(String key, int value) {
        spf.edit().putInt(key, value).commit();
    }

    /**
     * 偏好设置写入方法
     *
     * @param key   写入键
     * @param value 写入值long
     */
    public void putObjectKeyValue(String key, long value) {
        spf.edit().putLong(key, value).commit();
    }

    /**
     * 偏好设置写入方法
     *
     * @param key   写入键
     * @param value 写入值Float
     */
    public void putObjectKeyValue(String key, Float value) {
        spf.edit().putFloat(key, value).commit();
    }

    /**
     * 偏好设置写入方法
     *
     * @param key   写入键
     * @param value 写入值boolean
     */
    public void putObjectKeyValue(String key, boolean value) {
        spf.edit().putBoolean(key, value).commit();
    }

    /**
     * 获取偏好设置里面的值
     * <hint>
     * int,long,float类型的缺省值为0。
     * String 类型的缺省值为""。
     * boolean 类型的缺省值为false。
     * 如果使用BaseSharePreferenceTypeEnum.SP_ALL_MAP类型取出，那么key失效
     * </hint>
     *@param <T> 继承 Object的泛型
     * @param key      键
     * @param typeEnum 数据取出类型.通过BaseSharePreferenceTypeEnum枚举设置
     * @see BaseSharePreferenceTypeEnum
     * @return T
     */

    public <T extends Object> T getObjectKeyValue(String key, BaseSharePreferenceTypeEnum typeEnum) {
        switch (typeEnum) {
            case SP_INT:
                return (T) (Integer) spf.getInt(key, 0);
            case SP_LONG:
                return (T) (Long) spf.getLong(key, 0);
            case SP_STRING:
                return (T) spf.getString(key, "");
            case SP_FLOAT:
                return (T) (Float) spf.getFloat(key, 0);
            case SP_BOOLEAN:
                return (T) (Boolean) spf.getBoolean(key, false);
            case SP_ALL_MAP:
                return (T) spf.getAll();
            default:
                return (T) "";
        }

    }

    /**
     * 偏好设置移除方法
     *
     * @param key 移除 健
     */
    public void removeObjectKeyValue(String key) {
        spf.edit().remove(key).commit();
    }

    /**
     * 全部清除偏好设置
     */
    public void removeAllValue() {
        spf.edit().clear().commit();
    }
}
