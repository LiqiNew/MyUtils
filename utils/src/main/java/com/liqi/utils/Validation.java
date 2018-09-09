package com.liqi.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串验证工具类（网站域名 联系电话 手机号码 邮政编码 邮箱 身份证等）
 *
 * @author LiQi
 */
public class Validation {

    /**
     * 把Double类型只保留小数点后面几位数
     *
     * @param val Double数值
     * @param len 保留位数
     * @return 转换成功后的值
     */
    public static String jeShow(Double val, int len) {
        try {
            String subzero = "00000000000000".substring(0, len);
            DecimalFormat df = new DecimalFormat("######0." + subzero);
            return df.format(val);
        } catch (Exception e) {
            return "0.00";
        }
    }

    /**
     * double类型转换int类型 （去小数点准确转换）
     *<p>
     *     样例：要转换的值：1.0。转换之后的值：10
     *</p>
     * @param doubleData double类型数据
     * @return 转换成功后的值
     */
    public static int doubleToInt(double doubleData) {
        int doubleDataTag = 0;
        if (doubleData > 0.0) {
            String verString = String.valueOf(doubleData);
            String[] split = verString.split("\\.");
            if (split.length > 1) {
                int intTwo = Integer.parseInt(split[1]);
                if (intTwo >= 0) {
                    doubleDataTag = Integer.parseInt(split[0] + split[1]);
                }else {
                    doubleDataTag = Integer.parseInt(split[0]);
                }
            } else {
                doubleDataTag = Integer.parseInt(split[0]);
            }
        }
        return doubleDataTag;
    }

    /**
     * 正则验证方法
     *
     * @param regexstr 正则规则
     * @param str 验证内容
     * @return true正确，false错误。
     */
    public static boolean match(String regexstr, String str) {
        Pattern regex = Pattern.compile(regexstr, Pattern.CASE_INSENSITIVE
                | Pattern.DOTALL);
        Matcher matcher = regex.matcher(str);
        return matcher.matches();
    }

    /**
     * 解析短信推送内容
     *
     * @param data 短信推送内容
     * @return 解析后的数据
     */
    public static Map<String, Object> getParameterMap(String data) {
        Map<String, Object> map = null;
        if (data != null) {
            map = new HashMap<>();
            String[] params = data.split("&");
            for (int i = 0; i < params.length; i++) {
                int idx = params[i].indexOf("=");
                if (idx >= 0) {
                    map.put(params[i].substring(0, idx),
                            params[i].substring(idx + 1));
                }
            }
        }
        return map;
    }

