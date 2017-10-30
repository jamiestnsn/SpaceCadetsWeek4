package messagingclient2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ConsoleHandler implements Runnable {
	
	private PrintWriter out;
	private BufferedReader console;
	private String clientUserName;
	private Client client;
	private Thread thread;
	private String message;
	
	public ConsoleHandler(PrintWriter out, BufferedReader console, String clientUserName, Client client) {
		
		this.out = out;
		this.console = console;
		this.clientUserName = clientUserName;
		this.client = client;
		
	}

	public void run() {
		
		try {
			while ((message = console.readLine()) != null) {
				if (message.equals("#exit")) {
					client.exit(out, clientUserName);
				} else {
					out.println(clientUserName + ": " + message);
				}
			}
		} catch (IOException e) {
			System.out.println();
			System.out.println("ERROR: READ CONSOLE INPUT FAILED");
			System.out.println();
			e.printStackTrace();
		}
		
	}
	
	public void start() {
		
		if(thread == null) {
			thread = new Thread(this, "consoleHandlerThread");
			thread.start();
		}
		
	}

}
