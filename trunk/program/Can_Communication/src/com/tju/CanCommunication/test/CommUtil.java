package com.tju.CanCommunication.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

public class CommUtil implements SerialPortEventListener {

	InputStream inputStream; // 浠庝覆鍙ｆ潵鐨勮緭鍏ユ祦
	OutputStream outputStream;// 鍚戜覆鍙ｈ緭鍑虹殑娴�
	SerialPort serialPort; // 涓插彛鐨勫紩鐢�
	CommPortIdentifier portId;

	public CommUtil(Enumeration portList, String name) {
		while (portList.hasMoreElements()) {
			CommPortIdentifier temp = (CommPortIdentifier) portList.nextElement();
			if (temp.getPortType() == CommPortIdentifier.PORT_SERIAL) {// 鍒ゆ柇濡傛灉绔彛绫诲瀷鏄覆鍙�
				if (temp.getName().equals(name)) { // 鍒ゆ柇濡傛灉绔彛宸茬粡鍚姩灏辫繛鎺�
					portId = temp;
				}
			}
		}
		try {
			serialPort = (SerialPort) portId.open("My"+name, 2000);
		} catch (PortInUseException e) {

		}
		try {
			inputStream = serialPort.getInputStream();
			outputStream = serialPort.getOutputStream();
		} catch (IOException e) {
		}
		try {
			serialPort.addEventListener(this); // 缁欏綋鍓嶄覆鍙ｅぉ鍔犱竴涓洃鍚櫒
		} catch (TooManyListenersException e) {
		}
		serialPort.notifyOnDataAvailable(true);
		try {
			serialPort.setSerialPortParams(2400, SerialPort.DATABITS_8, 
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		} catch (UnsupportedCommOperationException e) {
		}
	}

	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		
		case SerialPortEvent.DATA_AVAILABLE:
			byte[] readBuffer = new byte[20];

			try {
				while (inputStream.available() > 0) {
					System.out.println(inputStream.available());
					int numBytes = inputStream.read(readBuffer);
					System.out.println("numByte = "+ numBytes);
				}
				System.out.println(new String(readBuffer).trim());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
	}
	public void send(String content){
		try {
			
			System.out.println(content);		
			outputStream.write(content.getBytes());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void ClosePort() {
	    if (serialPort != null) {
	      serialPort.close();
	    }
	  }

	
}


