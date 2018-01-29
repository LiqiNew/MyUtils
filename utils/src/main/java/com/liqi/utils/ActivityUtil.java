package com.liqi.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.liqi.Logger;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Activity跳转intent工具类
 *
 * @author LiQi
 */
public class ActivityUtil {
    private static ActivityUtil mActivityUtil;
    private final String TAG = "ActivityUtil.";
    private Intent mIntent;

    private ActivityUtil() {
        mIntent = new Intent();
    }

    /**
     * 获取ActivityUtil对象
     *
     * @return
     */
    public static ActivityUtil getActivityUtil() {
        return mActivityUtil = null == mActivityUtil ? new ActivityUtil() : mActivityUtil;
    }

    /**
     * 关闭所有的acitivity
     *
     * @param activities List<Activity>数据
     */
    public void finishActivities(List<Activity> activities) {
        if (null != activities) {
            for (int i = 0; i < activities.size(); i++) {
                Activity activity = activities.get(i);
                if (activity != null) {
                    activity.finish();
                } else {
                    Logger.e(TAG + "finishActivities()", "集合中数据为空。数据索引为：" + i);
                }
            }
        } else {
            Logger.e(TAG + "finishActivities()", "参数值传入为空");
        }
    }

    /**
     * 开始一个新的activity
     *
     * @param context 上下文
     * @param clazz   目标activity.class
     */
    public void startActivity(Context context, Class clazz) {
        if (null != context && null != clazz) {
            clearIntent();
            mIntent.setClass(context, clazz);
            context.startActivity(mIntent);
        } else {
            Logger.e(TAG + "startActivity()", "参数值传入为空");
        }
    }

    /**
     * 跳转到指定界面,并关闭其它所有打开的界面
     *
     * @param context 上下文
     * @param clazz   目标activity.class
     */
    public void startActivityClearAllInstances(Context context, Class<?> clazz) {
        if (null != context && null != clazz) {
            Intent intent = new Intent(context, clazz).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            Logger.e(TAG + "startActivityClearAllInstances()", "参数值传入为空");
        }
    }

    /**
     * 开启一个新的activity
     *
     * @param context 上下文
     * @param clazz   目标acitivity class
     * @param data    string类型的数据 key:名称 value:String类型值
     */
//    public void startActivityWithIntegerData(Context context, Class clazz, Map<String, String> data) {
//        if (null!=context&&null != clazz) {
//            clearIntent();
//            mIntent.setClass(context, clazz);
//            if (data != null) {
//                for (Entry<String, String> en : data.entrySet()) {
//                    mIntent.putExtra(en.getKey(), en.getValue());
//                }
//            }else{
//                Logger.e(TAG + "startActivityWithIntegerData()", "参数data的值传入为空");
//            }
//            context.startActivity(mIntent);
//        }else {
//            Logger.e(TAG + "startActivityWithIntegerData()", "参数值传入为空");
//        }
//    }

    /**
     * 开启一个新的activity
     *
     * @param context 上下文
     * @param clazz   目标acitivity class
     * @param data    string类型的数据 key:名称 value:Integer类型值
     */
//    public void startActivityWithStringData(Context context, Class clazz, Map<String, String> data) {
//        if (null != clazz) {
//            clearIntent();
//            mIntent.setClass(context, clazz);
//            if (data != null) {
//                for (Entry<String, String> en : data.entrySet())
//                    mIntent.putExtra(en.getKey(), en.getValue());
//            }
//            context.startActivity(mIntent);
//        }
//    }

    /**
     * 开启一个新的activity
     *
     * @param context 上下文
     * @param clazz   目标activity.class
     * @param data    Object类型的数据
     *                key:名称
     *                value:Object类型值,支持的类型(String,Integer,Boolean,Double,Float,Long,Serializable)
     */
    public void startActivityWithObjectData(Context context, Class clazz, Map<String, Object> data) {
        if (null != context && null != clazz) {
            clearIntent();
            mIntent.setClass(context, clazz);
            if (data != null) {
                for (Entry<String, Object> en : data.entrySet()) {
                    Object value = en.getValue();
                    if (value instanceof String) {
                        mIntent.putExtra(en.getKey(), en.getValue().toString());
                        continue;
                    }
                    if (value instanceof Integer) {
                        mIntent.putExtra(en.getKey(), (int) en.getValue());
                        continue;
                    }
                    if (value instanceof Boolean) {
                        mIntent.putExtra(en.getKey(), (boolean) en.getValue());
                        continue;
                    }
                    if (value instanceof Double) {
                        mIntent.putExtra(en.getKey(), (double) en.getValue());
                        continue;
                    }
                    if (value instanceof Float) {
                        mIntent.putExtra(en.getKey(), (float) en.getValue());
                        continue;
                    }
                    if (value instanceof Long) {
                        mIntent.putExtra(en.getKey(), (long) en.getValue());
                        continue;
                    }
                    if (value instanceof Serializable) {
                        mIntent.putExtra(en.getKey(), (Serializable) en.getValue());
                    } else {
                        Logger.e(TAG + "startActivityWithObjectData()", "传入的类型不符合规定");
                    }

                }
            } else {
                Logger.e(TAG + "startActivityWithObjectData()", "参数data的值传入为空");
            }
            context.startActivity(mIntent);
        } else {
            Logger.e(TAG + "startActivityWithObjectData()", "参数值传入为空");
        }
    }

