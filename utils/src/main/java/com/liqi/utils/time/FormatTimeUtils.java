package com.liqi.utils.time;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.util.Calendar;

/**
 * 时间转变处理工具
 * User: Liqi
 * Date: 9/2/18
 * Time: 6:07 PM
 * Desc: TimeUtils
 */
public class FormatTimeUtils {

    /**
     * 以毫秒为单位的时间解析成字符串格式:hh:mm:ss
     *
     * @param duration 需要解析的时间值（单位：毫秒）
     */
    @SuppressLint("DefaultLocale")
    public static String formatDuration(int duration) {
        duration /= 1000; // milliseconds into seconds
        int minute = duration / 60;
        int hour = minute / 60;
        minute %= 60;
        int second = duration % 60;
        if (hour != 0)
            return String.format("%2d:%02d:%02d", hour, minute, second);
        else
            return String.format("%02d:%02d", minute, second);
    }

    /**
     * 计算出毫秒转变成秒，分，时，天。按照最大单位进阶数值取值（秒=60，转变值>60就求分单位，小于60就取秒单位。）
     *<p>
     *     返回“当”就代表时间轴为当前
     *</p>
     * @param millisecond 毫秒值
     * @return 加单位格式化好的数值
     */
    public static String millisecondTurnValue(long millisecond) {
        String timeTurnValue;
        //转换秒
        millisecond /= 1000;
        //大于60秒
        if (millisecond > 60) {
            //转换分
            millisecond /= 60;
            //大于60分
            if (millisecond > 60) {
                //转换时
                millisecond /= 60;
                //大于24小时
                if (millisecond > 24) {
                    millisecond /= 24;
                    timeTurnValue = millisecond + "天";
                } else {
                    timeTurnValue = millisecond + "小时";
                }
            } else {
                timeTurnValue = millisecond + "分钟";
            }
        } else {
            if (millisecond <= 0) {
                timeTurnValue = "当";
            } else {
                timeTurnValue = millisecond + "秒";
            }
        }
        return timeTurnValue;
    }

    /**
     * 根据年月日计算年龄
     * <p>
     * 时间格式必须为：yyyy-MM-dd
     * </p>
     *
     * @param birthTimeString 时间值
     */
    public static int getAgeFromBirthTime(String birthTimeString) {
        if (!TextUtils.isEmpty(birthTimeString)) {
            // 先截取到字符串中的年、月、日
            String strs[] = birthTimeString.trim().split("-");
            int selectYear = Integer.parseInt(strs[0]);
            int selectMonth = Integer.parseInt(strs[1]);
            int selectDay = Integer.parseInt(strs[2]);
            // 得到当前时间的年、月、日
            Calendar cal = Calendar.getInstance();
            int yearNow = cal.get(Calendar.YEAR);
            int monthNow = cal.get(Calendar.MONTH) + 1;
            int dayNow = cal.get(Calendar.DATE);

            // 用当前年月日减去生日年月日
            int yearMinus = yearNow - selectYear;
            int monthMinus = monthNow - selectMonth;
            int dayMinus = dayNow - selectDay;

            int age = yearMinus; // 先大致赋值
            if (yearMinus < 0) { // 选了未来的年份
                age = 0;
            } else {
                if ((monthMinus > 0) || (monthMinus == 0 && dayMinus >= 0)) {
                    age += 1;
                }
            }
            return age > 0 ? age - 1 : age;
        }
        return 0;
    }

    /**
     * 根据时间戳计算年龄
     *
     * @param birthTimeLong 时间戳
     */
    public static int getAgeFromBirthTime(long birthTimeLong) {
        String birthTimeString = TimeUtil.longgetStringDate(birthTimeLong, "yyyy-MM-dd");
        return getAgeFromBirthTime(birthTimeString);
    }
}
