package com.liqi.utils.imageloader;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * 基于ImageLoader封装的操作对象
 *
 * @author Liqi
 */
public class ImageLoaderUtils {
    /**
     * 初始化ImageLoader
     *
     * @param context
     * @param fileString 图片缓存存储路径
     */
    public static void init(Context context, String fileString) {
        // 初始化图片加载框架
        getImageLoader().init(getImageLoaderConfiguration(context, fileString).build());
    }

    /**
     * 初始化ImageLoader
     */
    public static void init(ImageLoaderConfiguration imageLoaderConfiguration) {
        // 初始化图片加载框架
        getImageLoader().init(imageLoaderConfiguration);
    }

    /**
     * 通过URL把图片展示出来
     *
     * @param url           图片地址
     * @param imageView     图片展示控件
     * @param drawableStub  图片下载期间显示的图片ID
     * @param drawableError 错误的时候显示的图片ID
     * @param cacheTarget   false是存储在缓存进内存中，true缓存到SD中
     * @param rounded       圆角角度
     */
    public static void displayImage(String url, ImageView imageView, int drawableStub,
                                    int drawableError, boolean cacheTarget, int rounded) {
        getImageLoader().displayImage(url, imageView, getDisplayImageOptions(drawableStub, drawableError, cacheTarget, rounded));
    }

    /**
     * 通过URL把图片展示出来
     *
     * @param url                 图片地址
     * @param imageView           图片展示控件
     * @param displayImageOptions 显示图象选项
     */
    public static void displayImage(String url, ImageView imageView, DisplayImageOptions displayImageOptions) {
        getImageLoader().displayImage(url, imageView, displayImageOptions);
    }

    /**
     * 获取ImageLoader对象
     *
     * @return ImageLoader对象
     */
    public static ImageLoader getImageLoader() {
        return ImageLoader.getInstance();
    }

    /**
     * 获取ImageLoader配置ImageLoaderConfiguration对象
     * <p>
     * 此方法里面ImageLoaderConfiguration已经配置对应的设置。可以直接使用，也可以重新配置。
     * </p>
     *
     * @param context 上下文
     * @param path    存储路径
     * @return ImageLoaderConfiguration对象
     */
    public static ImageLoaderConfiguration.Builder getImageLoaderConfiguration(
            Context context, String path) {
        // 缓存文件的目录
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, path);
        return new ImageLoaderConfiguration.Builder(
                context)
                // default = device screen dimensions 内存缓存文件的最大长宽
                .memoryCacheExtraOptions(480, 800)
                // default 线程池内加载的数量
                .threadPoolSize(5)
                // default 设置当前线程的优先级
                .threadPriority(Thread.NORM_PRIORITY - 2)
                // default
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // 你可以通过自己的内存缓存实现
                // 内存缓存的最大值
                .memoryCacheSize(2 * 1024 * 1024)
                // default
                .memoryCacheSizePercentage(13)
                // default 可以自定义缓存路径
                .diskCache(new UnlimitedDiskCache(cacheDir))
                // 50 Mb sd卡(本地)缓存的最大值
                .diskCacheSize(50 * 1024 * 1024)
                // 可以缓存的文件数量
                .diskCacheFileCount(100)
                // default为使用Md5FileNameGenerator对UIL进行加密命名，用MD5(new
                // Md5FileNameGenerator())加密
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs(); // 打印debug log
    }

    /**
     * 获取ImageLoader配置DisplayImageOptions对象
     * <p>
     * 此方法里面DisplayImageOptions已经配置对应的设置。可以直接使用，也可以重新配置。
     * </p>
     *
     * @param drawableStub  图片下载期间显示的图片ID
     * @param drawableError 错误的时候显示的图片ID
     * @param cacheTarget   false是存储在缓存进内存中，true缓存到SD中
     * @param rounded       圆角角度
     * @return DisplayImageOptions对象
     */
    public static DisplayImageOptions getDisplayImageOptions(int drawableStub,
                                                             int drawableError, boolean cacheTarget, int rounded) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
                .showImageOnLoading(drawableStub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(drawableError) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(drawableError) // 设置图片加载或解码过程中发生错误显示的图片
                .resetViewBeforeLoading(true) // default 设置图片在加载前是否重置、复位
                .delayBeforeLoading(100) // 下载前的延迟时间
                .cacheInMemory(!cacheTarget) // default 设置下载的图片是否缓存在内存中
                .cacheOnDisk(cacheTarget) // default 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // default 保留Exif信息
                .imageScaleType(ImageScaleType.EXACTLY) // default设置图片以如何的编码方式显示
                .bitmapConfig(Config.RGB_565) // default 设置图片的解码类型
                .displayer(new FadeInBitmapDisplayer(100));// default设置淡入动画
        DisplayImageOptions options;
        //是否设置圆角
        if (rounded > 0) {
            options = builder.displayer(new RoundedBitmapDisplayer(rounded))//设置圆角图片
                    .build();
        } else {
            options = builder.build();
        }
        return options;
    }
}
