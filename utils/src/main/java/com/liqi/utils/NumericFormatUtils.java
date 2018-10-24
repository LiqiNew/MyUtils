package com.liqi.utils;

/**
 * 数字格式化工具
 * Created by LiQi on 2018/9/20.
 */

public class NumericFormatUtils {
    /**
     * 数值升值格式化。逢单位"万"格式化
     *
     * @param number   要格式化的数
     * @param typeEnum {@link NumericFormatTypeEnum}
     * @return 单位"万"格式化数据内容
     */
    public static String appreciationFormat(long number, NumericFormatTypeEnum typeEnum) {
        String result = number + "";
        int miriade = 10000;
        if (number >= miriade) {
            String type;
            if (typeEnum == NumericFormatTypeEnum.CHINESE) {
                type = "万";
            } else {
                type = "w";
            }
            result = number / miriade + type;
        }
        return result;
    }

    /**
     * 数值升值格式化。逢单位"千"或以上单位格式化
     *
     * @param number   要格式化的数
     * @param typeEnum {@link NumericFormatTypeEnum}
     * @return 逢"千" 和 "万"格式化内容
     */
    public static String appreciationThousandFormat(long number, NumericFormatTypeEnum typeEnum) {
        int thousand = 1000;
        String result = number + "";
        if (number >= thousand) {
            String type;

            int miriade = 10000;
            if (number >= miriade) {
                if (typeEnum == NumericFormatTypeEnum.CHINESE) {
                    type = "万";
                } else {
                    type = "w";
                }
                result = number / miriade + type;
            } else {
                if (typeEnum == NumericFormatTypeEnum.CHINESE) {
                    type = "千";
                } else {
                    type = "k";
                }

                result = number / thousand + type;
            }
        }
        return result;
    }

    /**
     * 转换类型枚举
     */
    public enum NumericFormatTypeEnum {
        //中文
        CHINESE,
        //英文
        ENGLISH
    }
}
