package com.liqi.utils;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;

/**
 * 单位转换帮助类
 *
 * @author LiQi
 */
public class FDUnitUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context  上下文
     * @param dipValue dp值
     * @return px值
     */
    public static int dpToPx(Context context, float dipValue) {
        if (context != null) {

            if ((float) LayoutParams.MATCH_PARENT == dipValue) {
                return LayoutParams.MATCH_PARENT;
            }

            if ((float) LayoutParams.WRAP_CONTENT == dipValue) {
                return LayoutParams.WRAP_CONTENT;
            }

            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dipValue * scale + 0.5f);
        }
        return (int) dipValue;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context 上下文
     * @param pxValue PX值
     * @return dp值
     */
    public static int pxToDip(Context context, float pxValue) {
        if (null != context) {
            if ((float) LayoutParams.MATCH_PARENT == pxValue) {
                return LayoutParams.MATCH_PARENT;
            }

            if ((float) LayoutParams.WRAP_CONTENT == pxValue) {
                return LayoutParams.WRAP_CONTENT;
            }
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }
        return (int) pxValue;
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue px值
     * @param context 上下文
     * @return sp值
     */
    public static int pxToSp(float pxValue, Context context) {
        return (int) (pxValue / context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }


    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue sp值
     * @param context 上下文
     * @return px值
     */
    public static int spToPx(float spValue, Context context) {
        return (int) (spValue * context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }

}
