package com.liqi.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import com.liqi.utils.file.FileSizeFormattingUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 系统存储空间获取工具对象
 * Created by LiQi on 2018/10/19.
 */

public class SystemMemorySpaceGetUtil {

    /**
     * SDCARD是否存
     */
    private static boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

//    /**
//     * 获取手机里面全部剩余存储空间值
//     *
//     * @return 单位为字节
//     */
//    public static long getAllAvailableInternalMemorySize() {
//        return getAvailableInternalMemorySize() + getAvailableExternalMemorySize();
//    }
//
//    /**
//     * 获取手机里面全部剩余存储空间,返回的数据按单位B、K、M、G进制格式化。
//     *
//     * @param isInteger 是否返回取整的单位
//     * @return 格式化好的数据
//     */
//    public static String getAllAvailableInternalMemorySize(boolean isInteger) {
//        return formatFileSize(getAllAvailableInternalMemorySize(), isInteger);
//    }
//
//    /**
//     * 获取手机里面全部存储空间值
//     *
//     * @return
//     */
//    public static long getAllTotalInternalMemorySize() {
//        return getTotalInternalMemorySize() + getTotalExternalMemorySize();
//    }
//
//    /**
//     * 获取手机里面全部存储空间值,返回的数据按单位B、K、M、G进制格式化。
//     *
//     * @param isInteger 是否返回取整的单位
//     * @return 格式化好的数据
//     */
//    public static String getAllTotalInternalMemorySize(boolean isInteger) {
//        return formatFileSize(getAllTotalInternalMemorySize(), isInteger);
//    }

    /**
     * 获取手机内部剩余存储空间
     *
     * @return
     */
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
        } else {
            blockSize = stat.getBlockSize();
        }

        long availableBlocks;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            availableBlocks = stat.getAvailableBlocks();
        }
        return availableBlocks * blockSize;
    }

    /**
     * 获取手机内部剩余存储空间,返回的数据按单位B、K、M、G进制格式化。
     *
     * @param isInteger 是否返回取整的单位
     * @return 格式化好的数据
     */
    public static String getAvailableInternalMemorySize(boolean isInteger) {
        return FileSizeFormattingUtil.formetFileSize(getAvailableInternalMemorySize(), isInteger);
    }

    /**
     * 获取手机内部总的存储空间
     *
     * @return
     */
    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
        } else {
            blockSize = stat.getBlockSize();
        }


        long totalBlocks;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            totalBlocks = stat.getBlockCountLong();
        } else {
            totalBlocks = stat.getBlockCount();
        }
        return totalBlocks * blockSize;
    }

    /**
     * 获取手机内部总的存储空间,返回的数据按单位B、K、M、G进制格式化。
     *
     * @param isInteger 是否返回取整的单位
     * @return 格式化好的数据
     */
    public static String getTotalInternalMemorySize(boolean isInteger) {
        return FileSizeFormattingUtil.formetFileSize(getTotalInternalMemorySize(), isInteger);
    }

    /**
     * 获取SDCARD剩余存储空间
     *
     * @return
     */
    public static long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = stat.getBlockSizeLong();
            } else {
                blockSize = stat.getBlockSize();
            }

            long availableBlocks;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                availableBlocks = stat.getAvailableBlocksLong();
            } else {
                availableBlocks = stat.getAvailableBlocks();
            }

            return availableBlocks * blockSize;
        } else {
            return 0;
        }
    }

    /**
     * 获取SDCARD剩余存储空间,返回的数据按单位B、K、M、G进制格式化。
     *
     * @param isInteger 是否返回取整的单位
     * @return 格式化好的数据
     */
    public static String getAvailableExternalMemorySize(boolean isInteger) {
        return FileSizeFormattingUtil.formetFileSize(getAvailableExternalMemorySize(), isInteger);
    }

    /**
     * 获取SDCARD总的存储空间
     *
     * @return
     */
    public static long getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = stat.getBlockSizeLong();
            } else {
                blockSize = stat.getBlockSize();
            }

            long totalBlocks;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                totalBlocks = stat.getBlockCountLong();
            } else {
                totalBlocks = stat.getBlockCount();
            }
            return totalBlocks * blockSize;
        } else {
            return 0;
        }
    }

    /**
     * 获取SDCARD总的存储空间,返回的数据按单位B、K、M、G进制格式化。
     *
     * @param isInteger 是否返回取整的单位
     * @return 格式化好的数据
     */
    public static String getTotalExternalMemorySize(boolean isInteger) {
        return FileSizeFormattingUtil.formetFileSize(getTotalExternalMemorySize(), isInteger);
    }

    /**
     * 获取系统总内存
     *
     * @param context 可传入应用程序上下文。
     * @return 总内存大单位为B。
     */
    public static long getTotalMemorySize(Context context) {
        if (null != context) {
            String dir = "/proc/meminfo";
            try {
                FileReader fr = new FileReader(dir);
                BufferedReader br = new BufferedReader(fr, 2048);
                String memoryLine = br.readLine();
                String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
                br.close();
                return Integer.parseInt(subMemoryLine.replaceAll("\\D+", "")) * 1024l;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 获取系统总内存,返回的数据按单位B、K、M、G进制格式化。
     *
     * @param context   可传入应用程序上下文。
     * @param isInteger 是否返回取整的单位
     * @return 格式化好的数据
     */
    public static String getTotalMemorySize(Context context, boolean isInteger) {
        return FileSizeFormattingUtil.formetFileSize(getTotalMemorySize(context), isInteger);
    }

    /**
     * 获取当前可用内存，返回数据以字节为单位。
     *
     * @param context 可传入应用程序上下文。
     * @return 当前可用内存单位为B。
     */
    public static long getAvailableMemory(Context context) {
        if (null != context) {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(memoryInfo);
            return memoryInfo.availMem;
        }
        return 0;
    }

    /**
     * 获取当前可用内存，返回的数据按单位B、K、M、G进制格式化。
     *
     * @param context   可传入应用程序上下文。
     * @param isInteger 是否返回取整的单位
     * @return 格式化好的数据
     */
    public static String getAvailableMemory(Context context, boolean isInteger) {
        return FileSizeFormattingUtil.formetFileSize(getAvailableMemory(context), isInteger);
    }
}
