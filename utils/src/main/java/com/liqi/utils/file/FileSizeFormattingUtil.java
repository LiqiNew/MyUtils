package com.liqi.utils.file;

import java.text.DecimalFormat;

/**
 * 文件大小格式化工具
 * Created by LiQi on 2018/10/19.
 */

public class FileSizeFormattingUtil {
    /**
     * 转换文件大小
     *
     * @param fileSize  文件大小
     * @param isInteger 是否返回取整的单位
     * @return 返回格式化的单位值。逢单位最大值进阶
     * <p>
     * 单位：B,KB,MG,G
     * </P>
     */
    public static String formetFileSize(long fileSize, boolean isInteger) {
        String fileSizeString;
        String wrongSize = "0B";
        if (fileSize <= 0) {
            return wrongSize;
        }
        DecimalFormat df = isInteger ? new DecimalFormat("#0") : new DecimalFormat("#0.##");
        if (fileSize < 1024) {
            fileSizeString = df.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            fileSizeString = df.format((double) fileSize / 1024) + "KB";
        } else if (fileSize < 1073741824) {
            fileSizeString = df.format((double) fileSize / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileSize / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小
     *
     * @param fileSize 文件大小
     * @param df       格式化对象
     * @return 返回格式化的单位值。逢单位最大值进阶
     * <p>
     * 单位：B,KB,MG,G
     * </P>
     */
    public static String formetFileSize(long fileSize, DecimalFormat df) {
        String fileSizeString;
        String wrongSize = "0B";
        if (fileSize <= 0) {
            return wrongSize;
        }
        df = null == df ? new DecimalFormat("#0") : df;
        if (fileSize < 1024) {
            fileSizeString = df.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            fileSizeString = df.format((double) fileSize / 1024) + "KB";
        } else if (fileSize < 1073741824) {
            fileSizeString = df.format((double) fileSize / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileSize / 1073741824) + "G";
        }
        return fileSizeString;
    }
}
