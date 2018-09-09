package com.liqi.myutils.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.liqi.Logger;
import com.liqi.myutils.demo.db.TestDataBaseOperateActivity;
import com.liqi.utils.ActivityUtil;
import com.liqi.utils.FDUnitUtil;
import com.liqi.utils.NetWorkUtil;
import com.liqi.utils.StaticUtility;
import com.liqi.utils.Validation;
import com.liqi.utils.VibratorUtil;
import com.liqi.utils.encoding.AndroidAESEncryptor;
import com.liqi.utils.encoding.Base64;
import com.liqi.utils.encoding.JToAAesEncryptor;
import com.liqi.utils.encoding.MD5Util;
import com.liqi.utils.file.StaticFileUtils;
import com.liqi.utils.imageloader.ImageLoaderUtils;
import com.liqi.utils.spf.BaseSharePreference;
import com.liqi.utils.spf.BaseSharePreferenceTypeEnum;
import com.liqi.utils.time.TimeUtil;
import com.liqi.utils.wifi.WifiController;
import com.liqi.utils.xml.XmlUtils;
import com.liqi.utils.zip.ZipUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 其它功能测试用例
 * <hint>
 * MVC结构模式
 * </hint>
 * Created by LiQi on 2017/12/13.
 */

public class TestOtherActivity extends AppCompatActivity {
    private int mType, mAseType, mBase64, mTestFile, mWifiType, mZipType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_other_activity);

        //SharePreference操作
        final TextView testOtherTextview = (TextView) findViewById(R.id.test_other_textview);
        findViewById(R.id.test_other_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mType++;
                Button button = (Button) v;
                switch (mType) {
                    case 1:
                        getBaseSharePreference().putObjectKeyValue("test", button.getText().toString());
                        button.setText("SharePreference读取值>>>2");
                        testOtherTextview.setText("");
                        break;
                    case 2:
                        String test = getBaseSharePreference().getObjectKeyValue("test", BaseSharePreferenceTypeEnum.SP_STRING);
                        testOtherTextview.setText(test);
                        button.setText("SharePreference存储值>>>1");
                        mType = 0;
                        break;
                }
            }
        });

        //android端本地AES加密
        findViewById(R.id.test_other_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAseType++;
                Button button = (Button) v;
                String content = button.getText().toString().split(":")[1];
                switch (mAseType) {
                    case 1:
                        try {
                            String encryptContent = AndroidAESEncryptor.encrypt128("123456", content);
                            button.setText("Android-本地AES要解密内容\n:" + encryptContent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        try {
                            String decryptContent = AndroidAESEncryptor.decrypt128("123456", content);
                            button.setText("Android-本地AES要加密内容\n:" + decryptContent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mAseType = 0;
                        break;
                }
            }
        });
        //解密Java端-AES加密内容
        findViewById(R.id.test_other_button17).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                //javaAES加密出来的密文，明文是：加密内容
                String decryptContent = "3DF88A85EC0D0489269F09286D6C6B10";
                try {
                    decryptContent = JToAAesEncryptor.decrypt(JavaTest.KEY, decryptContent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                button.setText("解密Java端-AES加密内容\n" + decryptContent);
            }
        });
        //Base64编码解码
        findViewById(R.id.test_other_button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBase64++;
                Button button = (Button) v;
                String content = button.getText().toString().split(":")[1];
                switch (mBase64) {
                    case 1:

                        String encode = Base64.encode(content);
                        button.setText("Base64要解码内容:" + encode);
                        break;
                    case 2:
                        String decode = Base64.decode(content);
                        button.setText("Base64要编码内容:" + decode);
                        mBase64 = 0;
                        break;
                }
            }
        });

        //MD5编码
        findViewById(R.id.test_other_button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String content = button.getText().toString().split(":")[1];
                String md5Encode = MD5Util.md5Encode(content, "utf-8");
                button.setText("MD5加密后内容:" + md5Encode);
            }
        });

        //File文件操作
        findViewById(R.id.test_other_button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Button button = (Button) v;
                //写入
                if (mTestFile == 0) {
                    button.setText("File文件正在写入...");
                    button.setEnabled(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                InputStream testFile = getAssets().open("testFile.txt");
                                /**
                                 * 根据传过去的文件目录路径和文件名来创建路径，并且返回路径File对象。
                                 * <p>
                                 * 没有创建，有直接返回。
                                 * </p
                                 * <p>
                                 * 已经判断是否有SD卡。如果有就拼接SD卡路径，没有就拼接手机内存路径
                                 * </p>
                                 */
                                final File foundFile = StaticFileUtils.nonentityFoundFile(TestOtherActivity.this, "test/file", "testFile.text");

                                /**
                                 * 文件写出。写出成功为true，写出失败为false
                                 */
                                if (StaticFileUtils.putFileOutputStream(testFile, foundFile, 1024 * 1024)) {
                                    //主线程运行
                                    TestOtherActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            button.setText("File文件删除\n写入和删除路径：" + foundFile.getAbsolutePath());
                                            button.setEnabled(true);
                                            mTestFile = 1;
                                        }
                                    });
                                }
                                //写出失败
                                else {
                                    //主线程运行
                                    TestOtherActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            button.setText("File文件写入\n写入和删除路径：" + foundFile.getAbsolutePath());
                                            button.setEnabled(true);
                                        }
                                    });
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                //删除
                else {
                    button.setText("File文件正在删除...");
                    button.setEnabled(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            /**
                             * 创建一个目录路径，并且返回目录路径
                             * <p>
                             * 没有创建，有直接返回。
                             * </p
                             * <p>
                             * 已经判断是否有SD卡。如果有就拼接SD卡路径，没有就拼接手机内存路径
                             * </p>
                             *
                             */
                            final String foundString = StaticFileUtils.foundFilePathString(TestOtherActivity.this, "test");
                            /**
                             * 根据路径删除指定的目录或文件，无论存在与否
                             *
                             * <p>
                             *     删除成功返回 true，否则返回 false。
                             * </p>
                             */
                            if (StaticFileUtils.deleteFolder(foundString)) {
                                //主线程运行
                                TestOtherActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        button.setText("File文件写入\n写入和删除路径：" + foundString);
                                        button.setEnabled(true);
                                        mTestFile = 0;
                                    }
                                });
                            }
                            //删除失败
                            else {
                                //主线程运行
                                TestOtherActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        button.setText("File文件删除\n写入和删除路径：" + foundString);
                                        button.setEnabled(true);
                                    }
                                });
                            }
                        }
                    }).start();
                }
            }
        });

        //日期获取和计算
        findViewById(R.id.test_other_button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                /**
                 * 获取当前指定格式的日期
                 * <p>
                 *     TimeUtil对象提供大量日期操作方法和时间值算法。
                 * </p>
                 */
                String dayss = TimeUtil.getDayss("yyyy-MM-dd　HH:mm:ss");

                button.setText("时间值获取：" + dayss);
            }
        });

        //界面跳转Intent操作
        findViewById(R.id.test_other_button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 开始一个新的activity
                 *<p>
                 *     ActivityUtil对象提供大量的Activity跳转方法
                 *</p>
                 */
                ActivityUtil.getActivityUtil().startActivity(TestOtherActivity.this, TestOtherActivity.class);
            }
        });

        //字符串验证和数值转换操作
        findViewById(R.id.test_other_button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * <p>
                 *     Validation对象不止提供大量的联系方式验证方法，还提供了一些类型值转换方法
                 * </p>
                 */


                /**
                 * double类型转换int类型 （去小数点准确转换）
                 *<p>
                 *     样例：要转换的值：1.0。转换之后的值：10
                 *</p>
                 */
                int doubleToInt = Validation.doubleToInt(12.123);
                /**
                 * 手机验证
                 *
                 */
                boolean matchMobile = Validation.matchMobile("16699999999");
                Toast.makeText(TestOtherActivity.this, "double类型12.123转换值：" + doubleToInt + "\n16699999999手机号码验证结果：" + matchMobile, Toast.LENGTH_SHORT).show();
            }
        });

        //手机震动
        findViewById(R.id.test_other_button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 开始震动
                 */
                VibratorUtil.vibrate(TestOtherActivity.this, 1000 * 3);
            }
        });

        //系统资源操作
        findViewById(R.id.test_other_button10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * <p>
                 *     StaticUtility对象不止提供大量的系统操作方法，还提供了一些资源类型获取方法
                 * </p>
                 */

                /**
                 * 获取本地APP版本号
                 *
                 */
                String versionString = StaticUtility.getVersionString(TestOtherActivity.this);
                /**
                 * 获取手机品牌
                 */
                String phoneBrand = StaticUtility.getPhoneBrand();
                /**
                 * 获取手机型号
                 *
                 */
                String phoneModel = StaticUtility.getPhoneModel();
                Toast.makeText(TestOtherActivity.this, "手机品牌：" + phoneBrand + "<>手机型号：" + phoneModel + "<>项目版本号：" + versionString, Toast.LENGTH_SHORT).show();
            }
        });

        //网络检测
        findViewById(R.id.test_other_button11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * <p>
                 *     NetWorkUtil对象提供了网络检测方法
                 * </p>
                 */

                /**
                 * 网络检测
                 */
                if (NetWorkUtil.isNetworkConnectionsOK(TestOtherActivity.this)) {
                    Toast.makeText(TestOtherActivity.this, "网络可用", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TestOtherActivity.this, "网络不可用", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //wifi操作
        findViewById(R.id.test_other_button12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                /**
                 * <p>
                 *     WifiController对象提供了一系列对wifi操作的方法
                 * </p>
                 */

                /**
                 * wifi操作
                 */
                switch (mWifiType) {
                    //开启
                    case 0:
                        /**
                         * 打开wifi.
                         */
                        WifiController.getWifiController(TestOtherActivity.this).openWifi();
                        button.setText("wifi关闭");
                        mWifiType = 1;
                        break;
                    //关闭
                    case 1:
                        /**
                         * 关闭wifi.
                         */
                        WifiController.getWifiController(TestOtherActivity.this).closeWifi();
                        button.setText("wifi开启");
                        mWifiType = 0;
                        break;
                }
            }
        });

        //单位换算操作
        findViewById(R.id.test_other_button13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                /**
                 * <p>
                 *     FDUnitUtil对象提供一系列单位转换操作的方法
                 * </p>
                 */


                /**
                 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
                 */
                int toPx = FDUnitUtil.dpToPx(TestOtherActivity.this, 5);
                button.setText("单位换算：5dp换算值px值为" + toPx);
            }
        });

        //zip压缩和解压文件
        findViewById(R.id.test_other_button14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Button button = (Button) v;
                //把资源zip文件写入本地-->（此处写入只是为了方便zip解压和压缩）
                if (mZipType == 0) {
                    button.setText("zip压缩文件写入中...");
                    button.setEnabled(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                InputStream testFile = getAssets().open("testZip.zip");
                                /**
                                 * 根据传过去的文件目录路径和文件名来创建路径，并且返回路径File对象。
                                 * <p>
                                 * 没有创建，有直接返回。
                                 * </p
                                 * <p>
                                 * 已经判断是否有SD卡。如果有就拼接SD卡路径，没有就拼接手机内存路径
                                 * </p>
                                 */
                                final File foundFile = StaticFileUtils.nonentityFoundFile(TestOtherActivity.this, "testZip", "testZip.zip");

                                /**
                                 * 文件写出。写出成功为true，写出失败为false
                                 */
                                if (StaticFileUtils.putFileOutputStream(testFile, foundFile, 1024 * 1024)) {
                                    //主线程运行
                                    TestOtherActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            button.setText("zip压缩文件解压\n先写入、再解压、再压缩测试用例\nzip查看路径：" + foundFile.getAbsolutePath());
                                            button.setEnabled(true);
                                            mZipType = 1;
                                        }
                                    });
                                }
                                //写出失败
                                else {
                                    //主线程运行
                                    TestOtherActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            button.setText("zip压缩文件写入\n先写入、再解压、再压缩测试用例");
                                            button.setEnabled(true);
                                        }
                                    });
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }


                /**
                 * <p>
                 *ZipUtils对象提供一系列对文件zip压缩和解压操作方法
                 * </p>
                 */

                /**
                 *zip解压--->(重点)
                 */
                else if (mZipType == 1) {
                    button.setText("zip压缩文件解压中...");
                    button.setEnabled(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final String foundString = StaticFileUtils.foundFilePathString(TestOtherActivity.this, "testZip/testZip.zip");

                            /**
                             * 解压一个zip文件，解压文件缓存指定大小 1M
                             *
                             */
                            ZipUtils.unZipFile(TestOtherActivity.this, foundString, "testZip/extract");
                            //主线程运行
                            TestOtherActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    button.setText("zip压缩文件压缩\n先写入、再解压、再压缩测试用例\nzip查看路径：" + foundString);
                                    button.setEnabled(true);
                                    mZipType = 2;
                                }
                            });
                        }
                    }).start();
                }

                /**
                 * zip压缩--->(重点)
                 */
                else {
                    button.setText("zip压缩文件压缩中...");
                    button.setEnabled(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final String foundString = StaticFileUtils.foundFilePathString(TestOtherActivity.this, "testZip/extract");

                            /**
                             * 无注释压缩文件（夹）。默认缓冲大小1M
                             *
                             */
                            ZipUtils.zipFiles(TestOtherActivity.this, foundString, "testZip", "testZip_02");
                            //主线程运行
                            TestOtherActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    button.setText("zip压缩文件演示完毕\nzip查看路径：" + foundString);
                                }
                            });
                        }
                    }).start();
                }
            }
        });

        /**
         * XML解析
         */
        final TextView testOtherTextview01 = (TextView) findViewById(R.id.test_other_textview01);
        findViewById(R.id.test_other_button15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String xml = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                        "<program>\n" +
                        " <data1>\n" +
                        "<t>测试类型1</t>\n" +
                        "<v>测试类型1>1</v> \n" +
                        "</data1>\n" +
                        " <data2>\n" +
                        "<t>测试类型2</t>\n" +
                        "<v>测试类型1>2</v>\n" +
                        "</data2>\n" +
                        "</program>";
                Logger.e("XML格式", xml);
                List<Map<String, String>> list = XmlUtils.xmlPullObjList(xml, new String[]{"t", "v"}, new String[]{"data1", "data2"});
                for (int i = 0; i < list.size(); i++) {
                    Map<String, String> map = list.get(i);
                    String t = map.get("t");
                    String v1 = map.get("v");
                    String trim = testOtherTextview01.getText().toString();
                    testOtherTextview01.setText(trim + "第" + (i + 1) + "节目录解析数据：t=" + t + "___v=" + v1 + "\n");
                }
            }
        });

        //图片加载操作
        final ImageView imageView = (ImageView) findViewById(R.id.image_view);
        findViewById(R.id.test_other_button16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoaderUtils.displayImage("http://122.224.4.84:8113/Images/4f5815ff02b8752db500000b/201510080932292559.jpg",
                        imageView,
                        R.mipmap.ic_launcher,
                        R.mipmap.ic_launcher,
                        true,
                        30);
            }
        });
        //数据库操作演示
        findViewById(R.id.test_other_button18).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.getActivityUtil().startActivity(TestOtherActivity.this, TestDataBaseOperateActivity.class);
            }
        });
    }

    private BaseSharePreference getBaseSharePreference() {
        return BaseSharePreference.initBaseSharePreference(this, "test_my");
    }
}
