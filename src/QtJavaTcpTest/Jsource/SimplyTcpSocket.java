package QtJavaTcpTest.Jsource;

import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class SimplyTcpSocket extends TcpSocket implements Runnable {

	public SimplyTcpSocket(Socket socket) {
		super(socket);
	}

	public void closed() {
		try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
		closedIO();
		try { this.socket.close(); } catch (Exception e) { this.socket = null; }
	}
	@Override
	public void run() {
		try {

			String msg;
			while (true) {
				msg = readLine();
				if (msg == null) {

					continue;
				}
				if (msg.equals("closed")) {
					sendLine("closed");
					break;
				}
				int v = sum(msg);

				sendLine(String.valueOf(v));

			}
		} catch (SocketTimeoutException e) {
			System.out.println("timeout..");
			
		} 
		catch (SocketException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			System.out.println("invaild input...");
			e.printStackTrace();
			
		}  catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
			closedIO();
			try { this.socket.close(); } catch (Exception e) { this.socket = null; }
			
		}
		
	}

	final public int sum(String s) throws SocketException, NoSuchElementException {
		socketCheck();
		if (s == null) {
			return Integer.MAX_VALUE;
		}
		int a = 0;
		int b = 0;
		try {
			StringTokenizer st = new StringTokenizer(s, " ");
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException();
		}
		return a + b;
	}

}
