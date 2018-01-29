package com.liqi.utils.xml;

import java.util.Arrays;

/**
 * XML标签名和标签属性值对象
 * 
 * @author LiQi
 * 
 */
public class XmlLabelData {
	/**
	 *     标签名
 	 */
	private String xmlLabel;
	/**
	 *     属性数组
 	 */
	private String attributeArray[];

	public String getXmlLabel() {
		return xmlLabel;
	}

	public void setXmlLabel(String xmlLabel) {
		this.xmlLabel = xmlLabel;
	}

	public String[] getAttributeArray() {
		return attributeArray;
	}

	public void setAttributeArray(String[] attributeArray) {
		this.attributeArray = attributeArray;
	}

	@Override
	public String toString() {
		return "XmlLabelData [xmlLabel=" + xmlLabel + ", attributeArray="
				+ Arrays.toString(attributeArray) + "]";
	}

	public XmlLabelData() {
	}

}