    /**
     *
     *
     * @param len
     * @return
     */
    /**检测字符串长度是否符合指定长度
     *
     * @param len 字符串长度
     * @param minLen 最小限制长度
     * @param maxLen 最大限制长度
     * @return true符合，false是不符合。
     */
    public static boolean checkingUserName(int len,int minLen,int maxLen) {
        boolean isValid;
        if (minLen < len && len < maxLen) {
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;
    }

    /**
     * 检测字符串是否为中文字符
     *
     * @param str 字符串内容
     * @return true是符合，false为不符合。
     */
    public static boolean isChinesrChar(String str) {
        if (str.length() < str.getBytes().length) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 邮箱验证
     *
     * @param mail 邮箱
     * @return true是符合，false为不符合。
     */
    public static boolean matchMail(String mail) {
        String mailregex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return match(mailregex, mail);
    }

    /**
     * 判断邮编
     *
     * @param zipString 邮编
     * @return true是符合，false为不符合。
     */
    public static boolean isZipNO(String zipString) {
        String str = "^[1-9][0-9]{5}$";
        return match(str, zipString);
    }

    /**
     * 手机验证
     *
     * @param mobile 手机
     * @return true是符合，false为不符合。
     */
    public static boolean matchMobile(String mobile) {
        String mobileregex = "^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9]|16[0-9])\\d{8}$";
        return match(mobileregex, mobile);
    }

    /**
     * 电话验证
     *
     * @param Tel 电话
     * @return true是符合，false为不符合。
     */
    public static boolean matchTel(String Tel) {
        String telregex = "(^[0-9]{3,4}-[0-9]{7,8}-[0-9]{3,4}$)|(^[0-9]{3,4}-[0-9]{7,8}$)|(^[0-9]{7,8}-[0-9]{3,4}$)|(^[0-9]{7,12}$)";
        return match(telregex, Tel);
    }

    /**
     * 域名验证
     *
     * @param webdomain 域名
     * @return true是符合，false为不符合。
     */
    public static boolean webdomain(String webdomain) {
        String webdomainregex = "http://([^/]+)/*";
        return match(webdomainregex, webdomain);
    }

    /**
     * 邮政编号验证
     *
     * @param zipcode 邮政编号
     * @return true是符合，false为不符合。
     */
    public static boolean zipCode(String zipcode) {
        String zipcoderegex = "^[0-9]{6}$";
        return match(zipcoderegex, zipcode);
    }

    /**
     * 判断字符串是否为空字符串。
     *
     * @param aString 字符串内容
     * @return true是为空，false不为空。
     */
    public static boolean isEmpty(String aString) {
            return TextUtils.isEmpty(aString);
    }

    /**
     * 判断字符串是否是整数
     *
     * @param aString 字符串内容
     * @return true是整数，false不是。
     */
    public static boolean isInteger(String aString) {
        try {
            Integer.parseInt(aString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     *
     * @param value 字符串内容
     * @return true是浮点数，false不是。
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 格式化手机号码，手机号码中间*显示。
     *
     * @param aPhoneNum 手机号码
     * @return 格式化好的手机号码内容
     */
    public static String formatPhoneNum(String aPhoneNum) {
        String first = aPhoneNum.substring(0, 3);
        String end = aPhoneNum.substring(7, 11);
        String phoneNumber = first + "****" + end;
        return phoneNumber;
    }

    /**
     * 检查字符串是否为纯数字
     *
     * @param str 字符串内容
     * @return true是纯数字，false不是。
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查字符串是否为纯字母
     *
     * @param s 字符串内容
     * @return true是纯字母，false不是。
     */
    public static boolean isLetter(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!(s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')
                    && !(s.charAt(i) >= 'a' && s.charAt(i) <= 'z')) {
                return false;
            }
        }
        return true;
    }

    /**
     * 去除字符串中空格
     *
     * @param aString 字符串内容
     * @return 去除空格的字符串内容
     */
    public static String clearSpaces(String aString) {
        StringTokenizer aStringTok = new StringTokenizer(aString, " ", false);
        String aResult = "";
        while (aStringTok.hasMoreElements()) {
            aResult += aStringTok.nextElement();
        }
        return aResult;
    }

    /**
     * 判断是否包含特殊字符，
     *
     * @param conten 需要判断的内容
     * @return 包含了为true，没有包含为false
     */
    public static boolean containSpecialCharacter(String conten) {
        if (null != conten) {
            if (!"".equals(conten)) {
                if (conten.replaceAll("[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 转换成UTF-8编码
     *
     * @param str 转换内容
     * @return 转换成功的内容
     * @throws UnsupportedEncodingException
     */
    public static String formatToUTF8(String str) {
        String result = "";
        try {
            if (isEmpty(str)) {
                return result;
            }
            result = new String(str.getBytes("ISO-8859-1"), "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 计算toNumber在总数量inNumber中的百分比。
     *
     * @param toNumber  要求出的百分比数量
     * @param numberSum 数量总和
     * @return 拼接好的百分比字符串
     */
    public static String shiftPercent(double toNumber, double numberSum) {
        if (numberSum > 0.0) {
            double percent = (toNumber / numberSum) * 100;
            return jeShow(percent, 2) + "%";
        } else
            return "100.00%";
    }
}
