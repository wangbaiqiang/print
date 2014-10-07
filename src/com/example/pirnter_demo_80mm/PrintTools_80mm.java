package com.example.pirnter_demo_80mm;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.example.pirnter_demo_80mm.util.ConvertUtil;
import com.example.pirnter_demo_80mm.util.UnicodeUtil;

@SuppressWarnings("all")
public class PrintTools_80mm {

	public static final byte HT = 0x9; // ˮƽ�Ʊ�
	public static final byte LF = 0x0A; // ��ӡ������
	public static final byte CR = 0x0D; // ��ӡ�س�
	public static final byte ESC = 0x1B;
	public static final byte DLE = 0x10;
	public static final byte GS = 0x1D;
	public static final byte FS = 0x1C;
	public static final byte STX = 0x02;
	public static final byte US = 0x1F;
	public static final byte CAN = 0x18;
	public static final byte CLR = 0x0C;
	public static final byte EOT = 0x04;

	/* ��ʼ����ӡ�� */
	public static final byte[] ESC_INIT = new byte[] { 0x1b, 0x40 };
	/* Ĭ����ɫ����ָ�� */
	public static final byte[] ESC_FONT_COLOR_DEFAULT = new byte[] { GS, 0x46,
			0x03, 0x01 };
	/* ��׼��С */
	public static final byte[] FS_FONT_ALIGN = new byte[] { FS, 0x21, 1, ESC,
			0x21, 1 };
	/* �����ӡ���� */
	public static final byte[] ESC_ALIGN_LEFT = new byte[] { 0x1b, 'a', 0x00 };
	/* ���д�ӡ���� */
	public static final byte[] ESC_ALIGN_CENTER = new byte[] { 0x1b, 'a', 0x01 };
	/* ȡ������Ӵ� */
	public static final byte[] ESC_CANCEL_BOLD = new byte[] { ESC, 0x45, 0 };

	/* ��߾� */
	public static final byte[] GS_LEFT = new byte[] { 0x1D, 0x4C, (byte) 0xFF,
			0x00 };

	// ��ֽ
	// public static final byte[] ESC_ENTER = new byte[] { 0x1B, 0x4A, 0x40 };

	// ��ֽ
	public static final byte[] ESC_GO_PAGE = new byte[] { 0x1B, 0x64, 0x02 };

	// ��ֽ
	public static final byte[] POS_CUT_MODE_FULL = new byte[] { GS, 'V', 0x00 };

	/* �������򶼷Ŵ�һ�� */
	public static final byte[] FS_FONT_DOUBLE = new byte[] { FS, 0x21, 12, ESC,
			0x21, 48 };

	// 80mm�Լ�
	public static final byte[] PRINTE_TEST = new byte[] { 0x1D, 0x28, 0x41,
			0x02, 0x00, 0x30, 0x02 };

	// �������Unicode Pirit Message
	public static final byte[] UNICODE_TEXT = new byte[] { 0x00, 0x50, 0x00,
			0x72, 0x00, 0x69, 0x00, 0x6E, 0x00, 0x74, 0x00, 0x20, 0x00, 0x20,
			0x00, 0x20, 0x00, 0x4D, 0x00, 0x65, 0x00, 0x73, 0x00, 0x73, 0x00,
			0x61, 0x00, 0x67, 0x00, 0x65 };

	public static final DateFormat formatw = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static final String jiebao = "�ݱ��Ƽ�";
	public static final String jiebao_en = "jiebao Technology";
	public static final String jiebao_site = "http://www.jiebaodz.com";

	public static final String a = "    �����һ���ķṦΰҵ��Ҫ�鹦�������������ľ޴����ѡ�";
	public static final String b = "    Many men owe the grandeur of their lives to their tremendous difficulties.";

	/** print test ��ӡ���Լ� */
	public static void printTest() {
		print(ESC_INIT);
		writeEnterLine(1);
		print(PRINTE_TEST);
		writeEnterLine(3);
	}

	/** print text ��ӡ���� */
	public static void printText_GB2312(String text) {
		print(ESC_INIT);
		print(ESC_ALIGN_CENTER);
		writeEnterLine(1);
		print_gbk(text);
		writeEnterLine(3);
		resetPrint();
	}

