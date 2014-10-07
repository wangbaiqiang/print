package com.example.pirnter_demo_80mm.util;

import java.util.ArrayList;
import java.util.List;

public class ConvertUtil {

	private static String hexStr = "0123456789ABCDEF";
	private static String[] binaryArray = { "0000", "0001", "0010", "0011",
			"0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011",
			"1100", "1101", "1110", "1111" };

	public static String myBinaryStrToHexString(String binaryStr) {
		String hex = "";
		String f4 = binaryStr.substring(0, 4);
		String b4 = binaryStr.substring(4, 8);
		for (int i = 0; i < binaryArray.length; i++) {
			if (f4.equals(binaryArray[i]))
				hex += hexStr.substring(i, i + 1);
		}
		for (int i = 0; i < binaryArray.length; i++) {
			if (b4.equals(binaryArray[i]))
				hex += hexStr.substring(i, i + 1);
		}

		return hex;
	}

	/**
	 * 
	 * @param hexString
	 * @return ��ʮ������ת��Ϊ�ֽ�����
	 */
	public static byte[] HexStringToBinary(String hexString) {
		// hexString�ĳ��ȶ�2ȡ������Ϊbytes�ĳ���
		int len = hexString.length() / 2;
		byte[] bytes = new byte[len];
		byte high = 0;// �ֽڸ���λ
		byte low = 0;// �ֽڵ���λ

		for (int i = 0; i < len; i++) {
			// ������λ�õ���λ
			high = (byte) ((hexStr.indexOf(hexString.charAt(2 * i))) << 4);
			low = (byte) hexStr.indexOf(hexString.charAt(2 * i + 1));
			bytes[i] = (byte) (high & 0xF0 | low & 0x0F);// �ߵ�λ��������
		}
		return bytes;
	}

	/**
	 * 
	 * @param bytes
	 * @return ��������ת��Ϊʮ�������ַ����
	 */
	public static String binaryToHexString(byte[] bytes) {

		String result = "";
		String hex = "";
		for (int i = 0; i < bytes.length; i++) {
			// �ֽڸ�4λ
			hex = String.valueOf(hexStr.charAt((bytes[i] & 0xF0) >> 4));
			// �ֽڵ�4λ
			hex += String.valueOf(hexStr.charAt(bytes[i] & 0x0F));
			result += hex + " ";
		}
		return result;
	}

	private static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode(new String(new byte[] { src0 })).byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode(new String(new byte[] { src1 })).byteValue();
		byte ret = (byte) (_b0 | _b1);
		byte aret = Byte.decode("0x" + ret).byteValue();

		return aret;
	}

	public static byte[] HexString2Bytes(String src) {
		int len = src.length() / 2;
		byte[] bytes = new byte[len];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < len; ++i) {
			bytes[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return bytes;
	}

	/**
	 * String���ַ���ת����unicode��String
	 */
	public static String stringToUnicode(String strText) throws Exception {
		char c;
		String strRet = "";
		int intAsc;
		String strHex;
		for (int i = 0; i < strText.length(); i++) {
			c = strText.charAt(i);
			intAsc = (int) c;
			strHex = Integer.toHexString(intAsc);
			if (intAsc > 128) {
				strRet += "\\u" + strHex;
			} else {
				// ��λ��ǰ�油00
				strRet += "\\u00" + strHex;
			}
		}
		return strRet;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/** Convert hexString to bytes(����) */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/** ������List<String>תΪHexString */
	public static List<String> binaryListToHexStringList(List<String> list) {
		List<String> hexList = new ArrayList<String>();
		for (String binaryStr : list) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < binaryStr.length(); i += 8) {
				String str = binaryStr.substring(i, i + 8);
				// ת��16����
				String hexString = ConvertUtil.myBinaryStrToHexString(str);
				sb.append(hexString);
			}
			hexList.add(sb.toString());
		}
		return hexList;

	}

	/**
	 * ָ��listת��Ϊbyte[]ָ��
	 */
	public static byte[] hexList2Byte(List<String> list) {

		List<byte[]> commandList = new ArrayList<byte[]>();

		for (String hexStr : list) {
			commandList.add(hexStringToBytes(hexStr));
		}
		byte[] bytes = sysCopy(commandList);
		return bytes;
	}

	/**
	 * ϵͳ�ṩ�����鿽������arraycopy
	 * */
	public static byte[] sysCopy(List<byte[]> srcArrays) {
		int len = 0;
		for (byte[] srcArray : srcArrays) {
			len += srcArray.length;
		}
		byte[] destArray = new byte[len];
		int destLen = 0;
		for (byte[] srcArray : srcArrays) {
			System.arraycopy(srcArray, 0, destArray, destLen, srcArray.length);
			destLen += srcArray.length;
		}
		return destArray;
	}

	/**
	 * ���ַ���ת��unicode
	 * 
	 * @param str
	 *            ��ת�ַ���
	 * @return unicode�ַ���
	 */
	public static String convert(String str) {
		str = (str == null ? "" : str);
		String tmp;
		StringBuffer sb = new StringBuffer(1000);
		char c;
		int i, j;
		sb.setLength(0);
		for (i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			sb.append("\\u");
			j = (c >>> 8); // ȡ����8λ
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);
			j = (c & 0xFF); // ȡ����8λ
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);

		}
		return (new String(sb));
	}
}