    /**
     * 开启一个带请求标识的新的activity
     *
     * @param activity    activity
     * @param clazz       目标activity.class
     * @param requestCode 请求编码
     * @param data        string类型的数据
     *                    key:名称
     *                    value:Object类型值,支持的类型(String,Integer,Boolean,Double,Float,Long,Serializable)
     */
    public void startActivityWithObjectData(Activity activity, Class<?> clazz, Map<String, Object> data, int requestCode) {
        if (null != activity && null != clazz) {
            clearIntent();
            mIntent.setClass(activity, clazz);
            if (data != null) {
                for (Entry<String, Object> en : data.entrySet()) {
                    Object value = en.getValue();
                    if (value instanceof String) {
                        mIntent.putExtra(en.getKey(), en.getValue().toString());
                        continue;
                    }
                    if (value instanceof Integer) {
                        mIntent.putExtra(en.getKey(), (int) en.getValue());
                        continue;
                    }
                    if (value instanceof Boolean) {
                        mIntent.putExtra(en.getKey(), (boolean) en.getValue());
                        continue;
                    }
                    if (value instanceof Double) {
                        mIntent.putExtra(en.getKey(), (double) en.getValue());
                        continue;
                    }
                    if (value instanceof Float) {
                        mIntent.putExtra(en.getKey(), (float) en.getValue());
                        continue;
                    }
                    if (value instanceof Long) {
                        mIntent.putExtra(en.getKey(), (long) en.getValue());
                        continue;
                    }
                    if (value instanceof Serializable) {
                        mIntent.putExtra(en.getKey(), (Serializable) en.getValue());
                    } else {
                        Logger.e(TAG + "startActivityWithObjectData()", "带请求标识的方法传入的类型不符合规定");
                    }
                }
            } else {
                Logger.e(TAG + "startActivityWithObjectData()", "带请求标识的方法参数data的值传入为空");
            }
            activity.startActivityForResult(mIntent, requestCode);
        } else {
            Logger.e(TAG + "startActivityWithObjectData()", "带请求标识的方法参数值传入为空");
        }
    }

    /**
     * 通过Fragment开启一个带请求标识的新的activity
     *
     * @param fragment 片段
     * @param activity 当前的activity
     * @param clazz    目标activity.class
     * @param data     string类型的数据 key:名称 value:Object类型值
     */
    public void startFragmentWithObjectData(Fragment fragment, Activity activity, Class<?> clazz, Map<String, Object> data, int requestCode) {
        if (null != fragment && null != activity && null != clazz) {
            clearIntent();
            mIntent.setClass(activity, clazz);
            if (data != null) {
                for (Entry<String, Object> en : data.entrySet()) {
                    Object value = en.getValue();
                    if (value instanceof String) {
                        mIntent.putExtra(en.getKey(), en.getValue().toString());
                        continue;
                    }
                    if (value instanceof Integer) {
                        mIntent.putExtra(en.getKey(), (int) en.getValue());
                        continue;
                    }
                    if (value instanceof Boolean) {
                        mIntent.putExtra(en.getKey(), (boolean) en.getValue());
                        continue;
                    }
                    if (value instanceof Double) {
                        mIntent.putExtra(en.getKey(), (double) en.getValue());
                        continue;
                    }
                    if (value instanceof Float) {
                        mIntent.putExtra(en.getKey(), (float) en.getValue());
                        continue;
                    }
                    if (value instanceof Long) {
                        mIntent.putExtra(en.getKey(), (long) en.getValue());
                        continue;
                    }
                    if (value instanceof Serializable) {
                        mIntent.putExtra(en.getKey(), (Serializable) en.getValue());
                    } else {
                        Logger.e(TAG + "startFragmentWithObjectData()", "传入的类型不符合规定");
                    }
                }
            } else {
                Logger.e(TAG + "startFragmentWithObjectData()", "参数data的值传入为空");
            }
            fragment.startActivityForResult(mIntent, requestCode);
        } else {
            Logger.e(TAG + "startFragmentWithObjectData()", "参数值传入为空");
        }
    }

