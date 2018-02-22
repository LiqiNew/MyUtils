[![](https://jitpack.io/v/liqinew/myutils.svg)](https://jitpack.io/#liqinew/myutils)
[![](https://img.shields.io/badge/%E4%BD%9C%E8%80%85-%E6%9D%8E%E5%A5%87-orange.svg)](https://github.com/LiqiNew)

# MyUtils
MyUtils是一个包含全方位的工具的工具项目。项目里面提供Base64编码解码工具、MD5加密工具、AES加密解码工具、SharePreference操作工具、
File文件操作工具、日期获取和计算工具、界面跳转Intent操作工具、字符串验证和数值转换操作工具、手机震动工具、系统资源操作工具、网络检测工具、
wifi操作工具、单位换算工具、zip压缩和解压操作工具、XML解析操作工具（只支持几种指定格式）、图片加载和处理工具，数据库操作（增删改查）工具。

### 内部远程依赖Library（已经远程依赖的Library，切勿主项目里重复依赖。） 

**BaseLogger：'com.github.liqinew:baselogger:V.1.0.0'**<br>

**PhotoFrame：'com.github.liqinew:photoframe:v.1.0.3'**<br>

**universal-image-loader：'com.nostra13.universalimageloader:universal-image-loader:1.9.5'**<br>

**appcompat-v7：'com.android.support:appcompat-v7:25.2.0'**<br>

### 关于权限
**项目targetSdkVersion <= 22，不需要做任何操作。<br>
项目targetSdkVersion >= 23，请自行动态赋予权限。[推荐使用此框架去动态设置权限-AndPermission](https://github.com/yanzhenjie/AndPermission)**

# 如何使用?

### Gradle远程依赖 ###
**1：在项目根目录build.gradley**	<br>
```gradle
allprojects {
　　repositories {
  　　//依赖仓库
　　　maven { url 'https://jitpack.io' }
　　}
}
```
**2：依赖MyUtils**<br>
```gradle
compile 'com.github.liqinew:myutils:V.1.0.1'
```

###  [点击查阅MyUtils-API文档](https://liqinew.github.io/MyUtils/)

### 对应的工具对象

* **Base64**对象是Base64编码解码工具
* **MD5Util**对象是MD5加密工具
* **AESEncryptor**对象是AES加密解码工具
* **BaseSharePreference**对象是SharePreference操作工具
* **StaticFileUtils**对象是File文件操作工具
* **TimeUtil**对象是日期获取和计算工具
* **ActivityUtil**对象是界面跳转Intent操作工具
* **Validation**对象是字符串验证和数值转换操作工具
* **VibratorUtil**对象是手机震动工具
* **StaticUtility**对象是系统资源操作工具
* **NetWorkUtil**对象是网络检测工具
* **WifiController**对象是wifi操作工具
* **FDUnitUtil**对象是单位换算工具
* **ZipUtils**对象是zip压缩和解压操作工具
* **ImageLoaderUtils**对象是图片加载工具，采用image-loader框架实现加载。
* **ImageStaticDispose**对象是图片处理工具
* **BaseDBManagerOperation**对象是数据库操作（增删改查）工具
* **XmlUtils**对象是XML解析操作工具，只支持几种指定格式。(建议下载demo查看支持的XML格式)

#### 如有没有包含的工具，后续会慢慢完善添加进来。如你有好的工具也可以通过QQ：543945827推荐给我。<br>另外别忘记给我star哦。^_^..谢谢。
