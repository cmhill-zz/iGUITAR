package edu.umd.cs.guitar.model;


import java.io.*;
import java.net.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class CommClient {
	
    File requestedFile;
    FileWriter fw;
    BufferedReader in;
    PrintWriter out;
    Thread thread;
    Socket socket;

    public static final int TIME_OUT = 0;
    public static int PORT_NUM = 0;
    public static String SERVER_HOST = "";
    public CommClient(String localhost, String port) {
    	SERVER_HOST = localhost;
    	PORT_NUM = Integer.valueOf(port);    
    }
    public CommClient() {
    	SERVER_HOST = "localhost";
    	PORT_NUM = 8081;     
    }
    
    public void close() {
    	try {
			fw.close();
			out.close();
	        in.close();
	        socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public boolean connect() {
    	return connect(SERVER_HOST, PORT_NUM);
    }
    public boolean connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            socket.setSoTimeout(TIME_OUT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Failed to connect to host: " + host);
            return false;
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to" + host);
            return false;
        }
        return true;
    }
    
    public boolean storeFile(String path, String content) {
    	requestedFile = new File(path);
    	try {
			fw = new FileWriter(requestedFile);
			fw.write(content);
        	fw.flush();
        	fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
    	return true;
    }
    
    public void hearAndReply() {
    	String fromServer;
    	try {
			while ((fromServer = in.readLine()) != null) {
				if (fromServer == IphCommServerConstants.GET_WINDOW_LIST) {
					FileReader fr = new FileReader(new File("G:\\window.xml"));
					BufferedReader br = new BufferedReader(fr);
					out.write(br.readLine());
					break;
				}
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public String request(String request) throws IOException {
    	out.println(request);
    	System.out.println("Client: Request sent");
    	String fromServer;
    	 while ((fromServer = in.readLine()) != null) {
    		 return fromServer;
		 	}
    	 return null;
    }
    
    public static void main(String[] args) throws IOException {
    	(new CommClient()).connect();
    }
}