    /**
     * 开启一个带请求标识的新的activity
     *
     * @param activity 上下文
     * @param clazz    目标acitivity class
     * @param data     string类型的数据 key:名称 value:Integer类型值
     */
//    public void startActivityWithStringData(Activity activity, Class clazz, Map<String, String> data, int why) {
//        if (null != clazz) {
//            clearIntent();
//            mIntent.setClass(activity, clazz);
//            if (data != null) {
//                for (Entry<String, String> en : data.entrySet())
//                    mIntent.putExtra(en.getKey(), en.getValue());
//            }
//            activity.startActivityForResult(mIntent, why);
//        }
//    }

    /**
     * 开启一个新的activity
     *
     * @param context 上下文
     * @param clazz   目标acitivity class
     * @param data    Int类型的数据 key:名称 value:int类型值
     */
//    public void startActivityWithIntData(Context context, Class clazz, Map<String, Integer> data) {
//        if (null != clazz) {
//            clearIntent();
//            mIntent.setClass(context, clazz);
//            if (null != data) {
//                for (Entry<String, Integer> en : data.entrySet())
//                    mIntent.putExtra(en.getKey(), en.getValue());
//            }
//            context.startActivity(mIntent);
//        }
//    }

    /**
     * 开启一个新的activity
     *
     * @param context 上下文
     * @param clazz   目标acitivity class
     * @param data    string类型的数据 key:名称 value:布尔类型值
     */
//    public void startActivityWithBooleanData(Context context, Class clazz, Map<String, Boolean> data) {
//        if (null != clazz) {
//            clearIntent();
//            mIntent.setClass(context, clazz);
//            if (null != data) {
//                for (Entry<String, Boolean> en : data.entrySet())
//                    mIntent.putExtra(en.getKey(), en.getValue());
//            }
//            context.startActivity(mIntent);
//        }
//    }

    /**
     * 开启一个带标识的新activity，并携带对象类型的数据
     *
     * @param activity        activity对象
     * @param clazz           目标activity class
     * @param serializableObj 传输实现Serializable的对象
     * @param datas           intent putExtra 数据，只支持基本数据类型。
     * @param requestCode     传输标识
     */
    public void startActivityForBundleData(Activity activity, Class clazz, Serializable serializableObj, Map<String, Object> datas, int requestCode) {
        if (null != activity && clazz != null) {
            clearIntent();
            mIntent.setClass(activity, clazz);
            if (serializableObj != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bundleObj", serializableObj);
                mIntent.putExtra("entity", bundle);
            } else {
                Logger.e(TAG + "startActivityForBundleData()", "参数serializableObj的值传入为空");
            }
            if (datas != null) {
                for (Entry<String, Object> en : datas.entrySet()) {
                    Object value = en.getValue();
                    if (value instanceof String) {
                        mIntent.putExtra(en.getKey(), en.getValue().toString());
                        continue;
                    }
                    if (value instanceof Integer) {
                        mIntent.putExtra(en.getKey(), (int) en.getValue());
                        continue;
                    }
                    if (value instanceof Boolean) {
                        mIntent.putExtra(en.getKey(), (boolean) en.getValue());
                        continue;
                    }
                    if (value instanceof Double) {
                        mIntent.putExtra(en.getKey(), (double) en.getValue());
                        continue;
                    }
                    if (value instanceof Float) {
                        mIntent.putExtra(en.getKey(), (float) en.getValue());
                        continue;
                    }
                    if (value instanceof Long) {
                        mIntent.putExtra(en.getKey(), (long) en.getValue());
                    } else {
                        Logger.e(TAG + "startActivityForBundleData()", "传入的类型不符合规定");
                    }
                }
            } else {
                Logger.e(TAG + "startActivityForBundleData()", "参数datas的值传入为空");
            }
            activity.startActivityForResult(mIntent, requestCode);
        } else {
            Logger.e(TAG + "startActivityForBundleData()", "参数值传入为空");
        }

    }

