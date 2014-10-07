package com.example.pirnter_demo_80mm;

import android.content.Context;
import android.graphics.Bitmap;

public class JBInterface {

	public static boolean openPrinter() {
		int result = GpioControl.activate(GpioControl.printer_o, true);
		if (result == 0)
			return true;
		else
			return false;
	}

	public static boolean closePrinter() {
		int result = GpioControl.activate(GpioControl.printer_o, false);
		if (result == 0)
			return true;
		else
			return false;
	}

	public static boolean convertPrinterControl() {
		int result = GpioControl.convertPrinter();
		C.printSerialPortTools = new SerialPortTools(C.printPort_80mm,
				C.printBaudrate_80mm);
		if (result == 0)
			return true;
		else
			return false;
	}

	/** 自检 */
	public static void testPrinter() {
		PrintTools_80mm.printTest();
	}

	/** 打印文字GB2312 */
	public static void printText_GB2312(String text) {
		PrintTools_80mm.printText_GB2312(text);
	}
	
	/** 打印文字Unicode */
	public static void printText_Unicode(String text) {
		PrintTools_80mm.printText_Unicode(text);
	}

	public static void printQRCodeWithPath(String qrcodeImagePath) {
		PrintTools_80mm.printPhotoWithPath(qrcodeImagePath);
	}

	public static void printImageWithPath(String iamgePath) {
		PrintTools_80mm.printPhotoWithPath(iamgePath);
	}

	public static void printQRCode(Bitmap bitmap) {
		byte[] command = PrintTools_80mm.decodeBitmap(bitmap);
		PrintTools_80mm.printPhoto(command);
	}

	public static void printImage(Bitmap bitmap) {
		byte[] command = PrintTools_80mm.decodeBitmap(bitmap);
		PrintTools_80mm.printPhoto(command);
	}
	
	public static void printQRCodeImageInAssets(Context context,String fileName){
		PrintTools_80mm.printPhotoInAssets(context, fileName);
	}
	
	public static void printImageInAssets(Context context,String fileName){
		PrintTools_80mm.printPhotoInAssets(context, fileName);
	}
}
