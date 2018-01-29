package com.liqi.utils.xml;

import android.util.Xml;

import com.liqi.Logger;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * XML 解析工具对象。
 * <p>
 * 由于XML解析的格式有限。
 * 在使用前，请参看解析方法对应的XML格式。
 * </p>
 *
 * @author LiQi
 */
public class XmlUtils {
    public static final String TAG = "tag";

    private XmlUtils() {

    }

    /**
     * 根据XML来获取指定节点（单个节点），判断是否符合OK来返回
     *
     * @param xmlName xmlName[0] XML文件，xmlName[1] XML子节点，xmlName[2] 要对比的值
     * @return true 是和对比的一致，false 和对比的值不一致
     */
    public static boolean xmlPull(String... xmlName) {
        boolean wxSign = false;
        try {
            // 准备解析器
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(xmlName[0]));// 设置要解析的输入流
            boolean isdone = false;// 是否结束解析
            // 取得当前节点的事件类型
            int eventType = parser.getEventType();
            // 当事件不是文档结尾时，一直解析内容
            while (eventType != XmlPullParser.END_DOCUMENT && !isdone) {
                // 比较事件类型
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                    case XmlPullParser.START_TAG:
                        // 取得当前节点的 标记
                        String tag = parser.getName();
                        if (xmlName[1].equals(tag)) {
                            String message = parser.nextText();
                            if (xmlName[2].equals(message)) {
                                wxSign = true;
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:// 结束标记
                        String nameTag = parser.getName();
                        if (xmlName[1].equalsIgnoreCase(nameTag)) {
                            isdone = true;
                        }
                        break;
                }
                // 手动指向下一个节点，继续解析
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wxSign;
    }

    /**
     * 通过XML文件获取一个节点文本内容list集合(只支持两种格式的XML)
     *
     * @param xml            xml文件
     * @param xmlLabelList   XML里面的三级标签名字
     *                       XML样例：
     *                       <p>
     *                       <?xml version="1.0" encoding="UTF-8"?>
     *                       <program>这是XML的一级标签
     *                       <data > 这是二级标签
     *                       <tag>videorect</tag> 这是三级标签
     *                       <nH>300</nH> 这是三级标签
     *                       </data>二级标签结束
     *                       <p>
     *                       <data> 这是二级标签
     *                       <tag>imagerect</tag>这是三级标签
     *                       <nH>300</nH>这是三级标签
     *                       </data>二级结束
     *                       <p>
     *                       </program>一级标签结束
     *                       </p>
     * @param xmlParentLabel XML里面二级标签名字
     *                       <P>
     *                       <?xml version="1.0" encoding="UTF-8"?>
     *                       <program> 这是XML的一级标签
     *                       <nW>300</nW>这是二级标签
     *                       <nH>300</nH>这是二级标签
     *                       </program>一级标签结束
     *                       </p>
     * @param <T>            继承Object 。只支持两种类型：Map<String, String>和String。
     * @return 解析之后数据结果集合
     */
    public static <T extends Object> List<T> xmlPullObjList(String xml, String xmlLabelList[],
                                                            String[] xmlParentLabel) {
        List<T> list = new ArrayList<>();
        if (xmlLabelList == null) {
            Logger.e("XmlUtils.xmlPullObjList()", "xmlLabelList存放XML字标签为空");
            return list;
        }
        try {
            // 是为了XML集合对象存储定义的变量
            Hashtable<String, String> hash = null;
            // 准备解析器
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(xml));// 设置要解析的输入流
            // 取得当前节点的事件类型
            int eventType = parser.getEventType();
            // 当事件不是文档结尾时，一直解析内容
            while (eventType != XmlPullParser.END_DOCUMENT) {
                // 比较事件类型
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        // 取得当前节点的 标记
                        String tag = parser.getName();
                        // 判断是否XML集合对象的父节点是否存在
                        if (xmlParentLabel != null) {
                            for (int i = 0; i < xmlParentLabel.length; i++) {
                                if (xmlParentLabel[i].equals(tag)) {
                                    hash = new Hashtable<>();
                                    break;
                                }
                            }

                        }
                        for (int i = 0; i < xmlLabelList.length; i++) {
                            String string = xmlLabelList[i];
                            if (string.equals(tag)) {
                                String nextText = parser.nextText();// nextText是获取文本数据，跳过当前节点尾部读取，并且读取下一个节点
                                if (hash != null) {
                                    hash.put(string, nextText);
                                } else {
                                    list.add((T) nextText);// 没有三级节点此处添进list里面
                                }
                                break;
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:// 结束标记
                        String nameTag = parser.getName();
                        if (xmlParentLabel != null) {
                            for (int i = 0; i < xmlParentLabel.length; i++) {
                                if (xmlParentLabel[i].equals(nameTag)) {
                                    list.add((T) hash);// 每个二级节点里面都有三级节点此处添进list里面
                                    hash = null;
                                    break;
                                }
                            }
                        }
                        break;
                }
                // 手动指向下一个节点，继续解析
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 通过XML文件获取一个节点属性list集合
     *
     * @param xmlName  XML文件
     *                 <p>
     *                 XML样例：
     *                 <?xml version="1.0" encoding="UTF-8"?>
     *                 <program>
     *                 <videorec layer="1" nX="10" nY="10" nW="300" nH="300"/>
     *                 <imagerect layer="1" nX="10" nY="10" nW="300" nH="300"/>
     *                 </program>
     *                 </p>
     * @param xmlLabel 存放标签名和标签属性数组的集合
     *                 @see XmlLabelData
     * @return 属性list集合
     */
    public static List<Hashtable<String, String>> xmlPullAttributeList(
            String xmlName, List<XmlLabelData> xmlLabel) {
        List<Hashtable<String, String>> list = new ArrayList<>();
        if (xmlLabel == null) {
            Logger.e("XmlUtils.xmlPullAttributeList()", "xmlLabel存放XML标签和属性数组的集合为空");
            return list;
        }
        try {
            Hashtable<String, String> hash = null;
            // 准备解析器
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(xmlName));// 设置要解析的输入流
            // 取得当前节点的事件类型
            int eventType = parser.getEventType();
            // 当事件不是文档结尾时，一直解析内容
            while (eventType != XmlPullParser.END_DOCUMENT) {
                // 比较事件类型
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        // 取得当前节点的 标记
                        String tag = parser.getName();
                        // 根据传过来的标签和标签属性数组来获取xml值
                        for (int i = 0; i < xmlLabel.size(); i++) {
                            XmlLabelData xmlLabelData = xmlLabel.get(i);
                            String label = xmlLabelData.getXmlLabel();
                            if (label.equals(tag)) {
                                String[] attributeArray = xmlLabelData
                                        .getAttributeArray();
                                hash = getAttributeDate(parser, tag, attributeArray);
                                break;
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:// 结束标记
                        String nameTag = parser.getName();
                        System.out.println("XML文件名称：服务器定义名称>" + nameTag);
                        for (int i = 0; i < xmlLabel.size(); i++) {
                            XmlLabelData xmlLabelData = xmlLabel.get(i);
                            String label = xmlLabelData.getXmlLabel();
                            if (label.equals(nameTag)) {
                                list.add(hash);
                                hash = null;
                                break;
                            }
                        }
                        break;
                }
                // 手动指向下一个节点，继续解析
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据属性名获取属性值
     *
     * @param parser
     * @param tag
     * @param attributeArray 属性数组
     * @return 填充满的数据对象
     */
    private static Hashtable<String, String> getAttributeDate(
            XmlPullParser parser, String tag, String attributeArray[]) {
        Hashtable<String, String> hash = new Hashtable<>();
        //保存当前节点名称
        hash.put(TAG, tag);
        for (int i = 0; i < attributeArray.length; i++) {
            String value = parser.getAttributeValue(null, attributeArray[i]);
            hash.put(attributeArray[i], value == null ? "" : value);
        }
        return hash;
    }
}
