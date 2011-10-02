package edu.umd.cs.guitar.model;

import java.net.*;
import java.util.ArrayList;
import java.util.Map;
import java.io.*;

public class IphCommServer {

	ServerSocket iServerSocket;

	Socket iSocket;

	static PrintWriter toIphone;
	static BufferedReader fromIphone;

	//XMLProcessor xmlProcessor;
	//File xmlFile;

	private static int time_out = 0;
	private static int port_num = 8081;


	public boolean setUpIServerSocket() {
		iServerSocket = null;
		try {
			iServerSocket = new ServerSocket(port_num);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + port_num);
			return false;
		}
		System.out.println("Server set up successfully!");
		return true;
	}

	public boolean waitForConnection() {
		// If server not set up, set up first
		if (iServerSocket == null) {
			setUpIServerSocket();
		} else {
			if (!iServerSocket.isBound()) {
				setUpIServerSocket();
			}
		}
		
		// Wait for connection, set up the connection
		iSocket = null;
		try {
			iSocket = iServerSocket.accept();
			iSocket.setSoTimeout(time_out);
			System.out.println("Connection set up successfully!");
		} catch (IOException e) {
			System.err.println("Accept failed.");
			return false;
		}

		try {
			toIphone = new PrintWriter(iSocket.getOutputStream(), true);
			fromIphone= new BufferedReader(new InputStreamReader(iSocket.getInputStream()));
			System.out.println("IOStream initialized successfully!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static void getWindowProperties(Map<String, String> nameValueMap, String title) {
		requestAndHear(IphCommServerConstants.GET_WINDOW_PROPERTY_LIST + title);
		XMLProcessor.parseProperties(nameValueMap, file)
	}
	
	
	public static String hear(){
		String inputLine;
		try {
			while ((inputLine = fromIphone.readLine()) != null) {
				return inputLine;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void setTimeOut(int timeout) {
		time_out = timeout;
	}
	
	public int getTimeOut() {
		return time_out;
	}
	
	public void setPortNum(int port) {
		port_num = port;
	}
	
	public int getPortNum() {
		return port_num;
	}
	
	public static void request(String request) {
		toIphone.write(request);
	}
	
	public static String requestAndHear(String request) {
		toIphone.write(request);
		return hear();
	}

	public IphCommServer(int port) {
		port_num = port;
	}
	
	public IphCommServer(int port, int timeout) {
		port_num = port;
		time_out = timeout;
	}
	public IphCommServer() {
		
	}

	public void close() {
		try {
			fromIphone.close();
			toIphone.close();
			iSocket.close();
			iServerSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String requestMainView() {
		// TODO Auto-generated method stub
		return null;
	}

	public static String requestAllOwnedView(String viewID) {
		// TODO Auto-generated method stub
		return null;
	}

}
