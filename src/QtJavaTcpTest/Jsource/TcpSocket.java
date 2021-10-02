package QtJavaTcpTest.Jsource;

import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
// ** io package ** //
import java.io.IOException;

// Reader
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataInputStream;
import java.io.InputStream;

// Sender
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.DataOutputStream;
import java.io.OutputStream;

public abstract class TcpSocket {

	Socket socket = null;
	Reader reader = null;
	Sender sender = null;
	
	public TcpSocket(Socket socket) {
		this.socket = socket;
		openstream();
	}
	
	private final void openstream() {
		 try {
			this.reader = new Reader(this.socket);
			this.sender = new Sender(this.socket);
		} catch (IOException e) {
			System.out.println("cannot open stream");
			e.printStackTrace();
		}
	}
	
	public String readLine() throws SocketException {
		return this.reader.baseReadLine();
	}
	
	public boolean sendLine(String s) throws SocketException {
		return this.sender.baseWriterLine(s);
	}
	
	public void socketCheck() throws SocketException {
	
		if(this.socket.isClosed()) {
			throw new SocketException();
		}
	}
	
	private final class Reader {
		BufferedReader br;
		InputStreamReader isr;
		DataInputStream dis;
		InputStream is;
		
		public Reader(Socket socket) throws IOException {
			this.is = socket.getInputStream();
			this.br = new BufferedReader(this.isr = new InputStreamReader(dis = new DataInputStream(is), StandardCharsets.UTF_8));
		}
		
		public String baseReadLine() throws SocketException {
			socketCheck();
			String s = null;
			try {
				s  = br.readLine();
			} catch (IOException e) {
				System.out.println("read failed..");
				e.printStackTrace();
				return null;
			}
			if(s == null) {
				return null;
			}
			System.out.println("read succeed.."); 
			System.out.println("msg  : " + s);
			return s;
		}
	}
	
	private final class Sender {
		PrintWriter pw;
		OutputStreamWriter osw;
		DataOutputStream dos;
		OutputStream os;
		
		public Sender(Socket socket) throws IOException {
			this.os = socket.getOutputStream();
			this.pw = new PrintWriter(this.osw = new OutputStreamWriter(this.dos = new DataOutputStream(os), StandardCharsets.UTF_8));
		}
		
		public boolean baseWriterLine(String string) throws SocketException  {
			socketCheck();
			try {
				pw.println(string); 
			} catch (Exception e) {
				System.out.println("send failed..");
				System.out.println("msg : " + string);
				e.printStackTrace();
				return false;
			}
			System.out.println("succeed : " + string);
			pw.flush();
			return true;
		}
	}
	
	
}
