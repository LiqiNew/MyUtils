package com.liqi.utils.zip;

import android.annotation.TargetApi;
import android.content.Context;
import android.text.TextUtils;

import com.liqi.Logger;
import com.liqi.utils.file.StaticFileUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 压缩解压文件工具
 *
 * @author LiQi
 */
public class ZipUtils {
    private static final String TAG = "ZipUtils";
    private static final int BUFF_SIZE = 1024 * 1024; // 1M Byte

    /**
     * 无注释批量压缩文件（夹）。默认缓冲大小1M
     *
     * @param context          上下文。不能为null
     * @param resFilePathList  要压缩的文件路径或者文件夹路径列表。不能为null和空
     * @param zipCataloguePath 生成的压缩文件目录路径。样例:xxx/xxx。不能为null和空
     *                         <p>
     *                         已经判断是否有SD卡。如果有就拼接SD卡路径，没有就拼接手机内存路径
     *                         </p>
     * @param zipName          zip压缩文件名字。扩展名内部已定义。不能为null和空
     */
    public static void zipFiles(Context context, Collection<String> resFilePathList, String zipCataloguePath, String zipName) {
        zipFiles(context, resFilePathList, zipCataloguePath, zipName, null, BUFF_SIZE);
    }

    /**
     * 无注释批量压缩文件（夹）。需要指定缓冲大小
     *
     * @param context          上下文。不能为null
     * @param resFilePathList  要压缩的文件路径或者文件夹路径列表。不能为null和空
     * @param zipCataloguePath 生成的压缩文件目录路径。样例:xxx/xxx。不能为null和空
     *                         <p>
     *                         已经判断是否有SD卡。如果有就拼接SD卡路径，没有就拼接手机内存路径
     *                         </p>
     * @param zipName          zip压缩文件名字。扩展名内部已定义。不能为null和空
     * @param buffSize         压缩文件缓存大小
     */
    public static void zipFiles(Context context, Collection<String> resFilePathList, String zipCataloguePath, String zipName, int buffSize) {
        zipFiles(context, resFilePathList, zipCataloguePath, zipName, null, buffSize);
    }

    /**
     * 有注释批量压缩文件（夹）。默认缓冲大小1M
     *
     * @param context          上下文。不能为null
     * @param resFilePathList  要压缩的文件路径或者文件夹路径列表。不能为null和空
     * @param zipCataloguePath 生成的压缩文件目录路径。样例:xxx/xxx。不能为null和空
     *                         <p>
     *                         已经判断是否有SD卡。如果有就拼接SD卡路径，没有就拼接手机内存路径
     *                         </p>
     * @param zipName          zip压缩文件名字。扩展名内部已定义。不能为null和空
     * @param comment          压缩文件注释
     */
    public static void zipFiles(Context context, Collection<String> resFilePathList, String zipCataloguePath, String zipName, String comment) {
        zipFiles(context, resFilePathList, zipCataloguePath, zipName, comment, BUFF_SIZE);
    }

