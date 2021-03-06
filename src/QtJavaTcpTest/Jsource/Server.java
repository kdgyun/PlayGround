package QtJavaTcpTest.Jsource;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	Socket clientSocket = null;
	ServerSocket serverSocket = null;
	private static final int port = 5555;
	private static final int MAX_THREAD_POOL_SIZE = 25;
	private static final int TIMEOUT = 60 * 1000;	// 60m
	private static ExecutorService threadPool = Executors.newWorkStealingPool(MAX_THREAD_POOL_SIZE);

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
		SimplyTcpSocket t = null;
		while (true) {
			try {
				clientSocket = serverSocket.accept();
				clientSocket.setSoTimeout(TIMEOUT);
				System.out.println("connect call..");
				System.out.println(clientSocket.getLocalSocketAddress());
				threadPool.submit((t = new SimplyTcpSocket(clientSocket)));

			} catch (IOException e) {
				if (e instanceof SocketTimeoutException) {
					t.closed();
				}
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (t.socket.isClosed()) {
					t.closed();
				}
				System.gc();

			}
		}

	}

	public static void main(String[] args) {
		Server s = new Server();
		s.run();
	}
}
