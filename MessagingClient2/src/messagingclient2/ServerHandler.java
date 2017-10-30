package messagingclient2;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Stack;

public class ServerHandler implements Runnable {
	
	private BufferedReader in;
	private Stack<String> messages;
	private Thread thread;
	private String message;
	
	public ServerHandler(BufferedReader in, Stack<String> messages) {
		
		this.in = in;
		this.messages = messages;
		
	}

	public void run() {
		
		try {
			while((message = in.readLine()) != null) {
				messages.push(message);
			}
		} catch (IOException e) {
			System.out.println();
			System.out.println("ERROR: READ MESSAGE FROM SERVER FAILED");
			System.out.println();
			e.printStackTrace();
		}
		
	}
	
	public void start() {
		
		if(thread == null) {
			thread = new Thread(this, "serverHandlerThread");
			thread.start();
		}
		
	}

}
