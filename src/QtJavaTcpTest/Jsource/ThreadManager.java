package QtJavaTcpTest.Jsource;

import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;


public class ThreadManager {
	private CopyOnWriteArrayList<Runnable> threadList;
	protected Socket clientSocket = null;
	
	protected ThreadManager() {
		threadList = new CopyOnWriteArrayList<Runnable>();
	}

	
	public synchronized void add(SimplyTcpSocket th) {
		System.out.println("tread make..");
		threadList.add(th);
	}
	
	public synchronized void remove(Runnable thread) {
		threadList.remove(thread);
		System.out.println("finished.. \n closed socket..");
		System.out.println("remain threads : " + threadList.size());
	}



}