	/** print text ��ӡ���� */
	public static void printText_Unicode(String text) {
		print(ESC_INIT);
		print(ESC_ALIGN_CENTER);
		writeEnterLine(1);
		// print(text);

		try {
			Log.e("unicode",
					ConvertUtil.binaryToHexString(text.getBytes("unicode")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String uMsg = UnicodeUtil.getUNICODEBytes(text);
		Log.e("uMsg", uMsg);
		print(UNICODE_TEXT);
		writeEnterLine(3);
		resetPrint();
	}

	/**
	 * print photo with path ����ͼƬ·����ӡͼƬ
	 * 
	 * @param ͼƬ��SD��·��
	 *            ����:photo/pic.bmp
	 * */
	public static void printPhotoWithPath(String filePath) {

		String SDPath = Environment.getExternalStorageDirectory() + "/";
		String path = SDPath + filePath;

		// ����·����ȡͼƬ
		File mfile = new File(path);
		if (mfile.exists()) {// �����ļ�����
			Bitmap bmp = BitmapFactory.decodeFile(path);
			byte[] command = decodeBitmap(bmp);
			printPhoto(command);
		} else {
			Log.e("PrintTools_58mm", "the file isn't exists");
		}

	}

	/**
	 * print photo in assets ��ӡassets���ͼƬ
	 * 
	 * @param ͼƬ��assetsĿ¼
	 *            ����:pic.bmp
	 * */
	public static void printPhotoInAssets(Context context, String fileName) {

		AssetManager asm = context.getResources().getAssets();
		InputStream is;
		try {
			is = asm.open(fileName);
			Bitmap bmp = BitmapFactory.decodeStream(is);
			is.close();
			if (bmp != null) {
				byte[] command = decodeBitmap(bmp);
				printPhoto(command);
			} else {
				Log.e("PrintTools", "the file isn't exists");
			}
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("PrintTools", "the file isn't exists");
		}
	}

	/**
	 * decode bitmap to bytes ����BitmapΪλͼ�ֽ���
	 * */
	public static byte[] decodeBitmap(Bitmap bmp) {
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();

		List<String> list = new ArrayList<String>(); // binaryString list
		StringBuffer sb;

		// ÿ���ֽ���(����8�����㲹0)
		int bitLen = bmpWidth / 8;
		int zeroCount = bmpWidth % 8;
		// ÿ����Ҫ�����0
		String zeroStr = "";
		if (zeroCount > 0) {
			bitLen = bmpWidth / 8 + 1;
			for (int i = 0; i < (8 - zeroCount); i++) {
				zeroStr = zeroStr + "0";
			}
		}
		// �����ȡ������ɫ�����ǰ�ɫ��Ϊ��ɫ
		for (int i = 0; i < bmpHeight; i++) {
			sb = new StringBuffer();
			for (int j = 0; j < bmpWidth; j++) {
				int color = bmp.getPixel(j, i); // ���Bitmap ͼƬ��ÿһ�����color��ɫֵ
				// ��ɫֵ��R G B
				int r = (color >> 16) & 0xff;
				int g = (color >> 8) & 0xff;
				int b = color & 0xff;

				// if color close to white��bit='0', else bit='1'
				if (r > 160 && g > 160 && b > 160)
					sb.append("0");
				else
					sb.append("1");
			}
			// ÿһ�н���ʱ������ʣ���0
			if (zeroCount > 0) {
				sb.append(zeroStr);
			}
			list.add(sb.toString());
		}
		// binaryStrÿ8λ����һ��ת����������ƴ��
		List<String> bmpHexList = ConvertUtil.binaryListToHexStringList(list);
		String commandHexString = "1D763000";
		// ���ָ��
		String widthHexString = Integer
				.toHexString(bmpWidth % 8 == 0 ? bmpWidth / 8
						: (bmpWidth / 8 + 1));
		if (widthHexString.length() > 2) {
			Log.e("decodeBitmap error", "��ȳ��� width is too large");
			return null;
		} else if (widthHexString.length() == 1) {
			widthHexString = "0" + widthHexString;
		}
		widthHexString = widthHexString + "00";

		// �߶�ָ��
		String heightHexString = Integer.toHexString(bmpHeight);
		if (heightHexString.length() > 2) {
			Log.e("decodeBitmap error", "�߶ȳ��� height is too large");
			return null;
		} else if (heightHexString.length() == 1) {
			heightHexString = "0" + heightHexString;
		}
		heightHexString = heightHexString + "00";

		List<String> commandList = new ArrayList<String>();
		commandList.add(commandHexString + widthHexString + heightHexString);
		commandList.addAll(bmpHexList);

		return ConvertUtil.hexList2Byte(commandList);
	}

	/**
	 * print photo with bytes ����ָ���ӡͼƬ
	 * */
	public static void printPhoto(byte[] bytes) {
		print(ESC_ALIGN_CENTER);
		writeEnterLine(1);
		print(GS_LEFT);
		print(bytes);
		writeEnterLine(3);
		print(POS_CUT_MODE_FULL);
	}

	/** reset ���ø�ʽ */
	public static void resetPrint() {

		print(ESC_FONT_COLOR_DEFAULT);
		print(FS_FONT_ALIGN);
		print(ESC_ALIGN_LEFT);
		print(ESC_CANCEL_BOLD);
		print(LF);
	}

	/** �����Ƿ���� */
	public static boolean allowTowrite() {
		return C.printSerialPortTools != null;
	}

	/**
	 * ���
	 * 
	 * @param String����
	 * */
	public static void print(String msg) {
		if (allowTowrite())
			C.printSerialPortTools.write(msg);
	}

	/**
	 * ���
	 * 
	 * @param byte[]ָ��
	 * */
	public static void print(byte[] b) {
		if (allowTowrite())
			C.printSerialPortTools.write(b);
	}

	/**
	 * ���
	 * 
	 * @param intָ��
	 * */
	public static void print(int oneByte) {
		if (allowTowrite())
			C.printSerialPortTools.write(oneByte);
	}

	/**
	 * EnterLine ��ֽ
	 * 
	 * @param ��ֽ����
	 * */
	public static void writeEnterLine(int count) {
		for (int i = 0; i < count; i++) {
			print(ESC_GO_PAGE);
		}
	}

	public static void print_unicode(String msg) {
		if (allowTowrite())
			C.printSerialPortTools.write_unicode(msg);
	}

	public static void print_gbk(String msg) {
		if (allowTowrite())
			C.printSerialPortTools.write_gbk(msg);
	}

	public static String getEnterLine(int count) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(ESC_GO_PAGE);
		return sBuilder.toString();
	}

}
