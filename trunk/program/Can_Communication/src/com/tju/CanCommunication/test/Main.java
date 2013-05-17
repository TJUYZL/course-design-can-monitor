package com.tju.CanCommunication.test;

import java.util.Enumeration;

import com.tju.CanCommunication.Communication.*;
import gnu.io.CommPortIdentifier;

public class Main {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		//communicationTest();
		testRXTX();
	}
	
	public static void communicationTest(){
		Rs232Command mycommand = new Rs232Command();
		String cmdStr = "";
		Command sendCmd = new Command(cmdStr);
		mycommand.setCmd(sendCmd);
		mycommand.sendCommand(sendCmd);
	}
	
	public static void testRXTX() throws InterruptedException{
		Enumeration portList = CommPortIdentifier.getPortIdentifiers(); //得到当前连接上的端口  
        
        CommUtil comm3 = new CommUtil(portList,"COM3");  
        int i = 0;  
        while(i<5)  
        {  
            Thread.sleep(3000);  
            comm3.send("hello");         
            i++;  
        }  
        comm3.ClosePort(); 
	}
	
	public static void operationTest(){
		
	}

}
