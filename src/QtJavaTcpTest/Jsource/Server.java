package QtJavaTcpTest.Jsource;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;


public class Server extends ThreadManager {

	ServerSocket serverSocket = null;
	private final int port = 5555;
	
	public Server() {
		System.out.println("running server....");
		
	}
	
	public void run() {
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.setReuseAddress(true);
		} catch (SocketException e) {
			System.out.println("can't bind");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("can't open port");
			e.printStackTrace();
		} 
		
		while(true) {
			try {
				clientSocket = serverSocket.accept();
				System.out.println("connect call..");
				System.out.println(clientSocket.getLocalSocketAddress());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SimplyTcpSocket socketThread = null;
			try {
				socketThread  = new SimplyTcpSocket(clientSocket);
				add(socketThread);
				socketThread.run();
				remove(socketThread);
				socketThread = null;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if(socketThread != null) {
					remove(socketThread);
				}
				System.gc();
				
			}
		}
		
		
	}
	
	public void remove(Runnable thread) {
		super.remove(thread);
	}
	
	public static void main(String[] args) {
		Server s = new Server();
		s.run();
	}
}
