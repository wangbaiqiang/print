package com.example.pirnter_demo_80mm.util;

import java.io.UnsupportedEncodingException;

public class UnicodeUtil {
	
	 public static String getUNICODEBytes(String s) {
		   try {
		    StringBuffer out = new StringBuffer();
		    byte[] bytes = s.getBytes("unicode");
		    for (int i = 2; i < bytes.length - 1; i += 2) {
		     out.append("");
		     String str = Integer.toHexString(bytes[i + 1] & 0xff);
		     for (int j = str.length(); j < 2; j++) {
		      out.append("0");
		     }
		     out.append(str);
		     String str1 = Integer.toHexString(bytes[i] & 0xff);
		     for (int j = str1.length(); j < 2; j++) {
		      out.append("0");
		     }
		     out.append(str1);
		    }
		    return out.toString();
		   } catch (UnsupportedEncodingException e) {
		    e.printStackTrace();
		    return null;
		   }
	 }
	
	/**
	 * ������ת��Unicode��
	 * @param str
	 * @return
	 */
	public static String chinaToUnicode(String str){
		String result="";
		for (int i = 0; i < str.length(); i++){
            int chr1 = (char) str.charAt(i);
            if(chr1>=19968&&chr1<=171941){//���ַ�Χ \u4e00-\u9fa5 (����)
                result+="\\u" + Integer.toHexString(chr1);
            }else{
            	result+=str.charAt(i);
            }
        }
		return result;
	}

	/**
	 * �ж��Ƿ�Ϊ�����ַ�
	 * @param c
	 * @return
	 */
	public static  boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

	public static String string2Unicode(String s) {
	    try {
	      StringBuffer out = new StringBuffer("");
	      byte[] bytes = s.getBytes("unicode");
	      for (int i = 2; i < bytes.length - 1; i += 2) {
	        out.append("u");
	        String str = Integer.toHexString(bytes[i + 1] & 0xff);
	        for (int j = str.length(); j < 2; j++) {
	          out.append("0");
	        }
	        String str1 = Integer.toHexString(bytes[i] & 0xff);

	        out.append(str);
	        out.append(str1);
	        out.append(" ");
	      }
	      return out.toString().toUpperCase();
	    }
	    catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	      return null;
	    }
	  } 

	 

	public static String unicode2String(String unicodeStr){
	    StringBuffer sb = new StringBuffer();
	    String str[] = unicodeStr.toUpperCase().split("U");
	    for(int i=0;i<str.length;i++){
	      if(str[i].equals("")) continue;
	      char c = (char)Integer.parseInt(str[i].trim(),16);
	      sb.append(c);
	    }
	    return sb.toString();
	  }

	
	/** 
	* ���ַ���ת��unicode 
	* @param str ��ת�ַ��� 
	* @return unicode�ַ��� 
	*/ 
	public static String convert(String str) 
	{ 
	str = (str == null ? "" : str); 
	String tmp; 
	StringBuffer sb = new StringBuffer(1000); 
	char c; 
	int i, j; 
	sb.setLength(0); 
	for (i = 0; i < str.length(); i++) 
	{ 
	c = str.charAt(i); 
	sb.append("\\u"); 
	j = (c >>>8); //ȡ����8λ 
	tmp = Integer.toHexString(j); 
	if (tmp.length() == 1) 
	sb.append("0"); 
	sb.append(tmp); 
	j = (c & 0xFF); //ȡ����8λ 
	tmp = Integer.toHexString(j); 
	if (tmp.length() == 1) 
	sb.append("0"); 
	sb.append(tmp); 

	} 
	return (new String(sb)); 
	} 

}
