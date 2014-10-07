package com.example.pirnter_demo_80mm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Init Printer
		initPrinter();

	}

	public void initPrinter() {
		JBInterface.openPrinter();
		JBInterface.convertPrinterControl();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dayin_gb2312:
			JBInterface.printText_GB2312("Pirnt message!");
			break;
		case R.id.dayin_unicode:
			JBInterface.printText_Unicode("Pirnt message!");
			break;
		case R.id.printer_test:
			JBInterface.testPrinter();
			break;
		case R.id.print_qrcode:
			JBInterface.printQRCodeImageInAssets(this, "qrcode.bmp");
			break;
		case R.id.print_image:
			JBInterface.printImageInAssets(this, "pic.bmp");
			break;
		case R.id.btn_open:
			JBInterface.openPrinter();
			break;
		case R.id.btn_close:
			JBInterface.closePrinter();
			break;
		case R.id.convert_printer:
			JBInterface.convertPrinterControl();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {

		JBInterface.closePrinter();

		super.onDestroy();
	}
}
