package com.liqi.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 星座|生肖获取工具类
 * Created by LiQi on 2018/7/10.
 */

public class ZodiacUtil {
    private static final String[] zodiacArray = {"猴", "鸡", "狗", "猪", "鼠", "牛",
            "虎", "兔", "龙", "蛇", "马", "羊"};

    private static final String[] constellationArray = {"水瓶座", "双鱼座", "牡羊座",
            "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};

    private static final int[] constellationEdgeDay = {20, 19, 21, 21, 21, 22,
            23, 23, 23, 23, 22, 22};

    /**
     * 根据日期获取生肖
     *
     * @return
     */
    private static String date2Zodica(Calendar time) {
        return zodiacArray[time.get(Calendar.YEAR) % 12];
    }

    /**
     * 根据日期获取生肖
     *
     * @param timeValue 时间值
     * @param format    时间值格式
     * @return
     */
    public static String date2Zodica(String timeValue, String format) {
        if (TextUtils.isEmpty(format)) {
            format = "yyyy-MM-dd";
        }
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date;
        try {
            date = sdf.parse(timeValue);
            c.setTime(date);

            String zodica = date2Zodica(c);
            System.out.println("生肖：" + zodica);
            return zodica;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据日期获取生肖
     *
     * @param time 时间值(毫秒值)
     * @return
     */
    public static String date2Zodica(long time) {

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        String zodica = date2Zodica(c);
        System.out.println("long>>生肖：" + zodica);
        return zodica;

    }

    /**
     * 根据日期获取星座
     *
     * @param time
     * @return
     */
    private static String date2Constellation(Calendar time) {
        int month = time.get(Calendar.MONTH);
        int day = time.get(Calendar.DAY_OF_MONTH);
        if (day < constellationEdgeDay[month]) {
            month = month - 1;
        }
        if (month >= 0) {
            return constellationArray[month];
        }
        // default to return 魔羯
        return constellationArray[11];
    }

    /**
     * 根据日期获取星座
     *
     * @param timeValue 时间值
     * @param format    时间值格式
     * @return
     */
    public static String date2Constellation(String timeValue, String format) {
        if (TextUtils.isEmpty(format)) {
            format = "yyyy-MM-dd";
        }
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date;
        try {
            date = sdf.parse(timeValue);
            c.setTime(date);

            String constellation = date2Constellation(c);
            System.out.println("星座：" + constellation);
            return constellation;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据日期获取星座
     *
     * @param time 时间值（毫秒值）
     * @return
     */
    public static String date2Constellation(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        String constellation = date2Constellation(c);
        System.out.println("long>>星座：" + constellation);
        return constellation;
    }
}
