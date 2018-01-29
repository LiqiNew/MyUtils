package com.liqi.myutils.demo;

import android.app.Application;

import com.liqi.utils.imageloader.ImageLoaderUtils;

/**
 * Created by LiQi on 2018/1/29.
 */

public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //ImageLoader图片加载初始化
        ImageLoaderUtils.init(getApplicationContext(), "liqi/test/image");
    }
}