    /**
     * 开启一个新的activity，并携带对象类型的数据
     *
     * @param context         上下文
     * @param clazz           目标activity class
     * @param serializableObj 传输实现Serializable的对象
     * @param datas           字符串数据
     */
    public void startActivityForData(Context context, Class clazz, Serializable serializableObj, Map<String, String> datas) {
        if (null != context && clazz != null) {
            clearIntent();
            mIntent.setClass(context, clazz);
            if (serializableObj != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bundleObj", serializableObj);
                mIntent.putExtra("entity", bundle);
            } else {
                Logger.e(TAG + "startActivityForData()", "参数serializableObj的值传入为空");
            }
            if (datas != null) {
                for (Entry<String, String> en : datas.entrySet()) {
                    mIntent.putExtra(en.getKey(), en.getValue());
                }
            } else {
                Logger.e(TAG + "startActivityForData()", "参数datas的值传入为空");
            }

            context.startActivity(mIntent);
        } else {
            Logger.e(TAG + "startActivityForData()", "参数值传入为空");
        }

    }

    /**
     * 开启一个新的activity，并携带对象类型的数据
     *
     * @param activity 当前页面
     * @param clazz    目标acitvity class
     * @param obj      被对象
     * @param datas    字符串数据
     * @param tag      请求标识
     */
//    public void startActivityForData(Activity activity, Class clazz, Serializable obj, Map<String, String> datas, int tag) {
//        if (clazz != null) {
//            clearIntent();
//            mIntent.setClass(activity, clazz);
//            if (datas != null) {
//                for (Entry<String, String> en : datas.entrySet())
//                    mIntent.putExtra(en.getKey(), en.getValue());
//            }
//            if (null != obj) {
//                mIntent.putExtra("obj", obj);
//            }
//            activity.startActivityForResult(mIntent, tag);
//        }
//
//    }

    /**
     * 开启一个新的activity，并携带集合的数据
     *
     * @param context          上下文
     * @param clazz            目标activity class
     * @param serializableList 实现Serializable的list集合数据
     */
    public void startActivityForBundleListObj(Context context, Class clazz, List<Serializable> serializableList) {
        if (null != context && clazz != null) {
            clearIntent();
            mIntent.setClass(context, clazz);
            if (serializableList != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bundleObj", (Serializable) serializableList);
                mIntent.putExtra("entity", bundle);
            } else {
                Logger.e(TAG + "startActivityForBundleListObj()", "参数list的值传入为空");
            }
            context.startActivity(mIntent);
        } else {
            Logger.e(TAG + "startActivityForBundleListObj()", "参数值传入为空");
        }

    }

    /**
     * 开启一个新的activity，并携带对象类型的数据
     *
     * @param context         上下文
     * @param clazz           目标acitvity class
     * @param serializableObj 实现Serializable被对象
     */
    public void startActivityForObj(Context context, Class clazz, Serializable serializableObj) {
        if (null != context && clazz != null) {
            clearIntent();
            mIntent.setClass(context, clazz);
            if (serializableObj != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bundleObj", serializableObj);
                mIntent.putExtra("entity", bundle);
            } else {
                Logger.e(TAG + "startActivityForObj()", "参数serializableObj的值传入为空");
            }
            context.startActivity(mIntent);
        } else {
            Logger.e(TAG + "startActivityForObj()", "参数值传入为空");
        }

    }

    /**
     * 开启一个带标识的activity，并携带对象类型的数据
     *
     * @param activity        activity
     * @param clazz           目标activity class
     * @param serializableObj 被对象
     * @param why             传输标识
     */
    public void startActivityForObjWhy(Activity activity, Class clazz, Serializable serializableObj, int why) {
        if (null != activity && clazz != null) {
            clearIntent();
            mIntent.setClass(activity, clazz);
            if (serializableObj != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bundleObj", serializableObj);
                mIntent.putExtra("entity", bundle);
            } else {
                Logger.e(TAG + "startActivityForObjWhy()", "参数serializableObj的值传入为空");
            }
            activity.startActivityForResult(mIntent, why);
        } else {
            Logger.e(TAG + "startActivityForObjWhy()", "参数值传入为空");
        }

    }

    /**
     * 开启一个新的activity，并携带对象类型的数据
     *
     * @param context 上下文
     * @param clazz   目标acitvity class
     * @param obj     被对象
     * @param str     字符串
     */
//    public void startActivityForObjAndString(Context context, Class clazz, Serializable obj, String str) {
//        if (clazz != null) {
//            clearIntent();
//            mIntent.setClass(context, clazz);
//
//            if (obj != null) {
//                Bundle bundle = new Bundle();
//                bundle.putString("strInfo", str);
//                bundle.putSerializable("bundleObj", obj);
//                mIntent.putExtra("entity", bundle);
//            }
//            context.startActivity(mIntent);
//        }
//
//    }

    /**
     * 清空掉intent里面存储的数据
     */
    private void clearIntent() {
        Bundle extras = mIntent.getExtras();
        if (null != extras && !extras.isEmpty())
            extras.clear();
    }
}
