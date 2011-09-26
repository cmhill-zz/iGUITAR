package edu.umd.cs.guitar.model;

import java.net.*;
import java.io.*;

public class CommServer {

	Thread channel;

	ServerSocket iServerSocket;
	ServerSocket jServerSocket;

	Socket iClientSocket;
	Socket jClientSocket;

	PrintWriter iOut;
	PrintWriter jOut;

	BufferedReader iIn;
	BufferedReader jIn;

	XMLProcessor xmlProcessor;
	File xmlFile;

	public static final int TIME_OUT = 0;
	public static final int I_PORT_NUM = 8081;
	public static final int J_PORT_NUM = 8082;

	boolean iRequesting = false;
	boolean iHearing = true;
	boolean jRequesting = false;
	boolean jHearing = true;

	public boolean setUpIServerSocket(int portNum) throws IOException {
		iServerSocket = null;
		try {
			iServerSocket = new ServerSocket(portNum);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + portNum);
			return false;
		}

		iClientSocket = null;

		try {
			iClientSocket = iServerSocket.accept();
			iClientSocket.setSoTimeout(TIME_OUT);
		} catch (IOException e) {
			System.err.println("Accept failed.");
			return false;
		}

		iOut = new PrintWriter(iClientSocket.getOutputStream(), true);
		iIn = new BufferedReader(new InputStreamReader(
				iClientSocket.getInputStream()));
		return true;
	}

	public boolean setUpJServerSocket(int portNum) throws IOException {
		jServerSocket = null;
		try {
			jServerSocket = new ServerSocket(portNum);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + portNum);
			return false;
		}
		System.out.println("Server set up successfully!");
		jClientSocket = null;

		try {
			jClientSocket = jServerSocket.accept();
			jClientSocket.setSoTimeout(TIME_OUT);
		} catch (IOException e) {
			System.err.println("Accept failed.");
			return false;
		}

		jOut = new PrintWriter(jClientSocket.getOutputStream(), true);
		jIn = new BufferedReader(new InputStreamReader(
				jClientSocket.getInputStream()));
		return true;
	}

	public void jHearing() throws IOException {
		String inputLine;
		while ((inputLine = jIn.readLine()) != null) {

			if (inputLine.equals(CommConstants.INVOKE_MAIN_METHOD)) {
				String response;
				System.out.println("Received:" + inputLine);
				jOut.println("Main method has been invoked!!");
				// if ((response = iRequest("get main window")) != null) {
				// jOut.println(response);
				// } else {
				// //throw new IOException("No response from IGuitar!");
				// }
				// // Send window info back to the requesting client
				// FileReader fr = new FileReader(xmlFile);
				// BufferedReader br = new BufferedReader(fr);
				// String buffer = br.readLine();
			}
		}
	}

	public String iRequest(String request) throws IOException {
		iOut.write(request);
		String inputLine;
		while ((inputLine = iIn.readLine()) != null) {
			return inputLine;
		}
		return null;
	}

	public CommServer() {
		channel = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (channel == null) {
					channel = new Thread(this);
					channel.start();
				}
				while (true) {

					try {
						if (jServerSocket == null) {
							setUpJServerSocket(J_PORT_NUM);
						} else {
							if (!jServerSocket.isBound()) {
								setUpJServerSocket(J_PORT_NUM);
							}
						}
						// if (setUpIServerSocket(I_PORT_NUM) &&
						// setUpJServerSocket(I_PORT_NUM)) {
						System.out.println("Connection succeed!");
						System.out.println("Hearing");
						jHearing();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		channel.start();
	}

	public void close() {
		try {
			channel.stop();
			iOut.close();
			iIn.close();
			jOut.close();
			jIn.close();
			iClientSocket.close();
			iServerSocket.close();
			jClientSocket.close();
			jServerSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
