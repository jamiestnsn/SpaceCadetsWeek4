package messagingserver2;

import java.io.BufferedReader;
import java.io.IOException;

public class ConsoleHandler implements Runnable {
	
	private BufferedReader console;
	private Server server;
	private Thread thread;
	private String message;
	
	public ConsoleHandler(BufferedReader console, Server server) {
		
		this.console = console;
		this.server = server;
		
	}

	public void run() {
		
		try {
			while ((message = console.readLine()) != null) {
				if (message.equals("#exit")) {
					server.exit();
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
