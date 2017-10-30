package messagingserver2;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Stack;

public class ClientHandler implements Runnable {
	
	private BufferedReader in;
	private Stack<String> messages;
	private Thread thread;
	
	public ClientHandler(BufferedReader in, Stack<String> messages) {
		
		this.in = in;
		this.messages = messages;
		
	}

	public void run() {
		
		String message;
		
		try {
			while ((message = in.readLine()) != null) {
				messages.push(message);
			}
		} catch (IOException e) {
			System.out.println();
			System.out.println("ERROR: READ FROM CLIENT FAILED");
			System.out.println();
			e.printStackTrace();
		}
		
	}
	
	public void start() {
		
		if(thread == null) {
			thread = new Thread(this, "clientHandlerThread");
			thread.start();
		}
		
	}

}