    /**
     * 有注释压缩文件（夹）。需要指定缓冲大小
     *
     * @param context          上下文。不能为null
     * @param resFilePath      要压缩的文件路径或者文件夹路径。不能为null和空
     * @param zipCataloguePath 生成的压缩文件目录路径。样例:xxx/xxx。不能为null和空
     *                         <p>
     *                         已经判断是否有SD卡。如果有就拼接SD卡路径，没有就拼接手机内存路径
     *                         </p>
     * @param zipName          zip压缩文件名字。扩展名内部已定义。不能为null和空
     * @param comment          压缩文件注释
     * @param buffSize         压缩文件缓存大小
     */
    public static void zipFiles(Context context, String resFilePath, String zipCataloguePath, String zipName, String comment, int buffSize) {
        if (!TextUtils.isEmpty(resFilePath)) {
            ArrayList<String> resFilePathList = new ArrayList<>();
            resFilePathList.add(resFilePath);
            zipFiles(context, resFilePathList, zipCataloguePath, zipName, comment, buffSize);
        } else {
            Logger.e(TAG, "zipFiles()方法中压缩的文件路径或者文件夹路径不能为null和空");
        }
    }
    /**
     * 有注释压缩文件（夹）。默认缓冲大小1M
     *
     * @param context          上下文。不能为null
     * @param resFilePath      要压缩的文件路径或者文件夹路径。不能为null和空
     * @param zipCataloguePath 生成的压缩文件目录路径。样例:xxx/xxx。不能为null和空
     *                         <p>
     *                         已经判断是否有SD卡。如果有就拼接SD卡路径，没有就拼接手机内存路径
     *                         </p>
     * @param zipName          zip压缩文件名字。扩展名内部已定义。不能为null和空
     * @param comment          压缩文件注释
     */
    public static void zipFiles(Context context, String resFilePath, String zipCataloguePath, String zipName, String comment) {
        if (!TextUtils.isEmpty(resFilePath)) {
            ArrayList<String> resFilePathList = new ArrayList<>();
            resFilePathList.add(resFilePath);
            zipFiles(context, resFilePathList, zipCataloguePath, zipName, comment, BUFF_SIZE);
        } else {
            Logger.e(TAG, "zipFiles()方法中压缩的文件路径或者文件夹路径不能为null和空");
        }
    }
    /**
     * 无注释压缩文件（夹）。需要指定缓冲大小
     *
     * @param context          上下文。不能为null
     * @param resFilePath      要压缩的文件路径或者文件夹路径。不能为null和空
     * @param zipCataloguePath 生成的压缩文件目录路径。样例:xxx/xxx。不能为null和空
     *                         <p>
     *                         已经判断是否有SD卡。如果有就拼接SD卡路径，没有就拼接手机内存路径
     *                         </p>
     * @param zipName          zip压缩文件名字。扩展名内部已定义。不能为null和空
     * @param buffSize         压缩文件缓存大小
     */
    public static void zipFiles(Context context, String resFilePath, String zipCataloguePath, String zipName,int buffSize) {
        if (!TextUtils.isEmpty(resFilePath)) {
            ArrayList<String> resFilePathList = new ArrayList<>();
            resFilePathList.add(resFilePath);
            zipFiles(context, resFilePathList, zipCataloguePath, zipName, null, buffSize);
        } else {
            Logger.e(TAG, "zipFiles()方法中压缩的文件路径或者文件夹路径不能为null和空");
        }
    }
    /**
     * 无注释压缩文件（夹）。默认缓冲大小1M
     *
     * @param context          上下文。不能为null
     * @param resFilePath      要压缩的文件路径或者文件夹路径。不能为null和空
     * @param zipCataloguePath 生成的压缩文件目录路径。样例:xxx/xxx。不能为null和空
     *                         <p>
     *                         已经判断是否有SD卡。如果有就拼接SD卡路径，没有就拼接手机内存路径
     *                         </p>
     * @param zipName          zip压缩文件名字。扩展名内部已定义。不能为null和空
     */
    public static void zipFiles(Context context, String resFilePath, String zipCataloguePath, String zipName) {
        if (!TextUtils.isEmpty(resFilePath)) {
            ArrayList<String> resFilePathList = new ArrayList<>();
            resFilePathList.add(resFilePath);
            zipFiles(context, resFilePathList, zipCataloguePath, zipName, null, BUFF_SIZE);
        } else {
            Logger.e(TAG, "zipFiles()方法中压缩的文件路径或者文件夹路径不能为null和空");
        }
    }
    /**
     * 批量压缩文件（夹）
     *
     * @param context          上下文。不能为null
     * @param resFilePathList  要压缩的文件路径或者文件夹路径列表。不能为null和空
     * @param zipCataloguePath 生成的压缩文件目录路径。样例:xxx/xxx。。不能为null和空
     *                         <p>
     *                         已经判断是否有SD卡。如果有就拼接SD卡路径，没有就拼接手机内存路径
     *                         </p>
     * @param zipName          zip压缩文件名字。扩展名内部已定义。不能为null和空
     * @param comment          压缩文件注释
     * @param buffSize         压缩文件缓存大小
     */
    public static void zipFiles(Context context, Collection<String> resFilePathList, String zipCataloguePath, String zipName,
                                String comment, int buffSize) {
        if (null != context && null != resFilePathList && !resFilePathList.isEmpty() && !TextUtils.isEmpty(zipCataloguePath) && !TextUtils.isEmpty(zipName)) {
            String path = StaticFileUtils.getPath(context, zipCataloguePath);
            //目录路径文件
            File file = new File(path);
            //目录不存在，重创建目录。
            if (!file.exists() || !file.isDirectory()) {
                file.mkdirs();
            }
            //文件写入路径文件
            file = new File(path, zipName + ".zip");
            ZipOutputStream zipOut = null;
            try {
                zipOut = new ZipOutputStream(new BufferedOutputStream(
                        new FileOutputStream(file), buffSize));
                for (String resFilePath : resFilePathList) {
                    if (!TextUtils.isEmpty(resFilePath)) {
                        zipFile(new File(resFilePath), zipOut, "");
                    }
                }
                //有压缩注释才设置
                if (!TextUtils.isEmpty(comment)) {
                    zipOut.setComment(comment);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != zipOut) {
                        zipOut.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Logger.e(TAG, "zipFiles()方法中有必须传值的参数未传入值");
        }
    }

    /**
     * 批量解压一个zip文件
     * 解压文件缓存大小 1M
     *
     * @param context         上下文。不能为null
     * @param zipFilePaths    压缩文件路径集合。需要带文件扩展名，不能为null和空
     * @param unZipFolderPath 解压写出文件目录。样例:xxx/xxx。不能为null和空
     *                        <p>
     *                        已经判断是否有SD卡。如果有就拼接SD卡路径，没有就拼接手机内存路径
     *                        </p>
     */
    public static void unZipFiles(Context context, Collection<String> zipFilePaths, String unZipFolderPath) {
        if (null != zipFilePaths && !zipFilePaths.isEmpty() && null != context && !TextUtils.isEmpty(unZipFolderPath)) {
            for (String filePath : zipFilePaths) {
                if (!TextUtils.isEmpty(filePath)) {
                    unZipFile(context, filePath, unZipFolderPath, BUFF_SIZE);
                } else {
                    Logger.e(TAG, "unZipFiles()方法中压缩文件路径集合的值为空");
                }
            }
        } else {
            Logger.e(TAG, "unZipFiles()方法中有必须传值的参数未传入值");
        }
    }

    /**
     * 批量解压一个zip文件
     *
     * @param context         上下文。不能为null
     * @param zipFilePaths    压缩文件路径集合。需要带文件扩展名，不能为null和空
     * @param unZipFolderPath 解压写出文件目录。样例:xxx/xxx。不能为null和空
     *                        <p>
     *                        已经判断是否有SD卡。如果有就拼接SD卡路径，没有就拼接手机内存路径
     *                        </p>
     * @param buffSize        解压文件缓存大小
     */
    public static void unZipFiles(Context context, Collection<String> zipFilePaths, String unZipFolderPath, int buffSize) {
        if (null != zipFilePaths && !zipFilePaths.isEmpty() && null != context && !TextUtils.isEmpty(unZipFolderPath)) {
            for (String filePath : zipFilePaths) {
                if (!TextUtils.isEmpty(filePath)) {
                    unZipFile(context, filePath, unZipFolderPath, buffSize);
                } else {
                    Logger.e(TAG, "unZipFiles()方法中压缩文件路径集合的值为空");
                }
            }
        } else {
            Logger.e(TAG, "unZipFiles()方法中有必须传值的参数未传入值");
        }
    }

    /**
     * 解压一个zip文件，解压文件缓存指定大小 1M
     *
     * @param context         上下文。不能为null
     * @param zipFilePath     压缩文件路径。需要带文件扩展名，不能为null和空
     * @param unZipFolderPath 解压写出文件目录。样例:xxx/xxx。不能为null和空
     *                        <p>
     *                        已经判断是否有SD卡。如果有就拼接SD卡路径，没有就拼接手机内存路径
     *                        </p>
     */
    public static void unZipFile(Context context, String zipFilePath, String unZipFolderPath) {
        unZipFile(context, zipFilePath, unZipFolderPath, BUFF_SIZE);
    }

    /**
     * 解压一个zip文件
     *
     * @param context         上下文。不能为null
     * @param zipFilePath     压缩文件路径。需要带文件扩展名，不能为null和空
     * @param unZipFolderPath 解压写出文件目录。样例:xxx/xxx。不能为null和空
     *                        <p>
     *                        已经判断是否有SD卡。如果有就拼接SD卡路径，没有就拼接手机内存路径
     *                        </p>
     * @param buffSize        解压文件缓存大小
     */
    public static void unZipFile(Context context, String zipFilePath, String unZipFolderPath, int buffSize) {
        if (null != context && !TextUtils.isEmpty(zipFilePath) && !TextUtils.isEmpty(unZipFolderPath)) {
            String path = StaticFileUtils.getPath(context, unZipFolderPath);
            File desDir = new File(path);
            if (!desDir.exists() || !desDir.isDirectory()) {
                desDir.mkdirs();
            }
            ZipFile zf = null;
            try {
                zf = new ZipFile(new File(zipFilePath));
                for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements(); ) {
                    ZipEntry entry = ((ZipEntry) entries.nextElement());
                    // 后面插入判断是否为文件夹，如果是不处理(因为程序会根据你的路径自动去文件之上的文件夹)
                    String name = entry.getName();
                    if (name.endsWith(File.separator)) {
                        continue;
                    }

                    InputStream in = null;
                    OutputStream out = null;
                    try {
                        in = zf.getInputStream(entry);
                        String str = path + File.separator + name;
                        str = new String(str.getBytes("8859_1"), "GB2312");
                        File desFile = new File(str);
                        if (!desFile.exists()) {
                            File fileParentDir = desFile.getParentFile();
                            if (!fileParentDir.exists() && !fileParentDir.isDirectory()) {
                                fileParentDir.mkdirs();
                            }
                            desFile.createNewFile();
                        }
                        out = new FileOutputStream(desFile);
                        byte buffer[] = new byte[buffSize];
                        int realLength;
                        while ((realLength = in.read(buffer)) > 0) {
                            out.write(buffer, 0, realLength);
                        }
                    } finally {
                        try {
                            if (null != in) {
                                in.close();
                            }
                            if (null != out) {
                                out.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != zf) {
                        zf.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Logger.e(TAG, "upZipFile()方法中有必须传值的参数未传入值");
        }
    }

    /**
     * 解压压缩文件并查找包含传入文字的文件
     *
     * @param context         上下文。不能为null
     * @param zipFilePath     压缩文件路径。需要带文件扩展名，不能为null和空
     * @param unZipFolderPath 解压写出文件目录。样例:xxx/xxx。不能为null和空
     *                        <p>
     *                        已经判断是否有SD卡。如果有就拼接SD卡路径，没有就拼接手机内存路径
     *                        </p>
     * @param nameContains    传入的文件匹配名。不能为null和空
     */
    public static ArrayList<File> unZipSelectedFile(Context context, String zipFilePath,
                                                    String unZipFolderPath, String nameContains) {
        ArrayList<File> fileList = new ArrayList<>();
        if (null != context && !TextUtils.isEmpty(zipFilePath) && !TextUtils.isEmpty(unZipFolderPath) && !TextUtils.isEmpty(nameContains)) {
            String path = StaticFileUtils.getPath(context, unZipFolderPath);
            File desDir = new File(path);
            if (!desDir.exists() || !desDir.isDirectory()) {
                desDir.mkdirs();
            }
            ZipFile zf = null;
            try {
                zf = new ZipFile(new File(zipFilePath));
                for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements(); ) {
                    ZipEntry entry = ((ZipEntry) entries.nextElement());
                    if (entry.getName().contains(nameContains)) {
                        InputStream in = null;
                        OutputStream out = null;
                        try {
                            in = zf.getInputStream(entry);
                            String str = path + File.separator + entry.getName();
                            str = new String(str.getBytes("8859_1"), "GB2312");
                            // str.getBytes("GB2312"),"8859_1" 输出
                            // str.getBytes("8859_1"),"GB2312" 输入
                            File desFile = new File(str);
                            if (!desFile.exists()) {
                                File fileParentDir = desFile.getParentFile();
                                if (!fileParentDir.exists()) {
                                    fileParentDir.mkdirs();
                                }
                                desFile.createNewFile();
                            }
                            out = new FileOutputStream(desFile);
                            byte buffer[] = new byte[BUFF_SIZE];
                            int realLength;
                            while ((realLength = in.read(buffer)) > 0) {
                                out.write(buffer, 0, realLength);
                            }
                            fileList.add(desFile);
                        } finally {
                            if (null != in) {
                                in.close();
                            }
                            if (null != out) {
                                out.close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != zf) {
                        zf.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Logger.e(TAG, "upZipSelectedFile()方法中有必须传值的参数未传入值");
        }
        return fileList;
    }

    /**
     * 获得压缩文件内文件列表
     *
     * @param zipFilePath 压缩文件路径。需要带文件扩展名，不能为null和空
     * @return 压缩文件内文件名称集合
     */
    public static ArrayList<String> getEntriesNames(String zipFilePath) {
        ArrayList<String> entryNames = new ArrayList<>();
        if (!TextUtils.isEmpty(zipFilePath)) {
            ZipFile zf = null;
            try {
                zf = new ZipFile(new File(zipFilePath));
                Enumeration<?> entries = zf.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = ((ZipEntry) entries.nextElement());
                    entryNames.add(new String(entry.getName().getBytes("GB2312"),
                            "8859_1"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != zf) {
                        zf.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Logger.e(TAG, "getEntriesNames()方法中有必须传值的参数未传入值");
        }
        return entryNames;
    }

    /**
     * 取得压缩文件对象的注释
     *
     * @param zipFilePath 压缩文件路径。需要带文件扩展名，不能为null和空
     * @return 压缩文件对象的注释
     */
    @TargetApi(19)
    public static String getEntryComment(String zipFilePath) {
        String byteToString = "";
        if (!TextUtils.isEmpty(zipFilePath)) {
            ZipFile zf = null;
            try {
                zf = new ZipFile(new File(zipFilePath));
                byte[] bytes = zf.getComment().getBytes("GB2312");
                byteToString = new String(bytes, "8859_1");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != zf) {
                        zf.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Logger.e(TAG, "getEntryComment()方法中有必须传值的参数未传入值");
        }
        return byteToString;
    }


    /**
     * 压缩文件
     *
     * @param resFile  需要压缩的文件（夹）
     * @param zipout   压缩的目的文件
     * @param rootpath 压缩的文件路径
     * @throws IOException 当压缩过程出错时抛出
     */
    private static void zipFile(File resFile, ZipOutputStream zipout,
                                String rootpath) throws IOException {
        if (resFile.exists()) {
            rootpath = rootpath
                    + (rootpath.trim().length() == 0 ? "" : File.separator)
                    + resFile.getName();
            rootpath = new String(rootpath.getBytes("8859_1"), "GB2312");
            if (resFile.isDirectory()) {
                File[] fileList = resFile.listFiles();
                for (File file : fileList) {
                    zipFile(file, zipout, rootpath);
                }
            } else {
                byte buffer[] = new byte[BUFF_SIZE];
                BufferedInputStream in = new BufferedInputStream(
                        new FileInputStream(resFile), BUFF_SIZE);
                zipout.putNextEntry(new ZipEntry(rootpath));
                int realLength;
                while ((realLength = in.read(buffer)) != -1) {
                    zipout.write(buffer, 0, realLength);
                }
                in.close();
                zipout.flush();
                zipout.closeEntry();
            }
        }
    }
}
